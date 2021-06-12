package com.example.budget.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.budget.R
import com.example.budget.data.budget.MonthBudget
import com.example.budget.repository.BudgetRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SetBudgetDialogFragment : DialogFragment() {
    @Inject
    lateinit var budgetRepository: BudgetRepository

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { it ->
            val dialogBuilder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_set_month_budget, null)
            val etBudgetInput: EditText = dialogView.findViewById(R.id.et_budget_input)
            val spMonthInput: Spinner = dialogView.findViewById(R.id.spinner_set_month)
            dialogBuilder.setView(dialogView)
                .setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
                    if (etBudgetInput.text.toString().isNotBlank()) {
                        lifecycleScope.launch(Dispatchers.IO) {
                            budgetRepository?.setBudget(
                                monthBudget = MonthBudget(
                                    spMonthInput.selectedItem.toString(),
                                    etBudgetInput.text.toString().toInt()
                                )
                            )
                        }
                    }
                })
                .setNegativeButton(
                    "Cancel",
                    DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
            dialogBuilder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}