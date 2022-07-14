package com.example.shoppinglist.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.shoppinglist.activity.MainApp
import com.example.shoppinglist.activity.ShopListActivity
import com.example.shoppinglist.const.Const.SHOP_LIST_NAME
import com.example.shoppinglist.databinding.FragmentShopListNamesBinding
import com.example.shoppinglist.db.MainViewModel
import com.example.shoppinglist.db.adapter.ShopListNameAdapter
import com.example.shoppinglist.dialogs.DeleteDialog
import com.example.shoppinglist.dialogs.NewListDialog
import com.example.shoppinglist.entities.ShopListNameItem
import com.example.shoppinglist.utils.TimeManager


class ShopListNamesFragment : BaseFragment(), ShopListNameAdapter.Listener{
    private lateinit var binding: FragmentShopListNamesBinding
    private lateinit var adapter: ShopListNameAdapter
    private lateinit var defPref: SharedPreferences

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClick() {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.listener{
            override fun onClick(name: String) {
                val shopListName = ShopListNameItem(
                    null,
                    name,
                    TimeManager.getCurrentTime(),
                    0,
                    0,
                    ""
                    )
                mainViewModel.insertShopListName(shopListName)
            }

        }, "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopListNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding){
        rcView.layoutManager = LinearLayoutManager(activity)
        defPref = activity?.let { PreferenceManager.getDefaultSharedPreferences(it) }!!
        adapter = ShopListNameAdapter(this@ShopListNamesFragment,defPref)
        rcView.adapter = adapter
    }

    private fun observer(){
        mainViewModel.allShopListNames.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }



    override fun deleteItem(id: Int) {
      DeleteDialog.showDialog(context as AppCompatActivity, object : DeleteDialog.listener{
          override fun onClick() {
              mainViewModel.deleteShopList(id,true)
          }

      })
    }

    override fun editItem(shoplistName: ShopListNameItem) {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.listener{
            override fun onClick(name: String) {

                mainViewModel.updateListName(shoplistName.copy(name = name))
            }

        }, shoplistName.name)
    }

    override fun onClickItem(shopListName: ShopListNameItem) {
        val i = Intent(activity,ShopListActivity::class.java).apply {
            putExtra(SHOP_LIST_NAME,shopListName)
        }

        startActivity(i)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShopListNamesFragment()
    }
}