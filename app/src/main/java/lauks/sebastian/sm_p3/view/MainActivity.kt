package lauks.sebastian.sm_p3.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import lauks.sebastian.sm_p3.R
import lauks.sebastian.sm_p3.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private val PERMISSION_CODE = 1

    private val mainViewModel:MainViewModel by viewModels()

    private val content = registerForActivityResult(ActivityResultContracts.GetContent()){
        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, it)
        mainViewModel.setSelectedBitmap(bitmap)
        val intent = Intent(this, BoardActivity::class.java)
        this.startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        askForPermission()

        btPredefined.setOnClickListener {
            val intent = Intent(this, PredefinedActivity::class.java)
            startActivity(intent)
        }

        btFromDevice.setOnClickListener {
            content.launch("image/*")

        }
    }

    private fun askForPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_CODE
                )

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {

            // Permission has already been granted
        }
    }
}
