package com.karthik.splash.homescreen.bottomhometab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    private val _selectedCategory: MutableLiveData<BottomHomeTabType> = MutableLiveData()
    val selectedCategory: LiveData<BottomHomeTabType> = _selectedCategory

    fun initalSelectedCategory(category: BottomHomeTabType) = onCategorySelected(category = category)

    fun onCategorySelected(category: BottomHomeTabType) {
        _selectedCategory.postValue(category)
    }
}