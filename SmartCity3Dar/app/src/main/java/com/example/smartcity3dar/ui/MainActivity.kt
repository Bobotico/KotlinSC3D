package com.example.smartcity3dar.ui

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
import com.example.smartcity3dar.R
import com.example.smartcity3dar.databinding.ActivityMainBinding
import com.example.smartcity3dar.ui.home.LoginViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)
        val signInButton: Button = findViewById(R.id.signInButton)

        signInButton.setOnClickListener {
            val view: View = layoutInflater.inflate(R.layout.modal_login, null)
            val modal = BottomSheetDialog(this)
            modal.setContentView(view)
            modal.show()
            val userNameTF: EditText? = modal.findViewById(R.id.userNameTF)
            val passTF: EditText? = modal.findViewById(R.id.passwordTF)
            val loginButton: Button? = modal.findViewById(R.id.loginButton)
            loginButton?.setOnClickListener {
                val inputUN = userNameTF?.text.toString()
                val inputPass = passTF?.text.toString()
                loginViewModel.login(inputUN, inputPass)
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
}