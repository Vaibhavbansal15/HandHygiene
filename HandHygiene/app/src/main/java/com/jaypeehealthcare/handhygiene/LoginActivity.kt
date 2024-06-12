package com.jaypeehealthcare.handhygiene

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jaypeehealthcare.handhygiene.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding : ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var auth : FirebaseAuth

    override fun onStart() {
        super.onStart()
        val currUser : FirebaseUser? = auth.currentUser
        val verified = auth.currentUser?.isEmailVerified
        if(currUser != null && verified == true){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.loginFooterSignup.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }

        binding.resetPassword.setOnClickListener{
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }

        binding.loginButton.setOnClickListener {

            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please Enter all the Details!", Toast.LENGTH_LONG).show()
            } else{
                val progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Logging In")
                progressDialog.setCancelable(false)
                progressDialog.show()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){task->
                        if(task.isSuccessful){
                            if(auth.currentUser?.isEmailVerified!!){
                                progressDialog.dismiss()
                                Toast.makeText(this, "Successfully Logged in.", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else{
                                Toast.makeText(this, "Please verify your email", Toast.LENGTH_LONG).show()
                                progressDialog.dismiss()
                            }
                        } else{
                            progressDialog.dismiss()
                            Toast.makeText(this, "Login Failed : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }
}