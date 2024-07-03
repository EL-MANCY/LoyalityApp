package com.example.bank.core

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater

import androidx.appcompat.app.AlertDialog
import com.example.bank.databinding.LoadingDialog2Binding

object DialogHelper {

    var loadingDialog: Dialog? = null;


    fun showLoadingDialog(activity: Activity, title: String) {
        activity.runOnUiThread {
            val dialogView = LoadingDialog2Binding.inflate(LayoutInflater.from(activity))
            dialogView.tvContent.text = title;
            val dialogBuilder = AlertDialog.Builder(activity)
                .setView(dialogView.root)
                .setTitle("Loading")

            loadingDialog?.dismiss();
            loadingDialog = dialogBuilder.show()

        }
    }
    fun hideLoading(activity: Activity) {
        activity.runOnUiThread {
            loadingDialog?.dismiss()
        }
    }
}