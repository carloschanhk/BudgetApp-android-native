package com.example.budget.ui.SpendingDetails

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.budget.R
import com.example.budget.databinding.FragmentSpendingDetailBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class SpendingDetailsFragment : Fragment() {
    lateinit var spendingDetailsBinding: FragmentSpendingDetailBinding
    private val spendingDetailsViewModel: SpendingDetailsViewModel by viewModels()
    lateinit var spendingChart: LineChart
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
            lifecycleOwner = viewLifecycleOwner
            tvSpendingDetailsCategory.text = spendingDetailsFragmentArgs.category
        }
        val description = Description()
        description.isEnabled = false
        spendingChart.apply {
            setDescription(description)
            setDrawGridBackground(false)
            setTouchEnabled(false)
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
            axisRight.setDrawLabels(false)
            axisRight.setDrawAxisLine(false)
            axisRight.setDrawGridLines(false)
            axisLeft.setDrawLabels(false)
            axisLeft.setDrawAxisLine(false)
            axisLeft.setDrawGridLines(false)
            axisLeft.spaceBottom = 200F
            legend.isEnabled = false
        }

        spendingDetailsViewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) {
                spendingDetailsBinding.spendingDetailLayout.visibility = View.GONE
            } else {
                spendingDetailsBinding.progressBar.visibility = View.GONE
                spendingDetailsBinding.spendingDetailLayout.visibility = View.VISIBLE
                spendingChart.apply {
                    data = LineData(getChartData("Last 7 Days", "Expenses"))
                    data.notifyDataChanged()
                    notifyDataSetChanged()
                    invalidate()
                }
            }
        })

        spendingDetailsViewModel.selectedTimeFrame.observe(viewLifecycleOwner, {
            spendingChart.apply {
                if (!spendingDetailsViewModel.isLoading.value!!){
                    data = LineData(getChartData(it, "Expenses"))
                    data.notifyDataChanged()
                    notifyDataSetChanged()
                    invalidate()
                }

            }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    fun getChartData(timeframe: String, label: String): LineDataSet {
        val chartData = LineDataSet(spendingDetailsViewModel.getYAxisData(timeframe), label)
        chartData.apply {
            color = requireActivity().getColor(R.color.color_main_50)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            fillColor = requireActivity().getColor(R.color.color_main_50)
            fillAlpha = 255
            setDrawValues(false)
            setDrawFilled(true)
            setDrawCircles(false)
        }
        return chartData
    }
}