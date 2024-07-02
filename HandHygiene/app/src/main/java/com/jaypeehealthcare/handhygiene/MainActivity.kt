package com.jaypeehealthcare.handhygiene

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jaypeehealthcare.handhygiene.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var icuArrayList : ArrayList<IcuType>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.ICUList.layoutManager = LinearLayoutManager(this)
        binding.ICUList.setHasFixedSize(true)

        icuArrayList = arrayListOf<IcuType>()
        getIcuList()

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

    private fun getIcuList() {
        databaseReference = FirebaseDatabase.getInstance().getReference("ICUs")

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(icuSnapshot in snapshot.children){
                        val icuName = icuSnapshot.getValue(IcuType::class.java)
                        icuArrayList.add(icuName!!)
                    }
                }

                val icuAdapter = IcuTypeAdapter(icuArrayList)
                binding.ICUList.adapter = icuAdapter
                icuAdapter.setOnItemClickListener(object : IcuTypeAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        val intent = Intent(this@MainActivity, IcuStaffEntryActivity::class.java)
                        intent.putExtra("icuName", icuArrayList[position].icuType)
                        startActivity(intent)
                    }

                })
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}