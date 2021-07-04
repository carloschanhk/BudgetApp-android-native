package com.example.budget.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.data.budget.MonthBudget
import com.example.budget.databinding.DialogSetMonthBudgetBinding
import com.example.budget.repository.BudgetRepository
import com.example.budget.ui.home.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SetBudgetDialogFragment : DialogFragment() {
    @Inject
    lateinit var budgetRepository: BudgetRepository
    private var binding: DialogSetMonthBudgetBinding? = null
    lateinit var etBudgetInput: EditText
    lateinit var spMonthInput: Spinner
    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSetMonthBudgetBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etBudgetInput = view.findViewById(R.id.et_budget_input)
        spMonthInput = view.findViewById(R.id.spinner_set_month)
        categoryAdapter = activity?.findViewById<RecyclerView>(R.id.rv_category)?.adapter as CategoryAdapter
        binding?.apply {
            fragment = this@SetBudgetDialogFragment
        }
    }

    fun onConfirmButton() {
        if (etBudgetInput.text.toString().isNotBlank()) {
            lifecycleScope.launch(Dispatchers.IO) {
                budgetRepository.setBudget(
                    monthBudget = MonthBudget(
                        spMonthInput.selectedItem.toString(),
                        etBudgetInput.text.toString().toInt()
                    )
                )
            }
            categoryAdapter.notifyDataSetChanged()
            findNavController().navigateUp()
        } else {
            etBudgetInput.error = "Please enter the budget"
        }
    }

    fun onCancelButton(){
        etBudgetInput.text.clear()
        findNavController().navigateUp()
    }
}