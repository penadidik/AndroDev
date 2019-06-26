package di2k.csi.androloginv15k.main

//import android.support.v7.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import di2k.csi.androloginv15k.R
import di2k.csi.androloginv15k.model.CSignUp
import di2k.csi.androloginv15k.network.ApiService
import di2k.csi.androloginv15k.network.UtilsAPI
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {

    //variable
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: TextView
    private lateinit var fullname: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var cpassword: EditText
    private lateinit var btnPass: CheckBox

    private lateinit var mContext: Context
    private lateinit var APIku: ApiService


    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btnLogin = findViewById(R.id.textViewLogin)
        btnRegister = findViewById(R.id.buttonRegister)
        fullname = findViewById(R.id.editTextName)
        email = findViewById(R.id.editTextEmail)
        password = findViewById(R.id.editTextPassword)
        cpassword = findViewById(R.id.editTextCPassword)
        btnPass = findViewById(R.id.showPass)

        mContext = this@SignUpActivity
        APIku = UtilsAPI.apiService;

        btnPass!!.setOnClickListener(object : View.OnClickListener{
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            override fun onClick(v: View){
                if (btnPass!!.isChecked()) {
                    password!!.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                    cpassword!!.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                } else {
                    password!!.setTransformationMethod(PasswordTransformationMethod.getInstance())
                    cpassword!!.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                }
            }
        })

        btnRegister!!.setOnClickListener(object : View.OnClickListener{
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            override fun onClick(v: View){
                val Nfullname = fullname!!.getText().toString().trim()
                val Nemail = email!!.getText().toString().trim()
                val Npassword = password!!.getText().toString().trim()
                val NCpassword = cpassword!!.getText().toString().trim()

                if (Nfullname.isEmpty()) {
                    fullname!!.setError("Fullname is required")
                    fullname!!.requestFocus()
                    return
                }

                if (Nemail.isEmpty()) {
                    email!!.setError("Email is required")
                    email!!.requestFocus()
                    return
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(Nemail).matches()) {
                    email!!.setError("Enter a valid email")
                    email!!.requestFocus()
                    return
                }

                if (Npassword.isEmpty()) {
                    password!!.setError("Password is required")
                    password!!.requestFocus()
                    return
                }

                if (Npassword.length < 6) {
                    password!!.setError("Password Should be at least 6 character long")
                    password!!.requestFocus()
                    return
                }

                if (NCpassword.isEmpty()) {
                    cpassword!!.setError("Password is required")
                    cpassword!!.requestFocus()
                    return
                }

                if (NCpassword.length < 6) {
                    cpassword!!.setError("Password Should be at least 6 character long")
                    cpassword!!.requestFocus()
                    return
                }

                APIku!!.createUser(Nfullname, Nemail, Npassword, NCpassword)
                    .enqueue(object : Callback<CSignUp> {
                        /**
                         * Invoked when a network exception occurred talking to the server or when an unexpected
                         * exception occurred creating the request or processing the response.
                         */
                        override fun onFailure(call: Call<CSignUp>, t: Throwable) {
                            Toast.makeText(this@SignUpActivity, "Test Failed Link", Toast.LENGTH_SHORT).show()
                        }

                        @Override
                        override fun onResponse(call: Call<CSignUp>, response: Response<CSignUp>){
                            if(response.isSuccessful()){
                                try{
                                    Toast.makeText(this@SignUpActivity, "Register Success, Already Sign In now", Toast.LENGTH_SHORT).show()
                                    val jsonRESULT = JSONObject(response.body().toString())
                                    startActivity(Intent(this@SignUpActivity, SignInActivity::class.java)
                                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
                                    SessionActivity.setLoggedIn(getApplicationContext(), true)
                                    finish()
                                } catch(e: JSONException){
                                    e.printStackTrace()
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Login Failure, please check connection", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

            }
        })
    }

}