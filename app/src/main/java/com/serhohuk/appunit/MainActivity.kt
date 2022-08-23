package com.serhohuk.appunit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private lateinit var tvCryptoInfo : TextView
    private var viewModel : MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCryptoInfo = findViewById(R.id.tv_crypto_info)
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel?.getCryptoData(Constants.TWT)

        lifecycleScope.launchWhenStarted {
            viewModel!!.cryptoFlow.collectLatest {
                when(it){
                    is Resource.Succes ->{
                        tvCryptoInfo.text = it.data.toString()
                    }
                    is Resource.Error ->{
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading ->{
                        Toast.makeText(this@MainActivity, "Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }
}