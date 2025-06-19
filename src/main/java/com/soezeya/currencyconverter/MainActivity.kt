package com.soezeya.currencyconverter

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.soezeya.currencyconverter.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentFromCurrency = "USD"
    private var currentToCurrency = "EUR"
    private val conversionHistory = mutableListOf<ConversionHistory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCurrencyDropdowns()
        setupButtonListeners()
    }

    private fun setupCurrencyDropdowns() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.currencies,
            android.R.layout.simple_dropdown_item_1line
        )

        // Configure From dropdown
        binding.spinnerFrom.setAdapter(adapter)
        binding.spinnerFrom.setText("USD", false)
        binding.spinnerFrom.setOnItemClickListener { _, _, position, _ ->
            currentFromCurrency = adapter.getItem(position)?.toString() ?: "USD"
        }

        // Configure To dropdown
        binding.spinnerTo.setAdapter(adapter)
        binding.spinnerTo.setText("EUR", false)
        binding.spinnerTo.setOnItemClickListener { _, _, position, _ ->
            currentToCurrency = adapter.getItem(position)?.toString() ?: "EUR"
        }
    }

    private fun setupButtonListeners() {
        binding.btnConvert.setOnClickListener {
            try {
                val amountText = binding.etAmount.text.toString()
                if (amountText.isEmpty()) {
                    Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val amount = amountText.toFloat()
                val convertedAmount = convertCurrency(amount, currentFromCurrency, currentToCurrency)
                val resultText = "%.2f %s".format(convertedAmount, currentToCurrency)
                binding.tvResult.text = resultText

                // Save to history
                val historyItem = ConversionHistory(
                    "%.2f %s".format(amount, currentFromCurrency),
                    resultText,
                    SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(Date())
                )
                conversionHistory.add(historyItem)
                Toast.makeText(this, "Conversion saved", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnHistory.setOnClickListener {
            if (conversionHistory.isEmpty()) {
                Toast.makeText(this, "No history yet", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, HistoryActivity::class.java).apply {
                    putParcelableArrayListExtra("history", ArrayList(conversionHistory))
                }
                startActivity(intent)
            }
        }

        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun convertCurrency(amount: Float, from: String, to: String): Float {
        val fromRate = ExchangeRates.rates[from] ?: 1.0f
        val toRate = ExchangeRates.rates[to] ?: 1.0f
        return (amount / fromRate) * toRate
    }
}