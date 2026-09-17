package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.R

@Composable
fun RenovaLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.v5_primary),
        contentDescription = "Renova Logo",
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}
