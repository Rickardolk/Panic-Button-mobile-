package com.example.panicbutton.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.panicbutton.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDetailRekap(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
   Box(
       modifier
           .shadow(elevation = 4.dp)
           .fillMaxWidth()
           .clip(RoundedCornerShape(24.dp))
           .background(color = Color.White)
   ) {
       Row(
           modifier
               .fillMaxWidth()
               .padding(horizontal = 16.dp, vertical = 8.dp),
           verticalAlignment = Alignment.CenterVertically
       ) {
           TextField(
               value = query,
               onValueChange = onQueryChange,
               placeholder = { Text("Search")},
               colors = TextFieldDefaults.textFieldColors(
                   containerColor = Color.Transparent,
                   focusedIndicatorColor = Color.Transparent,
                   unfocusedIndicatorColor = Color.Transparent,
                   focusedTextColor = colorResource(id = R.color.font),
                   unfocusedTextColor = colorResource(id = R.color.font2),
                   disabledIndicatorColor = Color.Transparent

               ),
               singleLine = true,
               modifier = Modifier
                   .weight(1f)
                   .padding(end = 8.dp)
           )
           IconButton(
               onClick = onSearch,
               modifier = Modifier.size(24.dp)
           ) {
               Icon(
                   imageVector = Icons.Default.Search,
                   contentDescription = "ic_search",
                   tint = colorResource(id = R.color.font2)
               )
           }
       }
   }
}

@Preview(showBackground = false)
@Composable
private fun Liat() {
    SearchDetailRekap(
        query = "",
        onQueryChange = {},
        onSearch = {}
    )
}