package lauks.sebastian.sm_p3.utils

import android.graphics.Bitmap

class ImageShuffler {

    companion object {
        fun shuffleImage(image: List<List<Bitmap>>): List<List<Bitmap>> {
            var output = mutableListOf<MutableList<Bitmap>>(mutableListOf(), mutableListOf(), mutableListOf())

            var list = mutableListOf<Bitmap>()

            image.forEach {row ->
                row.forEach {
                    list.add(it)
                }
            }

            list.shuffle()

            var listCounter = 0
            for(i in 0..2){
                for(j in 0..3){
                    output[i].add(list[listCounter++])
                }
            }
            return output


        }
    }

}