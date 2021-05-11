package lauks.sebastian.sm_p3

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_predefined.*
import lauks.sebastian.sm_p3.model.Image
import lauks.sebastian.sm_p3.viewmodel.MainViewModel

class PredefinedActivity : AppCompatActivity() {

    private lateinit var predefinedImages: MutableList<Image>

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predefined)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        predefinedImages = mutableListOf(
            Image(BitmapFactory.decodeResource(resources, R.drawable.landscape_1)),
            Image(BitmapFactory.decodeResource(resources, R.drawable.landscape_2)),
            Image(BitmapFactory.decodeResource(resources, R.drawable.landscape_3)),
            Image(BitmapFactory.decodeResource(resources, R.drawable.landscape_4)),
            Image(BitmapFactory.decodeResource(resources, R.drawable.landscape_5)),
            Image(BitmapFactory.decodeResource(resources, R.drawable.landscape_6)),
            Image(BitmapFactory.decodeResource(resources, R.drawable.landscape_7)),
            Image(BitmapFactory.decodeResource(resources, R.drawable.landscape_8)),
            Image(BitmapFactory.decodeResource(resources, R.drawable.landscape_9)),
            Image(BitmapFactory.decodeResource(resources, R.drawable.landscape_10))
        )


        val onImageClickListener = { bitmap: Bitmap ->
            mainViewModel.setSelectedBitmap(bitmap)
            val intent = Intent(this, BoardActivity::class.java)
            this.startActivity(intent)
        }

        val adapter = ImageAdapter(applicationContext, predefinedImages, onImageClickListener)
        gvImages.adapter = adapter
    }
}
