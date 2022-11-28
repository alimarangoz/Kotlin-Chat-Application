package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var edtName: EditText
    private lateinit var edtLastName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.edt_name)
        edtLastName = findViewById(R.id.edt_last_name)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)
        edtEmail = findViewById(R.id.edt_email)
        edtpassword = findViewById(R.id.edt_password)

        btnSignUp.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtpassword.text.toString()

            signUp(email,password);
        }

    }

    private fun signUp(email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUp, "Error occurred try again!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}