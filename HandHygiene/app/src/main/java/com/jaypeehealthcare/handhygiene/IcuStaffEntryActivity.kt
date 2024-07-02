package com.jaypeehealthcare.handhygiene

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jaypeehealthcare.handhygiene.databinding.ActivityIcuStaffEntryBinding

class IcuStaffEntryActivity : AppCompatActivity() {

    private val binding : ActivityIcuStaffEntryBinding by lazy {
        ActivityIcuStaffEntryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val bundle :Bundle? = intent.extras
        binding.entryIcuName.text = bundle!!.getString("icuName")

        binding.addEntryBtn.setOnClickListener {
//            startActivity(Intent(this, addEntryActivity::class.java))
        }
    }
}