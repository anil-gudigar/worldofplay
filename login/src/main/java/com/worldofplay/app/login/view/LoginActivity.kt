package com.worldofplay.app.login.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.worldofplay.app.login.R
import com.worldofplay.app.login.domian.LoggedInUserView
import com.worldofplay.app.login.viewmodel.LoginViewModel
import com.worldofplay.app.login.viewmodel.LoginViewModelFactory
import com.worldofplay.app.stylekit.themes.Themes
import com.worldofplay.app.stylekit.themes.view.ThemesActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : ThemesActivity() {

    private lateinit var loginViewModel: LoginViewModel
    override val layout: Int = R.layout.activity_login

    override fun initUI() {
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProviders.of(
            this,
            LoginViewModelFactory()
        ).get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer
            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                etUsernameLayout.error = getString(loginState.usernameError)
            } else {
                etUsernameLayout.error = ""
            }
            if (loginState.passwordError != null) {
                etPasswordLayout.error = getString(loginState.passwordError)
            } else {
                etPasswordLayout.error = ""
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }

        switchTheme.isChecked = mTheme == Themes.DARK_BLUE
        switchTheme.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            getPreferences(Context.MODE_PRIVATE).edit().putBoolean("nightMode", mIsNightMode)
                .apply()
            compoundButton.postDelayed(Runnable {
                if (b) {
                    setTheme(Themes.getThemeId(Themes.DARK_BLUE))
                    mPreferences.edit().putInt("themeId", Themes.DARK_BLUE).apply()
                } else {
                    setTheme(Themes.getThemeId(Themes.LIGHT_BLUE))
                    mPreferences.edit().putInt("themeId", Themes.LIGHT_BLUE).apply()
                }
                recreate()
            }, 400)
        })
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val intent = Intent("ACTION_EXECUTE")
        intent.setPackage(packageName)
        intent.putExtra("ACTION", "HOME")
        applicationContext.sendBroadcast(intent)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
