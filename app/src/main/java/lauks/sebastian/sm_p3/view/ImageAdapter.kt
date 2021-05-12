package lauks.sebastian.sm_p3.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_image.view.*
import lauks.sebastian.sm_p3.R
import lauks.sebastian.sm_p3.model.Image

class ImageAdapter(private val context: Context, private val images: List<Image>, private val onImageClickListener: (Bitmap) -> Unit) : BaseAdapter(){
    val TAG = "ImageAdapter"

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val image = images[position]

        val view= convertView ?: LayoutInflater.from(context).inflate(R.layout.item_image, null)

        view.setOnClickListener {
            Log.d(TAG,image.bitmap.toString())
            onImageClickListener(image.bitmap)
        }
        view?.ivImage?.setImageBitmap(image.bitmap)

        return view!!
    }

    override fun getItem(position: Int): Any {
        return images[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return images.size
    }
}