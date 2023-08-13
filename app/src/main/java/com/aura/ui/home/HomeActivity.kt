package com.aura.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aura.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity()
{

  private lateinit var binding: ActivityHomeBinding

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)

    binding = ActivityHomeBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val balance = binding.balance

    balance.text = "2654,54€"
  }

}
