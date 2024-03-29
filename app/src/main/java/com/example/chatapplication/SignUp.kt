package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var btnSignUp: Button
    private lateinit var edtName: EditText
    private lateinit var edtLastName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtpassword: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.edt_name)
        edtLastName = findViewById(R.id.edt_last_name)
        btnSignUp = findViewById(R.id.btnSignUp)
        edtEmail = findViewById(R.id.edt_email)
        edtpassword = findViewById(R.id.edt_password)

        var isAllFieldsChecked = false

        btnSignUp.setOnClickListener {
            val name = edtName.text.toString()
            val lastName = edtLastName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtpassword.text.toString()

            isAllFieldsChecked = checkAllFields()

            if(isAllFieldsChecked){
                signUp(name, lastName, email, password);
            }
        }
    }

    private fun signUp(name: String, lastName: String, email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDatabase(name,lastName, email ,mAuth.currentUser?.uid!!) //Null safe
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUp, "Error occurred try again!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkAllFields(): Boolean{

        if(!isValidEmail()){
            edtEmail.error = "Email format is wrong!"
        }

        if(edtName.length() == 0){

            edtName.error = "Name is required!"
            return false

        }else if (edtLastName.length() == 0){

            edtLastName.error = "Last is required!"
            return false

        }else if (edtEmail.length() == 0){

            edtEmail.error = "Email is required!"
            return false

        }else if (edtpassword.length() == 0) {

            edtpassword.error = "Password is required!"
            return false

        }else if (edtpassword.length() < 7) {

            edtpassword.error = "Password contains at least 8 characters."
            return false

        }

        return true
    }

    private fun isValidEmail(): Boolean{
        val getEmail = edtEmail.text
        return Patterns.EMAIL_ADDRESS.matcher(getEmail).matches()
    }


    private fun addUserToDatabase(name: String, lastName: String, email: String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name,lastName,email,uid))
    }
}