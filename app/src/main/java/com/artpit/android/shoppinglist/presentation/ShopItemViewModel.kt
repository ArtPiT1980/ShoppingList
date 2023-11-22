package com.artpit.android.shoppinglist.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artpit.android.shoppinglist.data.ShopListRepositoryImpl
import com.artpit.android.shoppinglist.domain.AddShopItemUseCase
import com.artpit.android.shoppinglist.domain.EditShopItemUseCase
import com.artpit.android.shoppinglist.domain.GetShopItemUseCase
import com.artpit.android.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _errorInputName: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(id: Int) {
        scope.launch {
            val item = getShopItemUseCase.getShopItem(id)
            _shopItem.postValue(item)
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)

        if (fieldsValid) {
            scope.launch {
                val shopItem = ShopItem(
                    name = name,
                    count = count,
                    enabled = true
                )

                addShopItemUseCase.addShopItem(shopItem)
                finishWork()
            }
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)

        if (fieldsValid) {
            _shopItem.value?.let {
                scope.launch {
                    val item = it.copy(name = name, count = count)
                    editShopItemUseCase.editShopItem(item)
                    finishWork()
                }
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch(e: Exception) {
            Log.d("ShopItemViewModel", e.toString())
            0
        }
    }

    private fun validateInput(name: String, count: Int) : Boolean {
        var result = true

        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }

        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }

        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.postValue(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}