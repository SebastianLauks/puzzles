package lauks.sebastian.sm_p3.view

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.DragEvent
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_board.*
import lauks.sebastian.sm_p3.R
import lauks.sebastian.sm_p3.utils.BitmapsComparer
import lauks.sebastian.sm_p3.utils.CustomDialogGenerator
import lauks.sebastian.sm_p3.utils.ImageCutter
import lauks.sebastian.sm_p3.utils.ImageShuffler
import lauks.sebastian.sm_p3.viewmodel.MainViewModel
import java.util.*

class BoardActivity : AppCompatActivity() {

    val TAG = "BoardActivity"

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

    private lateinit var mainViewModel: MainViewModel

    private lateinit var cutImage: List<List<Bitmap>>
    private lateinit var cutImageShuffled: List<List<Bitmap>>

    private var startTime: Long = 0L

    private var puzzlesDone = 0

    private val NUMBER_OF_PUZZLES = 12

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val bitmap = mainViewModel.getSelectedBitmap()

        if(bitmap == null){
            Toast.makeText(this, "Wystąpił błąd podczas przekazywania zdjęcia", Toast.LENGTH_SHORT).show()
            finish()
        }else {

            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val boardWidth = displayMetrics.widthPixels - 50
            val boardHeight = displayMetrics.heightPixels - 10
            cutImage = ImageCutter.cutImage( bitmap, boardWidth, boardHeight)
            cutImageShuffled = ImageShuffler.shuffleImage(cutImage)
            createBoardLayouts()
            createPatternLayouts()

            startTime = Calendar.getInstance().timeInMillis
        }
    }




    @SuppressLint("ClickableViewAccessibility")
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
                            if(BitmapsComparer.areBitmapsEqual(cutImage[i][j], cutImageShuffled[x][y])) {
                                (v as ImageView).setImageBitmap(cutImage[i][j])
                                pattern[x][y].setImageDrawable(null)
                                pattern[x][y].setOnTouchListener { _, _ ->
                                    false
                                }
                                puzzlesDone++
                                if(puzzlesDone >= NUMBER_OF_PUZZLES) finishGame()
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

    private fun finishGame(){
        val finishTime = Calendar.getInstance().timeInMillis
        val gameTimeS = (finishTime - startTime) / 1000

        CustomDialogGenerator.createCustomDialog(this, "Gra ukończona z czasem ${gameTimeS}s.", "OK", ""){
            finish()
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
