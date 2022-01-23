package com.example.goods_expiry_date_tracker.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.goods_expiry_date_tracker.R
import com.example.goods_expiry_date_tracker.adapter.ExpiredGoodsAdapter
import com.example.goods_expiry_date_tracker.databinding.FragmentExpiredItemsBinding
import com.example.goods_expiry_date_tracker.viewmodel.GoodsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpiredItemsFragment : Fragment(R.layout.fragment_expired_items) {

    private var _binding: FragmentExpiredItemsBinding? = null
    private val binding get() = _binding!!

    private lateinit var expiredGoodsAdapter: ExpiredGoodsAdapter
    private val viewModel by viewModels<GoodsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentExpiredItemsBinding.bind(view)

        initExpiredRecyclerView()
    }

    private fun initExpiredRecyclerView() {
        expiredGoodsAdapter = ExpiredGoodsAdapter()
        binding.rvExpired.apply {
            adapter = expiredGoodsAdapter
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()
        observeExpiredItems()
    }

    private fun observeExpiredItems() {

        viewModel.expiredItems.observe(viewLifecycleOwner, { items ->
            expiredGoodsAdapter.differ.submitList(items)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}