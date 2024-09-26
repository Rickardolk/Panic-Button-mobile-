package com.example.panicbutton.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panicbutton.R
import com.example.panicbutton.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.panicbutton.component.DataRekapItem
import com.example.panicbutton.component.SearchDetailRekap

@Composable
fun DataRekapScreen(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = viewModel(),
    navController: NavController
) {
    val rekapData by viewModel.rekapData.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState(null)
    var searchQuery by remember {mutableStateOf("")}
    val filterSearch = rekapData.filter { it.nomor_rumah.contains(searchQuery, ignoreCase = true) }

    LaunchedEffect(Unit) {
        viewModel.fetchRekapData()
    }

    Column(
        modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(colorResource(id = R.color.primary))
                .padding(top = 40.dp, start = 26.dp, end = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "List Data Rekap",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier.height(26.dp))
            SearchDetailRekap(
                query = searchQuery,
                onQueryChange = { newQuery ->
                    searchQuery = newQuery
                },
                onSearch = {

                }
            )
        }
        Column(
            modifier
                .background(color = colorResource(id = R.color.background))
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (errorMessage != null) {
                Text(text = "Error: $errorMessage", color = Color.Red)
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filterSearch) { log ->
                        DataRekapItem( log, navController = navController)
                    }
                }
            }
        }
    }
}


