package com.example.budget.ui.home

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budget.common.CategoryType
import com.example.budget.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = homeViewModel
            context = activity
            categoryTypes = CategoryType.values().toList()
            homeBottomSection.rvTransactions.adapter = TransactionsAdapter()
            homeBottomSection.rvTransactions.addItemDecoration(DividerItemDecoration(activity,LinearLayoutManager.VERTICAL))
        }

        homeViewModel.showTransactions.observe(viewLifecycleOwner,{ choice ->
            if (choice){
                binding.homeBottomSection.svCategory.visibility = View.GONE
                binding.homeBottomSection.rvTransactions.visibility = View.VISIBLE
                binding.homeBottomSection.btnCategory.paintFlags = Paint.UNDERLINE_TEXT_FLAG.inv() and binding.homeBottomSection.btnCategory.paintFlags
                binding.homeBottomSection.btnTransactions.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            } else {
                binding.homeBottomSection.svCategory.visibility = View.VISIBLE
                binding.homeBottomSection.rvTransactions.visibility = View.GONE
                binding.homeBottomSection.btnCategory.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                binding.homeBottomSection.btnTransactions.paintFlags = Paint.UNDERLINE_TEXT_FLAG.inv() and binding.homeBottomSection.btnTransactions.paintFlags
            }
        })
    }
}