package lauks.sebastian.sm_p3.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import lauks.sebastian.sm_p3.model.Image
import lauks.sebastian.sm_p3.model.MainRepository

class MainViewModel: ViewModel() {

    private val mainRepository = MainRepository.getInstance()


    fun setSelectedBitmap(bitmap: Bitmap){
        mainRepository.selectedBitmap = bitmap
    }

    fun getSelectedBitmap() = mainRepository.selectedBitmap
}