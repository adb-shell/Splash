package com.karthik.splash.homescreen.bottomhometab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomHomeTabViewModel: ViewModel() {
    private val _selectedCategory: MutableLiveData<BottomHomeTab> = MutableLiveData()
    val selectedCategory: LiveData<BottomHomeTab> = _selectedCategory

    fun initalSelectedCategory(category: BottomHomeTab) = onCategorySelected(category = category)

    fun onCategorySelected(category: BottomHomeTab) {
        _selectedCategory.postValue(category)
    }
}