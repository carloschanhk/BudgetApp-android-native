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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.data.chart.BarChartAdapter
import com.example.budget.data.expense.Transaction
import com.example.budget.databinding.DialogAddTransactionBinding
import com.example.budget.ui.home.CategoryAdapter
import com.example.budget.ui.home.TransactionsAdapter
import com.example.budget.ui.spending_details.SpendingTransactionAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TransactionCreationDialogFragment() : BottomSheetDialogFragment() {
    private val viewModel: TransactionCreationDialogViewModel by viewModels()
    private var binding: DialogAddTransactionBinding? = null
    lateinit var etTransactionTitle: EditText
    lateinit var etTransactionCost: EditText
    lateinit var spTransactionCategory: Spinner
    lateinit var datePicker: DatePicker
    private val transactionCreationDialogFragmentArgs: TransactionCreationDialogFragmentArgs by navArgs()
    var targetedTransaction: Transaction? = null
    var category: String? = null
    private val calendar = Calendar.getInstance()
    private var transactionsAdapter: TransactionsAdapter? = null
    private var categoryAdapter: CategoryAdapter? = null
    private var spendingTransactionAdapter: SpendingTransactionAdapter? = null
    private var barChartAdapter: BarChartAdapter? = null


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
        transactionsAdapter =
            activity?.findViewById<RecyclerView>(R.id.rv_home_transactions)?.adapter as? TransactionsAdapter
        categoryAdapter =
            activity?.findViewById<RecyclerView>(R.id.rv_home_category)?.adapter as? CategoryAdapter
        spendingTransactionAdapter = activity?.findViewById<RecyclerView>(R.id.rv_details_transactions)?.adapter as? SpendingTransactionAdapter
        barChartAdapter = activity?.findViewById<RecyclerView>(R.id.rv_bar_chart)?.adapter as? BarChartAdapter

        binding?.apply {
            fragment = this@TransactionCreationDialogFragment
        }
        targetedTransaction = transactionCreationDialogFragmentArgs.transaction
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
        category = transactionCreationDialogFragmentArgs.category
        category?.let {
            spTransactionCategory.setSelection(
                resources.getStringArray(R.array.categories).indexOf(it)
            )
        }
    }

    fun onConfirm() {
        if (etTransactionCost.text.isNotEmpty() && etTransactionTitle.text.isNotEmpty()) {
            val calendar = Calendar.getInstance()
            datePicker.let {
                calendar.set(it.year, it.month, it.dayOfMonth)
            }
            if (targetedTransaction != null) {
                viewModel.updateTransaction(
                    targetedTransaction!!.apply {
                        category = spTransactionCategory.selectedItem.toString()
                        date = calendar.time
                        cost = etTransactionCost.text.toString().toFloat()
                        title = etTransactionTitle.text.toString()
                    }
                )
            } else {
                viewModel.createTransaction(
                    Transaction(
                        category = spTransactionCategory.selectedItem.toString(),
                        date = calendar.time,
                        cost = etTransactionCost.text.toString().toFloat(),
                        title = etTransactionTitle.text.toString()
                    )
                )
            }
            transactionsAdapter?.notifyDataSetChanged()
            categoryAdapter?.notifyDataSetChanged()
            spendingTransactionAdapter?.notifyDataSetChanged()
            barChartAdapter?.notifyDataSetChanged()
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