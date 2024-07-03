package com.example.bank.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bank.databinding.ActivityMainBinding
import com.example.bank.core.DialogHelper

class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var newAmount = 0.0f

        val receivedIntent = intent
        if (receivedIntent?.action == Intent.ACTION_SEND && receivedIntent.type == "text/plain") {
            val receivedAmount = receivedIntent.getStringExtra(Intent.EXTRA_TEXT)
            receivedAmount?.let {
                newAmount = it.toFloat() - (it.toFloat() * 0.10f)
            }
        }


        binding.btnDiscount.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra(Intent.EXTRA_TEXT, newAmount.toString())
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
