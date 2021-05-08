package lauks.sebastian.sm_p3

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var board = mutableListOf<MutableList<ImageView>>(
        mutableListOf<ImageView>(),
        mutableListOf<ImageView>(),
        mutableListOf<ImageView>()
    )
    private var pattern = mutableListOf<MutableList<ImageView>>(
        mutableListOf<ImageView>(),
        mutableListOf<ImageView>(),
        mutableListOf<ImageView>()
    )

    private lateinit var cutImage: List<List<Bitmap>>
    private lateinit var cutImageShuffled: List<List<Bitmap>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cutImage = cutImage(BitmapFactory.decodeResource(resources, R.drawable.landscape_1))
        cutImageShuffled = shuffleImage(cutImage)
        createBoardLayouts()
        createPatternLayouts()
    }


    private fun shuffleImage(image: List<List<Bitmap>>): List<List<Bitmap>> {
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

    private fun createBoardLayouts() {

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels - 50
        val height = displayMetrics.heightPixels - 10


        for (i in 0..2) {
            val tableRow = TableRow(this).apply {
                id = i
            }
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )

            for (j in 0..3) {

                val imageView = ImageView(this).apply {
                    id = j
                    adjustViewBounds = true
                    scaleType = ImageView.ScaleType.FIT_XY
                    layoutParams = TableRow.LayoutParams(
                        Integer.min(height, width) / 4,
                        Integer.min(height, width) / 6
                    )
                    setBackgroundResource(R.drawable.image_border)
                }
                imageView.setOnDragListener { v, event ->
                    when(event.action){
                        DragEvent.ACTION_DRAG_STARTED -> {
                            true
                        }
                        DragEvent.ACTION_DROP -> {
                            val item = event.clipData.getItemAt(0)
                            val indexes = item.text.toString().toInt()
                            val y = indexes % 10
                            val x = (indexes - y) / 10
                            if(cutImage[i][j].sameAs(cutImageShuffled[x][y])) {
                                (v as ImageView).setImageBitmap(cutImage[i][j])
                                pattern[x][y].setImageDrawable(null)
                                pattern[x][y].setOnTouchListener { v, event ->
                                    false
                                }
                                true
                            }else false
                        }
                        else -> {
                            false
                        }
                    }

                }
                //SET ON CLICK HERE
//                imageView.setOnClickListener {  }
                board[i].add(imageView)
                tableRow.addView(imageView)

            }
            tlBoard.addView(
                tableRow,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }
    }

    private fun createPatternLayouts(){
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels - 50
        val height = displayMetrics.heightPixels - 10


        for (i in 0..2) {
            val tableRow = TableRow(this).apply {
                id = i*10
            }
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )


            for (j in 0..3) {

                val imageView = ImageView(this).apply {
                    id = j*10
                    adjustViewBounds = true
                    scaleType = ImageView.ScaleType.FIT_XY
                    layoutParams = TableRow.LayoutParams(
                        Integer.min(height, width) / 4,
                        Integer.min(height, width) / 6
                    )
//                    setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    setImageBitmap(cutImageShuffled[i][j])
                    setBackgroundResource(R.drawable.image_border)
                }
                imageView.setOnTouchListener { v, event ->
                    val item = ClipData.Item((i*10 + j).toString() as CharSequence)
                    val dragData = ClipData((i*10 + j).toString() as CharSequence, arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
                    val shadow = View.DragShadowBuilder(v)
                    v.startDragAndDrop(dragData, shadow, null, 0)
                }
                //SET ON CLICK HERE
//                imageView.setOnClickListener {  }
                pattern[i].add(imageView)
                tableRow.addView(imageView)

            }
            tlPattern.addView(
                tableRow,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }
    }

    private fun cutImage(img: Bitmap): List<List<Bitmap>> {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels - 50
        val screenHeight = displayMetrics.heightPixels - 10
        val image = Bitmap.createScaledBitmap(img, screenWidth, screenHeight/2, false)
        val output = mutableListOf<MutableList<Bitmap>>(mutableListOf(), mutableListOf(), mutableListOf())
        val width = image.width
        val height = image.height
        for(i in 0..3){
            for(j in 0..2){
                output[j].add(Bitmap.createBitmap(image, (width * i)/4, (height * j)/3, width / 4 , height / 3))
            }
        }
        return output
    }


}
