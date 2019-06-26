package di2k.csi.androloginv15k.main

//import android.support.v7.app.AppCompatActivity
//import di2k.csi.androloginv15k.network.getAPIService
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import di2k.csi.androloginv15k.R
import di2k.csi.androloginv15k.network.ApiService
import di2k.csi.androloginv15k.network.UtilsAPI
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInActivity : AppCompatActivity() {

    private lateinit var editTextEmail : EditText
    private lateinit var editTextPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var btnRegister : TextView
    private lateinit var btnShowPassword : CheckBox
    private lateinit var logForm : RelativeLayout
    private lateinit var APIku : ApiService
    private lateinit var loadingProgressBar: ContentLoadingProgressBar
    private lateinit var mContext : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        APIku = UtilsAPI.apiService;

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        btnLogin = findViewById(R.id.buttonLogin)
        btnRegister = findViewById(R.id.textViewRegister)
        btnShowPassword = findViewById(R.id.showPass)

        logForm = findViewById(R.id.loginForm)

        editTextEmail.setText("dm@gmail.com")
        editTextPassword.setText("mencoba123")

        //session login
        if (SessionActivity.getLoggedStatus(getApplicationContext())) {
            val intent = Intent(getApplicationContext(), ProfileActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            logForm!!.setVisibility(View.VISIBLE)
        }

        //btnshow password
        btnShowPassword!!.setOnClickListener(object : View.OnClickListener {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            override fun onClick(v: View) {
                if (btnShowPassword!!.isChecked()) {
                    editTextPassword!!.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                } else {
                    editTextPassword!!.setTransformationMethod(PasswordTransformationMethod.getInstance())
                }

            }
        })

        mContext = this;

        btnLogin!!.setOnClickListener(object : View.OnClickListener{
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            override fun onClick(v: View){
                val email = editTextEmail!!.getText().toString().trim()
                val password = editTextPassword!!.getText().toString().trim()

                if (email.isEmpty()) {
                    editTextEmail!!.setError("Email is required")
                    editTextEmail!!.requestFocus()
                    return
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail!!.setError("Enter a valid email")
                    editTextEmail!!.requestFocus()
                    return
                }

                if (password.isEmpty()) {
                    editTextPassword!!.setError("Password is required")
                    editTextPassword!!.requestFocus()
                    return
                }

                if (password.length < 6) {
                    editTextPassword!!.setError("Password Should be at least 6 character long")
                    editTextPassword!!.requestFocus()
                    return
                }

                APIku!!.userlogin(email, password)
                    .enqueue(object : Callback<ResponseBody> {
                        /**
                         * Invoked when a network exception occurred talking to the server or when an unexpected
                         * exception occurred creating the request or processing the response.
                         */
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(this@SignInActivity, "Test Failed Link", Toast.LENGTH_SHORT).show()
                        }

                        @Override
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if(response.isSuccessful()){
                                try{
                                    Toast.makeText(this@SignInActivity,"Login Success", Toast.LENGTH_SHORT).show()
                                    val jsonRESULT = JSONObject(response.body()?.string())

                                    startActivity(Intent(this@SignInActivity, ProfileActivity::class.java)
                                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
                                    SessionActivity.setLoggedIn(getApplicationContext(),true)
                                    finish()
                                } catch (e: JSONException){
                                    e.printStackTrace()
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"Login Failure, please check connection",Toast.LENGTH_SHORT).show()
                            }

                        }
                    })

            }

        })

        btnRegister!!.run {
            setOnClickListener(object : View.OnClickListener{
                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                override fun onClick(v: View){
                    val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
                    startActivity(intent)
                }
            })
        }

    }

    @Override
    private fun OnCreateOptionMenu(menu: Menu): Boolean{
        val inflater = getMenuInflater()
        inflater.inflate(R.menu.optionmenu, menu)
        return true
    }

    private fun OnOptionItemSelected(item: MenuItem): Boolean{
        if(item.getItemId() == R.id.btnhome){
            startActivity(Intent(this@SignInActivity, MainActivity::class.java))
        }else if(item.getItemId() == R.id.btnLogin){
            startActivity(Intent(this@SignInActivity, SignInActivity::class.java))
        }else if(item.getItemId() == R.id.btnRegister) {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }else if(item.getItemId() == R.id.btnHelp) {
            //startActivity(Intent(this@SignInActivity, Example:class.java))
        }
        return true
    }
}
