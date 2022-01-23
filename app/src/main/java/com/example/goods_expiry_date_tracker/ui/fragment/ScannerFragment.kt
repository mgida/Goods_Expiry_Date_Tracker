package com.example.goods_expiry_date_tracker.ui.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.goods_expiry_date_tracker.R
import com.example.goods_expiry_date_tracker.data.model.ItemModel
import com.example.goods_expiry_date_tracker.databinding.FragmentScannerBinding
import com.example.goods_expiry_date_tracker.notifaction.NotificationHelper
import com.example.goods_expiry_date_tracker.utils.compareDates
import com.example.goods_expiry_date_tracker.viewmodel.GoodsViewModel
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDateTime

@AndroidEntryPoint
class ScannerFragment : Fragment(R.layout.fragment_scanner) {

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!

    private lateinit var qrScanIntegrator: IntentIntegrator
    private val viewModel by viewModels<GoodsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentScannerBinding.bind(view)

        getData()

        setupScanner()
        binding.btnScan.setOnClickListener {
            qrScanIntegrator.initiateScan()
        }
    }

    private fun setupScanner() {
        qrScanIntegrator = IntentIntegrator.forSupportFragment(this)
        qrScanIntegrator.setOrientationLocked(false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(activity, R.string.result_not_found, Toast.LENGTH_LONG).show()
            } else {
                try {
                    val obj = JSONObject(result.contents)
                    var days = ""

                    val itemId = obj.getString("id")
                    val itemName = obj.getString("item_name")
                    val itemType = obj.getString("item_type")
                    val itemDate = obj.getString("item_date")

                    viewModel.attachValues(itemId, itemName, itemType, itemDate)
                    getData()

                    lifecycleScope.launch {
                        days = compareDates(
                            current = LocalDateTime.now(),
                            selectedDate = obj.getString("item_date").toString()
                        )
                        //  viewModel.attachDays(days)
                    }

                    val itemModel = ItemModel(
                        id = obj.getString("id").toInt(),
                        name = obj.getString("item_name"),
                        type = obj.getString("item_type"),
                        date = obj.getString("item_date"),
                        daysUntilExpired = days,
                    )

                    saveData(itemModel)

                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getData() {
        binding.apply {
            itemId.text = viewModel.id
            itemName.text = viewModel.name
            itemType.text = viewModel.type
            itemDate.text = viewModel.date
        }
    }

    private fun saveData(itemModel: ItemModel) {
        viewModel.insertItem(itemModel)
        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
        if (itemModel.daysUntilExpired == "0") {
            buildNotification(itemModel)
        }

    }

    private fun buildNotification(itemModel: ItemModel) {
        val msg = "Item ${itemModel.name} is expired .."
        NotificationHelper().displayNotification(requireContext(), msg)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}