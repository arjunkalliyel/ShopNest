package com.example.shopnest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shopnest.R
import com.example.shopnest.databinding.ListItemsBinding
import com.example.shopnest.model.ShopData

class ShopAdapter(val sContext: Context, private var shopList: ArrayList<ShopData>) :
    RecyclerView.Adapter<ShopAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ListItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(listData: ShopData) {
            binding.tvTitle.text = listData.title
            binding.tvDescription.text = listData.description
            binding.tvPrice.text = "$ " + listData.price.toString()
            binding.tvRating.text = listData.rating?.rate.toString()
            binding.tvCategory.text = listData.category
            binding.imageCart.load(listData.image) {
                placeholder(R.drawable.placeholder_img)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopAdapter.MyViewHolder {
        return MyViewHolder(
            ListItemsBinding.inflate(
                LayoutInflater.from(sContext), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ShopAdapter.MyViewHolder, position: Int) {
        holder.bind(shopList[position])
    }

    override fun getItemCount(): Int {
        return shopList.size
    }
}