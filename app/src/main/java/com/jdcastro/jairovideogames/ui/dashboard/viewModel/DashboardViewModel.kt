package com.jdcastro.jairovideogames.ui.dashboard.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdcastro.jairovideogames.domain.models.VideogameObj
import com.jdcastro.jairovideogames.ui.dashboard.constants.DashboardConstants
import com.jdcastro.jairovideogames.ui.state.VideogameStateUI
import com.jdcastro.jairovideogames.usecase.DeleteVideogameUseCase
import com.jdcastro.jairovideogames.usecase.GetCategoriesUseCase
import com.jdcastro.jairovideogames.usecase.GetVideogameDetailUseCase
import com.jdcastro.jairovideogames.usecase.GetVideogamesByCategoryUseCase
import com.jdcastro.jairovideogames.usecase.VideogameLocalUseCase
import com.jdcastro.jairovideogames.usecase.VideogameRemoteUseCase
import com.jdcastro.jairovideogames.usecase.VideogameSearchByTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val videogameUseCase: VideogameRemoteUseCase,
    private val videogameLocalUseCase: VideogameLocalUseCase,
    private val getVideogameDetailUseCase: GetVideogameDetailUseCase,
    private val getVideogameSearchByTitleUseCase: VideogameSearchByTitleUseCase,
    private val deleteVideogameUseCase: DeleteVideogameUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getVideogamesByCategoryUseCase: GetVideogamesByCategoryUseCase
) : ViewModel() {

    private val _getCategories = MutableLiveData<ArrayList<String>?>()
    val getCategories get() = _getCategories

    private val _getVideogameDetail = MutableLiveData<VideogameObj>()
    val getVideogameDetail get() = _getVideogameDetail

    private val _videogameStateUI = MutableStateFlow<VideogameStateUI>(VideogameStateUI.Loading)
    val videogameStateUI : StateFlow<VideogameStateUI> = _videogameStateUI

    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()

    fun getVideogames() = viewModelScope.launch {
        videogameUseCase.videogameList
            .catch { VideogameStateUI.Error(it.message.orEmpty()) }
            .flowOn(Dispatchers.IO)
            .collect { _videogames ->
            if (_videogames.isNotEmpty()) {
                _videogameStateUI.value = VideogameStateUI.Success(_videogames)
                getCategories()
            } else {
                _videogameStateUI.value = VideogameStateUI.Error("La lista esta vacia")
            }
        }
    }

    fun getVideogameLocal() = viewModelScope.launch {
        videogameLocalUseCase.videogameList
            .catch { VideogameStateUI.Error(it.message.orEmpty()) }
            .flowOn(Dispatchers.IO)
            .collect { _videogames ->
            if (_videogames.isNotEmpty()) {
                _videogameStateUI.value = VideogameStateUI.Success(_videogames)
            } else {
                 getVideogames()
            }
        }
    }

    fun getVideogameDetail(id: Int) = viewModelScope.launch {
        getVideogameDetailUseCase.invoke(id).let { _detail ->
            _detail.let { _getVideogameDetail.value = it }
        }
    }

    fun onQueryTextChanged(query: String) = viewModelScope.launch {
        _queryText.value = query
        if (query.isEmpty()) {
            getVideogameLocal()
        } else {
            getVideogameSearchByTitleUseCase.invoke(query).let { _videogames ->
                if (_videogames.isNotEmpty()) {
                    _videogameStateUI.value = VideogameStateUI.Success(_videogames)
                }
            }
        }
    }

    fun deleteVideogame(item: VideogameObj?) = viewModelScope.launch {
        item?.let { deleteVideogameUseCase.invoke(it) }
        getVideogameLocal()
    }

    fun getCategories() = viewModelScope.launch {
        val categorias = arrayListOf<String>()
        getCategoriesUseCase.invoke().let { _categories ->
            if (_categories.isNotEmpty()) {
                categorias.add(DashboardConstants.ALL_CATEGORIES)
                categorias.addAll(_categories)
                _getCategories.value = categorias
            }
        }
    }

    fun searchByCategory(category: String) = viewModelScope.launch {
        if (category.equals(DashboardConstants.ALL_CATEGORIES)) {
            getVideogameLocal()
        } else {
            getVideogamesByCategoryUseCase.invoke(category).let { _videogames ->
                if (_videogames.isNotEmpty()) {
                    _videogameStateUI.value = VideogameStateUI.Success(_videogames)
                }
            }
        }
    }

}