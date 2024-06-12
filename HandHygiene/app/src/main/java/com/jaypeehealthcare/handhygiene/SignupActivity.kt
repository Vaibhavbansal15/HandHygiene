package com.jaypeehealthcare.handhygiene

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.jaypeehealthcare.handhygiene.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private val binding : ActivitySignupBinding by lazy{
        ActivitySignupBinding.inflate(layoutInflater)
    }
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.signupFooterLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        auth = FirebaseAuth.getInstance()

        binding.signupBtn.setOnClickListener {
            val id  = binding.signupId.text.toString()
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()

            if(id.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please Enter All the Details", Toast.LENGTH_SHORT).show()
            } else{
                val progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Signing up")
                progressDialog.setCancelable(false)
                progressDialog.show()

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {task->
                        if(task.isSuccessful){
                            progressDialog.dismiss()
                            Toast.makeText(this, "Signup Successful!", Toast.LENGTH_SHORT).show()
                            auth.currentUser?.sendEmailVerification()!!
                                .addOnCompleteListener(this) {
                                    if(it.isSuccessful){
                                        Toast.makeText(this, "Verify your email", Toast.LENGTH_LONG).show()
                                        startActivity(Intent(this, LoginActivity::class.java))
                                        finish()
                                    } else{
                                        Toast.makeText(this, "Verification Failed : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }else{
                            Toast.makeText(this, "Registration Failed : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}