package com.octagontechnologies.harrypotter.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.octagontechnologies.harrypotter.R
import com.octagontechnologies.harrypotter.ui.components.CharacterIcon
import com.octagontechnologies.harrypotter.ui.components.LoadingSpinner
import com.octagontechnologies.harrypotter.ui.components.NoNetworkScreen
import com.octagontechnologies.harrypotter.ui.navigation.JetNavController
import com.octagontechnologies.harrypotter.ui.navigation.rememberJetNavController
import com.octagontechnologies.harrypotter.ui.theme.HarryPotterTheme
import com.octagontechnologies.harrypotter.ui.theme.Poppins
import com.octagontechnologies.harrypotter.utils.Resource
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    jetNavController: JetNavController,
    viewModel: DetailsViewModel = koinViewModel()
) {
    val characterResult by viewModel.character.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        IconButton(onClick = { jetNavController.upPress() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(R.string.back_button),
                modifier = Modifier
                    .padding(4.dp)
            )
        }


        when (characterResult) {
            is Resource.Loading -> {
                Column(Modifier.fillMaxSize()) {
                    LoadingSpinner()
                }
            }

            is Resource.Error -> {
                NoNetworkScreen()
            }

            is Resource.Success -> {
                val character = characterResult.data ?: return

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val roundedCornerShape = RoundedCornerShape(percent = 12)
                    CharacterIcon(
                        imageUrl = character.image, modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(0.82f)
                            .aspectRatio(1f)
                            .wrapContentWidth()
                            .clip(roundedCornerShape)
                    )

                    Text(
                        text = character.actorName,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = character.realName,
                        Modifier.padding(top = 10.dp),
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Light
                    )


                    Column(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(top = 24.dp)
                            .padding(bottom = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val characterTraitsMap = character.toCharacterMap()

                        characterTraitsMap.forEach { (title, content) ->
                            MiniDetail(title = title, content = content)
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun PreviewDetailsScreen() {
    HarryPotterTheme {
        DetailsScreen(rememberJetNavController())
    }
}


@Composable
fun MiniDetail(modifier: Modifier = Modifier, title: String, content: String) {

    Card(
        shape = RoundedCornerShape(2.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = (0.5).dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(end = 30.dp)
            )

            Text(
                text = content,
                fontFamily = Poppins,
                fontWeight = FontWeight.Light,
                fontSize = 15.sp
            )
        }
    }

}

@Preview
@Composable
private fun PreviewMiniDetail() {
    MiniDetail(title = "Ancestry", content = "Half-blood")
}