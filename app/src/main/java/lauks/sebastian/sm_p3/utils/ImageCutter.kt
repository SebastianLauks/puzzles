package lauks.sebastian.sm_p3.utils

import android.app.Activity
import android.app.Application
import android.graphics.Bitmap
import android.util.DisplayMetrics

class ImageCutter {

    companion object {
        fun cutImage(img: Bitmap, boardWidth: Int, boardHeight: Int): List<List<Bitmap>> {
            val image = Bitmap.createScaledBitmap(img, boardWidth, boardHeight / 2, false)
            val output =
                mutableListOf<MutableList<Bitmap>>(
                    mutableListOf(),
                    mutableListOf(),
                    mutableListOf()
                )
            val width = image.width
            val height = image.height
            for (i in 0..3) {
                for (j in 0..2) {
                    output[j].add(
                        Bitmap.createBitmap(
                            image,
                            (width * i) / 4,
                            (height * j) / 3,
                            width / 4,
                            height / 3
                        )
                    )
                }
            }
            return output
        }
    }


}