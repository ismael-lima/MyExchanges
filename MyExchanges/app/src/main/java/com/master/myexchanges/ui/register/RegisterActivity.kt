package com.master.myexchanges.ui.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.master.myexchanges.R
import com.master.myexchanges.domain.User
import com.master.myexchanges.ui.BaseActivity
import com.master.myexchanges.util.Constants
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.btnRegister
import kotlinx.android.synthetic.main.activity_register.password
import kotlinx.android.synthetic.main.activity_register.tilPassword
import kotlinx.android.synthetic.main.activity_register.tilUsername
import kotlinx.android.synthetic.main.activity_register.username

import org.koin.android.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity() {

    private val registerViewModel : RegisterViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setUpBaseComponents()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpBaseComponents()
    {
        progress = progressBar
        baseSnack = layout
    }
    private fun setUpObservers(){
        registerViewModel.name.observe(this, Observer {
            name.setText(it)
        })
        registerViewModel.nameError.observe(this, Observer {
            tilName.error = it
        })
        registerViewModel.username.observe(this, Observer {
            username.setText(it)
        })
        registerViewModel.usernameError.observe(this, Observer {
            tilUsername.error = it
        })
        registerViewModel.email.observe(this, Observer {
            email.setText(it)
        })
        registerViewModel.emailError.observe(this, Observer {
            tilEmail.error = it
        })
        registerViewModel.password.observe(this, Observer {
            password.setText(it)
        })
        registerViewModel.passwordError.observe(this, Observer {
            tilPassword.error = it
        })
        registerViewModel.confirmPassword.observe(this, Observer {
            confirmPassword.setText(it)
        })
        registerViewModel.confirmPasswordError.observe(this, Observer {
            tilConfirmPassowrd.error = it
        })
        registerViewModel.registerButtonEnabled.observe(this, Observer {
            btnRegister.isEnabled = it
        })
        registerViewModel.registerError.observe(this, Observer {
            Snackbar.make(btnRegister, it, Snackbar.LENGTH_SHORT).show()
        })
        registerViewModel.registerSuccess.observe(this, Observer {
            returnRegisteredUser(it)
        })
    }

    private fun setUpListeners(){
        name.setOnFocusChangeListener { _: View?, b: Boolean ->
            if(!b){
                registerViewModel.setName(name.text.toString())
            }
        }
        username.setOnFocusChangeListener { _: View?, b: Boolean ->
            if(!b){
                registerViewModel.setUsername(username.text.toString())
            }
        }
        email.setOnFocusChangeListener { _: View?, b: Boolean ->
            if(!b){
                registerViewModel.setEmail(email.text.toString())
            }
        }
        password.setOnFocusChangeListener { _: View?, b: Boolean ->
            if(!b){
                registerViewModel.setPassword(password.text.toString())
            }
        }
        confirmPassword.setOnFocusChangeListener { _: View?, b: Boolean ->
            if(!b){
                registerViewModel.setConfirmPassword(confirmPassword.text.toString())
            }
        }
        btnRegister.setOnClickListener {
            registerViewModel.register()
        }
    }

    private fun returnRegisteredUser(user: User){
        val resultIntent = Intent().apply {
            putExtra(Constants.Input.USER, user)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
