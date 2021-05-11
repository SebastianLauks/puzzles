package lauks.sebastian.sm_p3.model

import android.graphics.Bitmap

class MainRepository private constructor(){

    var selectedBitmap: Bitmap? = null


    companion object{
        @Volatile private  var instance: MainRepository ? = null

        fun getInstance(): MainRepository   =
            instance ?: synchronized(this) {
                instance ?: MainRepository  ().also { instance = it }
            }

    }
}