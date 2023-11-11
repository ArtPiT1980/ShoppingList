package com.artpit.android.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.artpit.android.shoppinglist.R

class MainActivity : AppCompatActivity() {
    var count = 0
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            Log.d("ObserveShopItemList", it.toString())

            if (count++ == 0) {
                viewModel.deleteShopItem(it[0])
            }

            if (count++ == 1) {
                viewModel.editShopItem(it[0])
            }
        }
    }
}