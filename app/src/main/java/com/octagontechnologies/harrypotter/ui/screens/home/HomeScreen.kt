package com.octagontechnologies.harrypotter.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.octagontechnologies.harrypotter.R
import com.octagontechnologies.harrypotter.domain.Character
import com.octagontechnologies.harrypotter.domain.CharacterGroup
import com.octagontechnologies.harrypotter.ui.components.LoadingSpinner
import com.octagontechnologies.harrypotter.ui.components.MiniCharacter
import com.octagontechnologies.harrypotter.ui.components.NoNetworkScreen
import com.octagontechnologies.harrypotter.ui.navigation.JetNavController
import com.octagontechnologies.harrypotter.ui.theme.HarryPotterTheme
import com.octagontechnologies.harrypotter.ui.theme.Poppins
import com.octagontechnologies.harrypotter.ui.theme.Yellow
import com.octagontechnologies.harrypotter.utils.ErrorType
import com.octagontechnologies.harrypotter.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    coroutineScope: CoroutineScope,
    jetNavController: JetNavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val characters by viewModel.characters.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(Modifier.height(18.dp))
        Image(
            painter = painterResource(id = R.drawable.harry_potter_name),
            contentDescription = "Harry Potter",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(18.dp))

        val pagerState = rememberPagerState { 3 }
        PotterTabs(
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            charactersResult = characters,
            loadCharacters = { viewModel.fetchCharacters(it) },
            openCharacterDetails = { jetNavController.navigateToDetailsScreen(it) }
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PotterTabs(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    charactersResult: Resource<List<Character>>,
    loadCharacters: (CharacterGroup) -> Unit,
    openCharacterDetails: (Character) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            divider = {},
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            CharacterGroup.list.forEachIndexed { index, characterGroup ->
                val isSelected = pagerState.currentPage == index

                Tab(
                    selected = isSelected,
                    onClick = {
                        coroutineScope.launch {
                            Timber.d("Former page is ${pagerState.currentPage}")
                            pagerState.scrollToPage(index)
                            Timber.d("New page is ${pagerState.currentPage}")
                        }
                        loadCharacters(characterGroup)
                    },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(percent = 25))
                        .background(if (isSelected) Yellow else Color.Transparent)
                ) {
                    Text(
                        text = characterGroup.groupName,
                        modifier = Modifier.padding(10.dp),
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }


        when (charactersResult) {
            is Resource.Loading -> {
                LoadingSpinner()
            }

            is Resource.Error -> {
                NoNetworkScreen()
            }

            else -> {
                HorizontalPager(state = pagerState) {

                    val characters = charactersResult.data ?: return@HorizontalPager
                    LazyVerticalGrid(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .padding(horizontal = 8.dp)
                            .fillMaxSize()
                            .padding(bottom = 2.dp),
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(characters) { character ->
                            MiniCharacter(
                                character = character,
                                openCharacterDetails = openCharacterDetails
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewPotterTabs() {
    val pagerState = rememberPagerState { 3 }
    val coroutineScope = rememberCoroutineScope()
    val characters = Resource.Success(Character.LIST_OF_CHARACTERS)

    HarryPotterTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Spacer(Modifier.height(18.dp))
            Image(
                painter = painterResource(id = R.drawable.harry_potter_name),
                contentDescription = "Harry Potter",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(18.dp))

            PotterTabs(
                pagerState = pagerState,
                coroutineScope = coroutineScope,
                charactersResult = characters,
                loadCharacters = {},
                openCharacterDetails = {}
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewPotterTabsWithLoading() {
    val pagerState = rememberPagerState { 3 }
    val coroutineScope = rememberCoroutineScope()
    val characters = Resource.Loading<List<Character>>()

    HarryPotterTheme {
        PotterTabs(
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            charactersResult = characters,
            loadCharacters = {},
            openCharacterDetails = {}
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewPotterTabsWithError() {
    val pagerState = rememberPagerState { 3 }
    val coroutineScope = rememberCoroutineScope()
    val characters = Resource.Error<List<Character>>(ErrorType.NoNetworkError)


    HarryPotterTheme {
        PotterTabs(
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            charactersResult = characters,
            loadCharacters = {},
            openCharacterDetails = {}
        )
    }
}