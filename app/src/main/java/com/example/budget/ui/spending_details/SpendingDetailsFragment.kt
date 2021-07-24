package com.example.budget.ui.spending_details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budget.R
import com.example.budget.databinding.FragmentSpendingDetailBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class SpendingDetailsFragment : Fragment() {
    lateinit var spendingDetailsBinding: FragmentSpendingDetailBinding
    private val spendingDetailsViewModel: SpendingDetailsViewModel by viewModels()
    lateinit var spendingChart: BarChart
    private val spendingDetailsFragmentArgs: SpendingDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        spendingDetailsBinding = FragmentSpendingDetailBinding.inflate(inflater, container, false)
        return spendingDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spendingDetailsViewModel.getAllExpenses(spendingDetailsFragmentArgs.category)
        spendingChart = spendingDetailsBinding.spendingChart
        spendingDetailsBinding.apply {
            spendingToolbar.apply {
                title = spendingDetailsFragmentArgs.category
                inflateMenu(R.menu.menu_spending)
                setOnMenuItemClickListener {
                    when (it.itemId){
                        R.id.icon_search_spending -> true
                        else -> false
                    }
                }
                setNavigationOnClickListener {
                    findNavController().navigateUp()
                }
            }
            lifecycleOwner = viewLifecycleOwner
            viewModel = spendingDetailsViewModel
            rvDetailsTransactions.apply {
                adapter = SpendingTransactionAdapter()
                addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            }
        }
        val description = Description()
        description.isEnabled = false
        spendingChart.apply {
            setDescription(description)
            setDrawGridBackground(false)
            setTouchEnabled(false)
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            axisRight.setDrawLabels(false)
            axisRight.setDrawAxisLine(false)
            axisRight.setDrawGridLines(false)
            legend.isEnabled = false
        }

        spendingDetailsViewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) {
                spendingDetailsBinding.progressBar.visibility = View.VISIBLE
                spendingDetailsBinding.spendingDetailLayout.visibility = View.GONE
            } else {
                spendingDetailsBinding.progressBar.visibility = View.GONE
                spendingDetailsBinding.spendingDetailLayout.visibility = View.VISIBLE
                spendingChart.apply {
                    data = BarData(getChartData("Last 7 Days", spendingDetailsFragmentArgs.category))
                    data.notifyDataChanged()
                    notifyDataSetChanged()
                    invalidate()
                }
            }
        })

        spendingDetailsViewModel.selectedTimeFrame.observe(viewLifecycleOwner, {
            spendingChart.apply {
                if (!spendingDetailsViewModel.isLoading.value!!) {
                    data = BarData(getChartData(it, spendingDetailsFragmentArgs.category))
                    data.notifyDataChanged()
                    notifyDataSetChanged()
                    invalidate()
                }
            }
            spendingDetailsBinding.rvDetailsTransactions.adapter?.notifyDataSetChanged()

        })
        spendingDetailsBinding.spinnerSetTimeframe.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedTimeFrame: String =
                        resources.getStringArray(R.array.timeframe)[position]
                    spendingDetailsViewModel.setTimeFrame(selectedTimeFrame)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }


    }

    private fun getChartData(timeframe: String, label: String): BarDataSet {
        val chartData = BarDataSet(spendingDetailsViewModel.getYAxisData(timeframe), label)
        chartData.apply {
            color = requireActivity().getColor(R.color.color_main_50)
            setDrawValues(false)
        }
        return chartData
    }
}