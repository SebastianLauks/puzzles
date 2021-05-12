package lauks.sebastian.sm_p3.utils

import android.graphics.Bitmap

class BitmapsComparer {
    companion object {
        fun areBitmapsEqual(b1: Bitmap, b2: Bitmap): Boolean{
            return b1.sameAs(b2)
        }
    }
}