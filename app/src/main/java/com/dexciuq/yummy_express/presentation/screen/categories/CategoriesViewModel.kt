package com.dexciuq.yummy_express.presentation.screen.categories

import androidx.lifecycle.ViewModel
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(

) : ViewModel() {

    private val _categories = MutableStateFlow<Resource<List<Category>>>(Resource.Loading)
    val categories get() = _categories.asStateFlow()
}