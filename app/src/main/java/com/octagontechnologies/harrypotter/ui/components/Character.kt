@file:OptIn(ExperimentalGlideComposeApi::class)

package com.octagontechnologies.harrypotter.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.octagontechnologies.harrypotter.R
import com.octagontechnologies.harrypotter.domain.Character
import com.octagontechnologies.harrypotter.ui.theme.HarryPotterTheme
import com.octagontechnologies.harrypotter.ui.theme.Poppins

@Composable
fun MiniCharacter(
    modifier: Modifier = Modifier,
    imageHeight: Dp = 220.dp,
    character: Character,
    openCharacterDetails: (Character) -> Unit
) {
    val roundedCornerShape = RoundedCornerShape(12.dp)

    Card(
        modifier = modifier.clickable { openCharacterDetails(character) },
        shape = roundedCornerShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CharacterIcon(
                imageUrl = character.image, modifier = Modifier
                    .requiredHeight(imageHeight)
                    .fillMaxWidth()
                    .clip(roundedCornerShape)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = character.actorName,
                fontFamily = Poppins,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = character.realName,
                fontFamily = Poppins,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PreviewMiniCharacter() {
    HarryPotterTheme {
        MiniCharacter(character = Character.TEST_CHARACTER) {}
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterIcon(
    modifier: Modifier,
    imageUrl: String?,
    loadingSpinnerSize: Dp = 32.dp
) {
    GlideImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier,
        loading = placeholder {
            Column(
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = 64.dp),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(loadingSpinnerSize),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        failure = placeholder {
            Image(
                painter = painterResource(id = R.drawable.alien),
                contentDescription = null,
                modifier = modifier,
                contentScale = ContentScale.Crop
            )
        }
    )
}