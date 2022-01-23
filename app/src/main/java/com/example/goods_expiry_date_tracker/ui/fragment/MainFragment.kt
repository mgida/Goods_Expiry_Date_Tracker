package com.example.goods_expiry_date_tracker.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.goods_expiry_date_tracker.R
import com.example.goods_expiry_date_tracker.adapter.GoodsAdapter
import com.example.goods_expiry_date_tracker.databinding.FragmentMainBinding
import com.example.goods_expiry_date_tracker.viewmodel.GoodsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var goodsAdapter: GoodsAdapter
    private val viewModel by viewModels<GoodsViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        initMainRecyclerView()

        binding.fab.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToScannerFragment()
            findNavController().navigate(action)
        }
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController()
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        observeGoods()
    }

    private fun observeGoods() {
        viewModel.unExpiredItems.observe(viewLifecycleOwner) { items ->
            items?.let {
                goodsAdapter.differ.submitList(it)
            }
        }
    }

    private fun initMainRecyclerView() {
        goodsAdapter = GoodsAdapter()
        binding.rvMain.apply {
            adapter = goodsAdapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}