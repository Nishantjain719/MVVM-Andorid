package com.example.mvvmstart.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmstart.R
import com.example.mvvmstart.data.db.AppDatabase
import com.example.mvvmstart.data.db.entities.User
import com.example.mvvmstart.data.network.MyApi
import com.example.mvvmstart.data.network.NetworkConnectionInterceptor
import com.example.mvvmstart.data.repositories.UserRepository
import com.example.mvvmstart.databinding.ActivityLoginBinding
import com.example.mvvmstart.ui.home.HomeActivity
import com.example.mvvmstart.util.hide
import com.example.mvvmstart.util.show
import com.example.mvvmstart.util.snackbar
import com.example.mvvmstart.util.toast

class LoginActivity : AppCompatActivity(), AuthListener {

    private var progressBar: ProgressBar? = null
    private var mCoordinatorLayout: CoordinatorLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(this)
        val repository = UserRepository(api, db)
        val factory = AuthViewModelFactory(repository)
        // we need to get callback from ViewModel to this activity to show errors or success message, so we implement AuthListener Interface.
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        binding.viewmodel = viewModel

        viewModel.authListener = this
        progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        mCoordinatorLayout = findViewById<CoordinatorLayout>(R.id.root_layout);
        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if(user != null){
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onStarted() {

        progressBar?.show()
    }

    override fun onSuccess(user: User) {
        progressBar?.hide()
    }

    override fun onFailure(message: String) {
        progressBar?.hide()
        mCoordinatorLayout?.snackbar(message)
    }


}