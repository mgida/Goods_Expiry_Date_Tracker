package com.example.goods_expiry_date_tracker.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.goods_expiry_date_tracker.data.model.ItemModel
import com.example.goods_expiry_date_tracker.databinding.ExpiredGoodsListItemBinding

class ExpiredGoodsAdapter :
    RecyclerView.Adapter<ExpiredGoodsAdapter.GoodsViewHolder>() {

    inner class GoodsViewHolder(private val binding: ExpiredGoodsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(currentItem: ItemModel?) {
            binding.apply {
                expiredTvItemName.text = currentItem?.name
                expiredTvItemType.text = currentItem?.type
                expiredTvItemDate.text = currentItem?.date
            }
        }
    }

    private val comparator = object : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel) =
            oldItem.id == newItem.id
    }
    val differ = AsyncListDiffer(this, comparator)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        return GoodsViewHolder(
            ExpiredGoodsListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        val currentItem: ItemModel? = differ.currentList[position]
        currentItem?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = differ.currentList.size
}