package com.master.myexchanges.ui.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.master.myexchanges.R
import com.master.myexchanges.domain.User
import com.master.myexchanges.ui.BaseActivity
import com.master.myexchanges.ui.main.MainActivity
import com.master.myexchanges.util.Constants
import com.master.myexchanges.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.koin.android.viewmodel.ext.android.viewModel

class SignInActivity : BaseActivity() {

    private val signInViewModel: SignInViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        setUpBaseComponents()
        setUpListeners()
        setUpObservers()
        setUpInitialData()
    }
    private fun setUpInitialData() {
        intent.extras?.let { bundle ->
            val user = bundle.getSerializable(Constants.Input.USER) as User
            user?.let{
                signInViewModel.setUsernameOrEmail(it.username)
                signInViewModel.setPassword(it.password)
            }
        }
    }

    private fun setUpBaseComponents()
    {
        progress = progressBar
        baseSnack = layout
    }

    override fun onResume() {
        super.onResume()
        signInViewModel.retrieveSignedUser()
    }

    private fun setUpObservers() {

        signInViewModel.observe(
            this,
            this::startWork,
            this::finishWork,
            this::showError
        )
        signInViewModel.usernameOrEmail.observe(this, Observer {
            username.setText(it)
        })
        signInViewModel.usernameOrEmailError.observe(this, Observer {
            tilUsername.error = it
        })
        signInViewModel.password.observe(this, Observer {
            password.setText(it)
        })
        signInViewModel.passwordError.observe(this, Observer {
            tilPassword.error = it
        })
        signInViewModel.signButtonEnabled.observe(this, Observer {
            btnRegister.isEnabled = it
        })
        signInViewModel.signInError.observe(this, Observer {
            Snackbar.make(btnRegister, it, Snackbar.LENGTH_SHORT).show()
        })
        signInViewModel.signInSuccess.observe(this, Observer {
            startMainActivity(it)
        })
    }

    private fun setUpListeners() {
        username.setOnFocusChangeListener { _: View?, b: Boolean ->
            if (!b) {
                signInViewModel.setUsernameOrEmail(username.text.toString())
            }
        }
        password.setOnFocusChangeListener { _: View?, b: Boolean ->
            if (!b) {
                signInViewModel.setPassword(password.text.toString())
            }
        }
        btnRegister.setOnClickListener {
            signInViewModel.signIn()
        }
        tvRegister.setOnClickListener { startRegisterActivity() }
    }

    private fun startMainActivity(user: User) {
        val intentLogin = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            putExtra(Constants.Input.USER, user)
        }
        startActivity(intentLogin)
    }

    private fun startRegisterActivity() {
        val i = Intent(this, RegisterActivity::class.java)
        startActivityForResult(i, Constants.Request.REGISTER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if((requestCode == Constants.Request.REGISTER)
            && (resultCode == Activity.RESULT_OK))
        {
            val user = data?.extras?.getSerializable(Constants.Input.USER) as User?
            user?.let{
                signInViewModel.setUsernameOrEmail(it.username)
                signInViewModel.setPassword(it.password)
                signInViewModel.signIn()
            }
        }
    }
}
