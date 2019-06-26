package di2k.csi.androloginv15k.main

//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.Toolbar
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import di2k.csi.androloginv15k.R
import di2k.csi.androloginv15k.network.ApiService

class ProfileActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: ImageView
    private lateinit var menuLogout: Menu
    private lateinit var btnLogout: Button
    private lateinit var fullname: TextView
    private lateinit var email: TextView
    private lateinit var password: TextView
    private lateinit var resultname: TextView
    internal lateinit var mApiService: ApiService
    internal lateinit var resultN: String

    @Override
    internal fun onCreate(savedInstanceState: Bundle){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        btnLogout = findViewById(R.id.btnLogout1)
        fullname = findViewById(R.id.fullname)
        email = findViewById(R.id.emailshow)
        password = findViewById(R.id.passshow)

        //mApiService = getAPIService()

        initComponent()
        //to put data from activity before
        val extras = getIntent().getExtras()
        if (extras != null) {
            resultN = extras!!.getString("result_nama").toString()
            resultname!!.setText(resultN)
        }

        btnLogout!!.setOnClickListener(object : View.OnClickListener {

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            override fun onClick(v: View) {
                logout()
            }
        })
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater = getMenuInflater()
        inflater.inflate(R.menu.profile, menu)
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true

    }

    private fun initComponent() {
        resultname = findViewById(R.id.fullname) as TextView
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === R.id.btnprofile) {
            startActivity(Intent(this@ProfileActivity, ProfileActivity::class.java))
        } else if (item.getItemId() === R.id.btnLogout) {
            logout()
        }
        return true
    }

    fun logout() {
        SessionActivity.setLoggedIn(getApplicationContext(), false)
        startActivity(
            Intent(this@ProfileActivity, SignInActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
    }
}