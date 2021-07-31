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
import com.example.budget.data.chart.BarChartAdapter
import com.example.budget.databinding.FragmentSpendingDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class SpendingDetailsFragment : Fragment() {
    lateinit var spendingDetailsBinding: FragmentSpendingDetailBinding
    private val spendingDetailsViewModel: SpendingDetailsViewModel by viewModels()
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
        spendingDetailsBinding.apply {
            spendingToolbar.apply {
                title = spendingDetailsFragmentArgs.category
                setNavigationOnClickListener {
                    findNavController().navigateUp()
                }
                inflateMenu(R.menu.menu_spending_details)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.btn_spending_fragment_add -> {
                            findNavController().navigate(
                                SpendingDetailsFragmentDirections.actionSpendingDetailFragmentToTransactionCreationDialogFragment(
                                    category = spendingDetailsFragmentArgs.category
                                )
                            )
                            true
                        }
                        else -> false
                    }
                }
            }
            lifecycleOwner = viewLifecycleOwner
            viewModel = spendingDetailsViewModel
            rvDetailsTransactions.apply {
                adapter = SpendingTransactionAdapter()
                addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            }
            rvBarChart.adapter = context?.let { BarChartAdapter(it) }
        }

        spendingDetailsViewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) {
                spendingDetailsBinding.progressBar.visibility = View.VISIBLE
                spendingDetailsBinding.spendingDetailLayout.visibility = View.GONE
            } else {
                spendingDetailsBinding.progressBar.visibility = View.GONE
                spendingDetailsBinding.spendingDetailLayout.visibility = View.VISIBLE
                spendingDetailsViewModel.loadBarChartData("Last 7 Days")
            }
        })

        spendingDetailsViewModel.selectedTimeFrame.observe(viewLifecycleOwner, {
            spendingDetailsViewModel.loadBarChartData(it)
            spendingDetailsBinding.rvBarChart.adapter?.notifyDataSetChanged()
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
}