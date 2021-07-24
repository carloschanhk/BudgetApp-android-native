package com.example.budget.ui.home

import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budget.R
import com.example.budget.common.CategoryType
import com.example.budget.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels<HomeViewModel>()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            homeToolbar.apply {
                title = getString(R.string.home_fragment_label)
                inflateMenu(R.menu.menu_main)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.action_set_month_budget -> {
                            findNavController().navigate(R.id.action_HomeFragment_to_setBudgetDialogFragment)
                            true
                        }
                        R.id.action_add_expense -> {
                            findNavController().navigate(R.id.action_HomeFragment_to_transactionCreationDialogFragment)
                            true
                        }
                        else -> false
                    }
                }
            }
            lifecycleOwner = viewLifecycleOwner
            viewModel = homeViewModel
            context = activity
            categoryTypes = CategoryType.values().toList()
            homeBottomSection.rvTransactions.apply {
                adapter = TransactionsAdapter(homeViewModel, findNavController())
                addItemDecoration(
                    DividerItemDecoration(
                        activity,
                        LinearLayoutManager.VERTICAL
                    )
                )
            }
            homeBottomSection.rvCategory.apply {
                adapter = CategoryAdapter(context, homeViewModel, findNavController())
                addItemDecoration(
                    DividerItemDecoration(
                        activity,
                        LinearLayoutManager.VERTICAL
                    )
                )
            }
        }

        homeViewModel.showTransactions.observe(viewLifecycleOwner, { choice ->
            if (choice) {
                binding.homeBottomSection.apply {
                    rvCategory.visibility = View.GONE
                    rvTransactions.visibility = View.VISIBLE
                    btnSort.visibility = View.VISIBLE
                    btnCategory.paintFlags =
                        Paint.UNDERLINE_TEXT_FLAG.inv() and btnCategory.paintFlags
                    btnTransactions.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                }
            } else {
                binding.homeBottomSection.apply {
                    rvCategory.visibility = View.VISIBLE
                    rvTransactions.visibility = View.GONE
                    btnSort.visibility = View.GONE
                    btnCategory.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                    btnTransactions.paintFlags =
                        Paint.UNDERLINE_TEXT_FLAG.inv() and btnTransactions.paintFlags
                }
            }
        })

        val sortButton = binding.homeBottomSection.btnSort
        val dropDownMenu = PopupMenu(activity, sortButton)
        dropDownMenu.inflate(R.menu.menu_sort_transactions)
        dropDownMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
            homeViewModel.onChangeSortBy(it.itemId)
            true
        })
        sortButton.setOnClickListener {
            dropDownMenu.show()
        }
    }
}