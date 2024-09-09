package com.example.panicbutton.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.panicbutton.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panicbutton.component.DataItem

@Composable
fun DataRekapScreen(
    modifier: Modifier,
    viewModel: ViewModel = viewModel()
) {
    val rekapData by viewModel.rekapData.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState(null)

    LaunchedEffect(Unit) {
        viewModel.fetchRekapData()
    }

    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (errorMessage != null) {
            Text(text = "Error: $errorMessage", color = Color.Red)
        } else {
            LazyColumn {
                items(rekapData) { data ->
                    DataItem(modifier, data)
                }
            }
        }
    }
}




