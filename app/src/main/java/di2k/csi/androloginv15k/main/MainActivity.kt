package di2k.csi.androloginv15k.main

//import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import di2k.csi.androloginv15k.R

class MainActivity : AppCompatActivity() {
    private lateinit var btSubmit : RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btSubmit = findViewById(R.id.lewati)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        btSubmit.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

    }



}
