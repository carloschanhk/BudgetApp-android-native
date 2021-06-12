package com.example.budget.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.budget.R
import com.example.budget.databinding.DialogAddTransactionBinding
import com.example.budget.repository.BudgetRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TransactionCreationDialogFragment: DialogFragment() {
    @Inject
    lateinit var budgetRepository: BudgetRepository
    private var binding: DialogAddTransactionBinding? = null
    lateinit var etTransactionTitle: EditText
    lateinit var etTransactionCost: EditText
    lateinit var spTransactionCategory: Spinner
    lateinit var tvDatePicker: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddTransactionBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etTransactionCost = view.findViewById(R.id.et_transaction_cost)
        etTransactionTitle = view.findViewById(R.id.et_transaction_title)
        binding?.apply{
            fragment = this@TransactionCreationDialogFragment
        }
    }
}