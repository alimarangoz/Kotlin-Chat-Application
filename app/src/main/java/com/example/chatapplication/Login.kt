package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtpassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)

        var isAllFieldsChecked = false

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtpassword.text.toString()

            isAllFieldsChecked = checkAllFields()

            if(isAllFieldsChecked){
                login(email,password)
            }

        }

    }

    private fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@Login, "User does not exist!",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkAllFields(): Boolean{
        if(edtEmail.length() == 0){

            edtEmail.error = "Email field is required!"
            return false

        }else if (edtpassword.length() == 0){

            edtpassword.error = "Password field is required!"
            return false
        }
        return true
    }

}