package lauks.sebastian.sm_p3


import android.graphics.BitmapFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import lauks.sebastian.sm_p3.utils.BitmapsComparer
import lauks.sebastian.sm_p3.utils.ImageCutter
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComparingBitmaps {

    @Test
    fun compareTheSameBitmaps() {
        val bitmap = BitmapFactory.decodeResource(
            InstrumentationRegistry.getInstrumentation().context.resources,
            lauks.sebastian.sm_p3.test.R.drawable.landscape_1
        )

        val cutImage = ImageCutter.cutImage(bitmap, 1000, 1000)
        val cutImage2 = ImageCutter.cutImage(bitmap, 1000, 1000)

        Assert.assertEquals(true, BitmapsComparer.areBitmapsEqual(cutImage[0][0], cutImage2[0][0]))
        Assert.assertEquals(false, BitmapsComparer.areBitmapsEqual(cutImage[0][0], cutImage2[0][1]))
        Assert.assertEquals(true, BitmapsComparer.areBitmapsEqual(cutImage[1][1], cutImage2[1][1]))
        Assert.assertEquals(true, BitmapsComparer.areBitmapsEqual(cutImage[2][3], cutImage2[2][3]))

    }

    @Test
    fun compareWholeDifferentBitmaps() {
        val bitmap = BitmapFactory.decodeResource(
            InstrumentationRegistry.getInstrumentation().context.resources,
            lauks.sebastian.sm_p3.test.R.drawable.landscape_1
        )

        val bitmap2 = BitmapFactory.decodeResource(
            InstrumentationRegistry.getInstrumentation().context.resources,
            lauks.sebastian.sm_p3.test.R.drawable.landscape_2
        )

        val cutImage = ImageCutter.cutImage(bitmap, 1000, 1000)
        val cutImage2 = ImageCutter.cutImage(bitmap2, 1000, 1000)


        cutImage.forEach {row1 ->
            row1.forEach { piece1 ->
                cutImage2.forEach { row2 ->
                    row2.forEach { piece2 ->
                        Assert.assertEquals(false, BitmapsComparer.areBitmapsEqual(piece1, piece2))
                    }

                }
            }

        }

    }

}