package com.github.valecarrirolo.carbrands.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.github.valecarrirolo.carbrands.CarBrandsViewModel
import com.github.valecarrirolo.carbrands.ui.theme.CarbrandsTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("list") {
            ListScreen(navController)
        }
    }
}


@Composable
private fun HomeScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("CarBrands") })
    }) {
        val vm: CarBrandsViewModel = hiltViewModel()
        val carWrapperList by vm.carWrapperStateFlow.collectAsState()

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Favorite cars:\n ${carWrapperList.count { it.isFavourite }}",
                modifier = Modifier.padding(32.dp),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
            )
            Button(onClick = { navController.navigate("list") }) {
                Text(text = "Car list")
            }
        }
    }
}

@Composable
private fun ListScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("CarBrands List") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, null)
                }
            })
    }) {
        val vm: CarBrandsViewModel = hiltViewModel()
        val carWrapperList by vm.carWrapperStateFlow.collectAsState()

        LazyColumn(modifier = Modifier.padding(it)) {
            items(carWrapperList) { carWrapper ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillParentMaxWidth()
                ) {
                    Column {
                        Row {
                            Switch(
                                checked = carWrapper.isFavourite,
                                onCheckedChange = { vm.setCarFavorite(carWrapper, it) }
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = carWrapper.car.name,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(8.dp),
                                )
                                Text(
                                    text = carWrapper.car.origin,
                                    modifier = Modifier.padding(8.dp),
                                )
                                Text(
                                    text = carWrapper.car.type,
                                    modifier = Modifier.padding(8.dp),
                                )
                            }
                            AsyncImage(
                                model = carWrapper.car.image,
                                contentDescription = null,
                                modifier = Modifier.padding(8.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CarbrandsTheme {
        MainScreen()
    }
}