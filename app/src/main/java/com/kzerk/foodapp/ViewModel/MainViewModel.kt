package com.kzerk.foodapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kzerk.foodapp.Domain.CategoryModel
import com.kzerk.foodapp.Domain.ItemsModel
import com.kzerk.foodapp.Domain.SliderModel
import com.kzerk.foodapp.Repository.MainRepository

class MainViewModel(): ViewModel() {
	private val repository = MainRepository()

	fun loadBanner(): LiveData<MutableList<SliderModel>> {
		return repository.loadBanner()
	}

	fun loadCategory(): LiveData<MutableList<CategoryModel>> {
		return repository.loadCategory()
	}

	fun loadBestSeller(): LiveData<MutableList<ItemsModel>> {
		return repository.loadBestSeller()
	}

	fun loadFiltered(id: String): LiveData<MutableList<ItemsModel>> {
		return repository.loadFiltered(id)
	}
}