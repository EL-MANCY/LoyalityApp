package com.example.bank.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bank.databinding.ActivityMainBinding
import com.example.bank.core.DialogHelper
import com.example.bank.core.FlowDataObject
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val receivedIntent = intent
        if (receivedIntent?.action == Intent.ACTION_SEND && receivedIntent.type == "application/json") {
            val receivedJson = receivedIntent.getStringExtra(Intent.EXTRA_TEXT)
            receivedJson?.let {
                val receivedObject = Gson().fromJson(it, FlowDataObject::class.java)
                FlowDataObject.getInstance().amount = receivedObject.amount - (receivedObject.amount * 0.10f)
            }
        }

        binding.btnDiscount.setOnClickListener {
            val flowDataJson = FlowDataObject.toJson()

            val resultIntent = Intent().apply {
                putExtra(Intent.EXTRA_TEXT, flowDataJson)
            }
            setResult(RESULT_OK, resultIntent)

            Thread {
                DialogHelper.showLoadingDialog(this, "Calculating Your Discount")
                Thread.sleep(2000)
                DialogHelper.hideLoading(this)
                this.runOnUiThread {
                    finish()
                }
            }.start()
        }
    }
}
