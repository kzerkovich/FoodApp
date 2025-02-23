package com.kzerk.foodapp.Activity.Dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kzerk.foodapp.Activity.Splash.BaseActivity
import com.kzerk.foodapp.Domain.CategoryModel
import com.kzerk.foodapp.Domain.ItemModel
import com.kzerk.foodapp.Domain.SliderModel
import com.kzerk.foodapp.R
import com.kzerk.foodapp.ViewModel.MainViewModel
import com.kzerk.foodapp.ui.theme.FoodAppTheme

class MainActivity : BaseActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			DashboardScreen {

			}
		}
	}
}

@Composable
fun DashboardScreen(onCartClick:() -> Unit) {
	val viewModel = MainViewModel()
	val banners = remember { mutableStateListOf<SliderModel>() }
	val categories = remember { mutableStateListOf<CategoryModel>() }
	val bestSeller = remember { mutableStateListOf<ItemModel>() }

	var showBannerLoading by remember { mutableStateOf(true) }
	var showCategoryLoading by remember { mutableStateOf(true) }
	var showBestSellerLoading by remember { mutableStateOf(true) }

	LaunchedEffect(Unit) {
		viewModel.loadBanner().observeForever {
			banners.clear()
			banners.addAll(it)
			showBannerLoading = false
		}
	}

	LaunchedEffect(Unit) {
		viewModel.loadCategory().observeForever {
			categories.clear()
			categories.addAll(it)
			showCategoryLoading = false
		}
	}

	LaunchedEffect(Unit) {
		viewModel.loadBestSeller().observeForever {
			bestSeller.clear()
			bestSeller.addAll(it)
			showBestSellerLoading = false
		}
	}

	ConstraintLayout(
		modifier = Modifier.background(Color.White)
	) {
		val (scrollList, bottomMenu) = createRefs()

		LazyColumn(
			modifier = Modifier
				.fillMaxSize()
				.constrainAs(scrollList) {
					top.linkTo(parent.top)
					bottom.linkTo(parent.bottom)
					end.linkTo(parent.end)
					start.linkTo(parent.start)
				}
		) {
			item {
				Row(
					modifier = Modifier
						.fillMaxSize()
						.padding(top = 70.dp)
						.padding(horizontal = 16.dp),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Column {
						Text("С возвращением", color = Color.Black)
						Text(
							"Егор", color = Color.Black,
							fontSize = 18.sp,
							fontWeight = FontWeight.Bold
						)
					}
					Row() {
						Image(
							painter = painterResource(R.drawable.search_icon),
							contentDescription = null
						)
						Spacer(modifier = Modifier.width(16.dp))
						Image(
							painter = painterResource(R.drawable.bell_icon),
							contentDescription = null
						)
					}
				}
			}

			item {
				if (showBannerLoading) {
					Box(
						modifier = Modifier
							.fillMaxSize()
							.height(200.dp),
						contentAlignment = Alignment.Center
					) {
						CircularProgressIndicator()
					}
				} else {
					Banners(banners)
				}
			}

			item {
				Text(
					"Categories",
					color = Color.Black,
					fontSize = 18.sp,
					fontWeight = FontWeight.Bold,
					modifier = Modifier
						.fillParentMaxWidth()
						.padding(top = 24.dp)
						.padding(horizontal = 16.dp)
				)
			}

			item {
				if (showCategoryLoading) {
					Box(
						modifier = Modifier
							.fillMaxSize()
							.height(50.dp),
						contentAlignment = Alignment.Center
					) {
						CircularProgressIndicator()
					}
				} else {
					CategoryList(categories)
				}
			}

			item {
				Row(
					modifier = Modifier
						.fillParentMaxWidth()
						.padding(top = 24.dp)
						.padding(horizontal = 16.dp),
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					Text(
						text = "Best Seller Product",
						color = Color.Black,
						fontSize = 18.sp,
						fontWeight = FontWeight.Bold
					)
					Text(
						text = "See All",
						color = colorResource(R.color.midBrown)
					)
				}
			}

			item {
				if (showBestSellerLoading) {
					Box(
						modifier = Modifier
							.fillMaxWidth()
							.height(200.dp),
						contentAlignment = Alignment.Center
					) {
						CircularProgressIndicator()
					}
				} else {
					ListItems(bestSeller)
				}
			}
			item {
				BottomMenu(
					modifier = Modifier
						.fillMaxWidth()
						.constrainAs(bottomMenu) {
							bottom.linkTo(parent.bottom)
						},
					onItemClick = onCartClick
				)
			}
		}

	}
}