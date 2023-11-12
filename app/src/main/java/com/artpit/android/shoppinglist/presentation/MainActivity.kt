package com.artpit.android.shoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.artpit.android.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAddShopItem = findViewById<FloatingActionButton>(R.id.add_shop_item)
        buttonAddShopItem.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddMode(this)
            startActivity(intent)
        }

        initRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
//            Log.d("ObserveShopItemList", it.toString())
            shopListAdapter.shopList = it
        }
    }

    private fun initRecyclerView() {
        shopListAdapter = ShopListAdapter()
        val rvShopList: RecyclerView = findViewById(R.id.rv_shop_list)

        with(rvShopList) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }

        setOnShopItemClickListener()
        setOnShopItemLongClickListener()
        setOnShopItemSwipe(rvShopList)
    }

    private fun setOnShopItemSwipe(rvShopList: RecyclerView) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.shopList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setOnShopItemLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
    //            Log.d("MainActivity", "onLongClick: it")
            viewModel.editShopItem(it)
        }
    }

    private fun setOnShopItemClickListener() {
        shopListAdapter.onShopItemClickListener = {
//            Log.d("MainActivity", "onClick: $it")
            val intent = ShopItemActivity.newIntentEditMode(this, it.id)
            startActivity(intent)
            //viewModel.editShopItem(it)
        }
    }
}