package com.example.budget.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.budget.R

class SetBudgetDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { it ->
            val dialogBuilder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            dialogBuilder.setView(inflater.inflate(R.layout.dialog_set_month_budget, null))
                .setPositiveButton("OK", DialogInterface.OnClickListener { _, _ -> })
                .setNegativeButton(
                    "Cancel",
                    DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
            dialogBuilder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}