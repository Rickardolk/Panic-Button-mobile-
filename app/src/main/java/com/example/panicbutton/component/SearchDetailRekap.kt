package com.example.panicbutton.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
           .shadow(
               elevation = 4.dp,
               shape = RoundedCornerShape(24.dp),
               clip = false,
               ambientColor = Color.LightGray,
               spotColor = Color.Black.copy(alpha = 0.5f)
           )
           .offset(y = (-2).dp)
           .padding(bottom = 8.dp)
           .fillMaxWidth()
           .clip(RoundedCornerShape(24.dp))
           .background(color = Color.White)
   ) {
       Row(
           modifier
               .fillMaxWidth()
               .padding(horizontal = 16.dp),
           verticalAlignment = Alignment.CenterVertically
       ) {
           TextField(
               value = query,
               onValueChange = onQueryChange,
               placeholder = {
                   Text(
                       "Search",
                       color = colorResource(id = R.color.font3),
                       style = TextStyle(lineHeight = 20.sp)
                   )
               },
               colors = TextFieldDefaults.textFieldColors(
                   containerColor = Color.Transparent,
                   focusedIndicatorColor = Color.Transparent,
                   unfocusedIndicatorColor = Color.Transparent,
                   focusedTextColor = colorResource(id = R.color.font2),
                   unfocusedTextColor = colorResource(id = R.color.font2),
                   disabledIndicatorColor = Color.Transparent

               ),
               singleLine = true,
               modifier = Modifier
                   .height(46.dp)
                   .weight(1f),
               textStyle = TextStyle(lineHeight = 20.sp)
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