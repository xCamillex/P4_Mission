package com.aura.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aura.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity()
{

  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)

    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val username = binding.username
    val password = binding.password
    val login = binding.login
    val loading = binding.loading

    login.setOnClickListener {
      loading.visibility = View.VISIBLE
    }
  }

}
