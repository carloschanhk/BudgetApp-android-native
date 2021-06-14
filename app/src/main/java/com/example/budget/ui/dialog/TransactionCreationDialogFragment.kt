package com.example.budget.ui.dialog

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.budget.R
import com.example.budget.data.expense.Transaction
import com.example.budget.databinding.DialogAddTransactionBinding
import com.example.budget.repository.BudgetRepository
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class TransactionCreationDialogFragment : BottomSheetDialogFragment() {
    @Inject
    lateinit var budgetRepository: BudgetRepository
    private var binding: DialogAddTransactionBinding? = null
    lateinit var etTransactionTitle: EditText
    lateinit var etTransactionCost: EditText
    lateinit var spTransactionCategory: Spinner
    lateinit var datePicker: DatePicker
    lateinit var flow: List<Transaction>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddTransactionBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etTransactionCost = view.findViewById(R.id.et_transaction_cost)
        etTransactionTitle = view.findViewById(R.id.et_transaction_title)
        spTransactionCategory = view.findViewById(R.id.spinner_set_category)
        datePicker = view.findViewById(R.id.date_picker)

        binding?.apply {
            fragment = this@TransactionCreationDialogFragment
        }
    }

    fun onConfirm() {
        if (etTransactionCost.text.isNotEmpty() && etTransactionTitle.text.isNotEmpty()) {
            val calendar = Calendar.getInstance()
            datePicker.let {
                calendar.set(it.year, it.month, it.dayOfMonth)
            }
            lifecycleScope.launch(Dispatchers.IO) {
                budgetRepository.createTransaction(
                    Transaction(
                        spTransactionCategory.selectedItem.toString(),
                        calendar.time,
                        etTransactionCost.text.toString().toFloat(),
                        etTransactionTitle.text.toString()
                    )
                )
            }
            findNavController().navigateUp()
        } else {
            if (etTransactionTitle.text.isEmpty()){
                etTransactionTitle.error = "Please enter the title"
            }
            if (etTransactionCost.text.isEmpty()){
                etTransactionCost.error = "Please enter the cost"
            }
        }

    }

    fun onCancel() {
        lifecycleScope.launch(Dispatchers.IO) {
            val list = budgetRepository.getAllTransactions()
            Log.d("TRANSACTION DIALOG", "non-flow: $list")
        }
        findNavController().navigateUp()
    }
}