package com.jaypeehealthcare.handhygiene

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.jaypeehealthcare.handhygiene.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.logoutButton.setOnClickListener{

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Signing out")
            progressDialog.setCancelable(false)
            progressDialog.show()
            if(auth.currentUser != null) {
                auth.signOut()
                progressDialog.dismiss()
                Toast.makeText(this, "Sign out Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else{
                progressDialog.dismiss()
                Toast.makeText(this, "No user Logged in", Toast.LENGTH_SHORT).show()
            }
        }
    }
}