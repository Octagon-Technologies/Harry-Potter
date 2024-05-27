package com.octagontechnologies.harrypotter.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.octagontechnologies.harrypotter.R
import com.octagontechnologies.harrypotter.ui.theme.HarryPotterTheme
import com.octagontechnologies.harrypotter.ui.theme.Poppins


/**
 * This makes the assumption that if we ever expand this codebase, we will always retain the same No Network Message
 */
@Composable
fun NoNetworkScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.round_glasses),
            contentDescription = null,
            modifier = Modifier.height(130.dp)
        )

        Text(
            text = stringResource(id = R.string.no_network_available),
            modifier = Modifier.padding(top = 12.dp),
            fontFamily = Poppins,
            fontSize = 16.sp
        )
    }
}


@Preview
@Composable
private fun PreviewNoNetworkScreen() {
    HarryPotterTheme {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            NoNetworkScreen()
        }
    }
}