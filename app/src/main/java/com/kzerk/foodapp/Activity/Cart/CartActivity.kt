package com.kzerk.foodapp.Activity.Cart

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kzerk.foodapp.Activity.BaseActivity
import com.kzerk.foodapp.Helper.ManagementCart
import com.kzerk.foodapp.R

class CartActivity : BaseActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			CartScreen(
				ManagementCart(this),
				onBackCLick = { finish() }
			)
		}
	}
}

@Composable
private fun CartScreen(
	managementCart: ManagementCart = ManagementCart(LocalContext.current),
	onBackCLick: () -> Unit
) {
	var cartItem = remember { mutableStateOf(managementCart.listCart) }
	var tax = remember { mutableStateOf(0.0) }
	calculatorCart(managementCart, tax)

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
	) {
		ConstraintLayout(modifier = Modifier.padding(top = 36.dp)) {
			val (backBtn, cartTxt) = createRefs()
			Text(
				modifier = Modifier
					.fillMaxWidth()
					.constrainAs(cartTxt) {
						centerTo(parent)
					},
				text = "Your Cart",
				textAlign = TextAlign.Center,
				fontWeight = FontWeight.Bold,
				fontSize = 25.sp
			)
			Image(
				painter = painterResource(R.drawable.back),
				contentDescription = null,
				modifier = Modifier
					.constrainAs(backBtn) {
						top.linkTo(parent.top)
						bottom.linkTo(parent.bottom)
						start.linkTo(parent.start)
					}
					.clickable { onBackCLick() }
			)
		}

		if (cartItem.value.isEmpty()) {
			Text(
				text = "Cart is empty",
				fontSize = 18.sp,
				modifier = Modifier.align(Alignment.CenterHorizontally)
			)
		} else {
			CartList(
				cartItem = cartItem.value,
				managementCart
			) {
				cartItem.value = managementCart.listCart
				calculatorCart(managementCart, tax)
			}
			CartSummary(
				itemTotal = managementCart.totalFee,
				tax = tax.value,
				delivery = 10.0
			)
		}
	}
}

fun calculatorCart(managementCart: ManagementCart, tax: MutableState<Double>) {
	val percentTax = 0.02
	tax.value = Math.round((managementCart.totalFee * percentTax) * 100) / 100.0
}