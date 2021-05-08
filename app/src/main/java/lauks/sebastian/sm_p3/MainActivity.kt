package lauks.sebastian.sm_p3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createBoardLayouts()
        createPatternLayouts()
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
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    layoutParams = TableRow.LayoutParams(
                        Integer.min(height, width) / 4,
                        Integer.min(height, width) / 6
                    )
                    setBackgroundResource(R.drawable.image_border)
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
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    layoutParams = TableRow.LayoutParams(
                        Integer.min(height, width) / 4,
                        Integer.min(height, width) / 6
                    )
                    setPadding(1,1,1,1)
//                    setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    setBackgroundResource(R.drawable.image_border)
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


}
