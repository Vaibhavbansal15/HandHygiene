package com.jaypeehealthcare.handhygiene

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.jaypeehealthcare.handhygiene.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {
    private val binding : ActivityResetPasswordBinding by lazy{
        ActivityResetPasswordBinding.inflate(layoutInflater)
    }
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.resetPasswordBtn.setOnClickListener{
            val email = binding.resetEditText.text.toString()

            if(email.isEmpty()){
                Toast.makeText(this, "Please Enter the email!", Toast.LENGTH_SHORT).show()
            } else{
                val progressDialog = ProgressDialog(this)
                progressDialog.setMessage("sending link to your mail")
                progressDialog.setCancelable(false)
                progressDialog.show()

                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(this){task->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Check your mail!!", Toast.LENGTH_SHORT).show()
                            binding.resetEditText.text.clear()
                            progressDialog.dismiss()
                        } else{
                            Toast.makeText(this, "Failed to reset : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                    }
            }
        }

    }
}