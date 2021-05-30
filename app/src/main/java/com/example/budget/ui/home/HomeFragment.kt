package com.example.budget.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.common.CategoryType
import com.example.budget.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val homeBinding = FragmentHomeBinding.inflate(inflater, container,false)
        binding = homeBinding
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.rv_transactions)
        recyclerView?.adapter = activity?.let { TransactionItemAdapter(it, CategoryType.values()) }
        recyclerView?.setHasFixedSize(true)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = homeViewModel
        }
    }
}