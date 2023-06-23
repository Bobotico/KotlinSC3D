package com.example.smartcity3dar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartcity3dar.NavigationActivity
import com.example.smartcity3dar.R
import com.example.smartcity3dar.databinding.ActivityMainBinding
import com.example.smartcity3dar.ui.home.LoginViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var webView: WebView
    private lateinit var loginViewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)
        val signInButton: Button = findViewById(R.id.signInButton)

        signInButton.setOnClickListener {
            var view: View = layoutInflater.inflate(R.layout.modal_login, null)
            val modal = BottomSheetDialog(this)
            modal.setContentView(view)
            modal.show()
            val userNameTF: EditText? = modal.findViewById(R.id.userNameTF)
            val passTF: EditText? = modal.findViewById(R.id.passwordTF)
            val loginButton: Button? = modal.findViewById(R.id.loginButton)
            loginButton?.setOnClickListener {
                val inputUN = userNameTF?.text.toString()
                val inputPass = passTF?.text.toString()
                loginAsync(inputUN, inputPass)
            }
        }

        val registerButton : Button = findViewById(R.id.registerButton)
        registerButton.setOnClickListener{
            // Create a new WebView instance
            /*webView = WebView(this)

           // Configure the WebView
           webView.settings.javaScriptEnabled = true
           webView.webViewClient = WebViewClient()
           webView.loadUrl("https://www.google.com/")

           setContentView(webView)*/

            webView = WebView(this)
            webView.visibility = VISIBLE
            webView.settings.javaScriptEnabled = true
            webView.canGoBack()
            webView.loadUrl("https://www.google.com/")
            setContentView(webView)
        }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(webView.isVisible){
                    webView.goBack()
                    setContentView(binding.root)
                }else{
                    onBackPressed()
                }
            }

        })

        /*val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/
    }
    fun loginAsync(inputUN : String, inputPass : String) {
            loginViewModel.viewModelScope.launch(Dispatchers.Default) {
                val loginResult = loginViewModel.login(inputUN, inputPass)
                if (loginResult.SessionID != null && loginResult.Data != null) {
                    val intent = Intent(this@MainActivity, NavigationActivity::class.java)
                    startActivity(intent)
                }else{
                    println("Errore, login non effettuato")
                }
            }
    }

}