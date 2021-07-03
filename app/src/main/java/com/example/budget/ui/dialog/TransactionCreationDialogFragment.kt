package com.example.budget.ui.dialog

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.data.expense.Transaction
import com.example.budget.databinding.DialogAddTransactionBinding
import com.example.budget.repository.BudgetRepository
import com.example.budget.ui.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class TransactionCreationDialogFragment() : BottomSheetDialogFragment() {
    @Inject
    lateinit var budgetRepository: BudgetRepository
    private val homeViewModel: HomeViewModel by activityViewModels<HomeViewModel>()
    private var binding: DialogAddTransactionBinding? = null
    lateinit var etTransactionTitle: EditText
    lateinit var etTransactionCost: EditText
    lateinit var spTransactionCategory: Spinner
    lateinit var datePicker: DatePicker
    var targetedTransaction: Transaction? = null
    private val calendar = Calendar.getInstance()
    private lateinit var listAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        listAdapter = activity?.findViewById<RecyclerView>(R.id.rv_transactions)?.adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>

        binding?.apply {
            fragment = this@TransactionCreationDialogFragment
        }
        targetedTransaction = homeViewModel.targetedTransaction
        targetedTransaction?.let {
            etTransactionTitle.setText(it.title)
            etTransactionCost.setText(it.cost.toString())
            calendar.time = it.date
            datePicker.updateDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            spTransactionCategory.setSelection(
                resources.getStringArray(R.array.categories).indexOf(it.category)
            )
        }
    }

    override fun dismiss() {
        super.dismiss()
        homeViewModel.targetedTransaction = null
    }

    fun onConfirm() {
        if (etTransactionCost.text.isNotEmpty() && etTransactionTitle.text.isNotEmpty()) {
            val calendar = Calendar.getInstance()
            datePicker.let {
                calendar.set(it.year, it.month, it.dayOfMonth)
            }
            if (targetedTransaction != null) {
                homeViewModel.updateTransaction(
                    targetedTransaction!!.apply {
                        category = spTransactionCategory.selectedItem.toString()
                        date = calendar.time
                        cost = etTransactionCost.text.toString().toFloat()
                        title = etTransactionTitle.text.toString()
                    }
                )
            } else {
                homeViewModel.createTransaction(
                    Transaction(
                        category = spTransactionCategory.selectedItem.toString(),
                        date = calendar.time,
                        cost = etTransactionCost.text.toString().toFloat(),
                        title = etTransactionTitle.text.toString()
                    )
                )
            }
            listAdapter.notifyDataSetChanged()
            findNavController().navigateUp()
        } else {
            if (etTransactionTitle.text.isEmpty()) {
                etTransactionTitle.error = "Please enter the title"
            }
            if (etTransactionCost.text.isEmpty()) {
                etTransactionCost.error = "Please enter the cost"
            }
        }

    }

    fun onCancel() {
        findNavController().navigateUp()
    }
}