package com.jonathan.mixeddoublesgame.game.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class GameViewModel: ViewModel() {
    private val _score = MutableLiveData("0")
    val score: LiveData<String>
        get() = _score
    private  var gameFlag: Boolean = true
    private val randomList = (0..15).shuffled()



    private  val _dataObservable = MutableLiveData ( arrayListOf(arrayListOf(0,0,0,0),arrayListOf(0,0,0,0),arrayListOf(0,0,0,0),arrayListOf(0,0,0,0)))
    val dataObservable : LiveData<ArrayList<ArrayList<Int>>>
        get() = _dataObservable


    init {
        Log.d("GameFragment", "GameViewModel created!")
        _dataObservable.value  = arrayListOf(arrayListOf(0,0,0,0),arrayListOf(0,0,0,0),arrayListOf(0,0,0,0),arrayListOf(0,0,0,0))!!!!

    }
    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

    private fun getNextNumber(): Boolean {

            var randomNumber: Int = Random().nextInt(16) + 1
            var ranrow: Int = (randomNumber - 1) / 4
            var rancol: Int = if ((randomNumber % 4) == 0) 3 else (randomNumber % 4 - 1)

                while(true){
                if (_dataObservable.value!![ranrow][rancol] == 0 ) {
                    gameFlag = true
                    break
                }
                else
                {
                    if (checkGameover())
                        return false
                    else
                    {
                        randomNumber = Random().nextInt(16) + 1
                        ranrow= (randomNumber - 1) / 4
                        rancol = if ((randomNumber % 4) == 0) 3 else (randomNumber % 4 - 1)
                    }

                }
                }


        //_dataObservable.value = arrayListOf(arrayListOf(0,2,0,randomNumber),arrayListOf(0,0,0,0),arrayListOf(0,0,0,0),arrayListOf(0,0,0,0))
     //   _dataObservable.value!![randomRow]= arrayListOf(1,2,3,randomCol)
        updateMatrix(ranrow,rancol,2)
        Log.d("GameFragment","Random num : $randomNumber")
        Log.d("GameFragment","Random List : ${dataObservable.value} ")
        Log.d("GameFragment","Random List : $ranrow $rancol " )

        return gameFlag
    }

    private fun checkGameover(): Boolean {

        for (i in 0..3)
            for (j in 0..3)
                if (dataObservable.value!![i][j] == 0)
                    return false

        return true

    }

    fun reinitializeData() {
        _dataObservable.value = arrayListOf(arrayListOf(0,0,0,0),arrayListOf(0,0,0,0),arrayListOf(0,0,0,0),arrayListOf(0,0,0,0))
      //  getNextNumber()
    }

    fun nextNumber(): Boolean{
        gameFlag=getNextNumber()
        return  gameFlag
    }

    fun LeftMoveData(): Boolean{
        val tempval = _dataObservable.value
        for (j in 0..3)
          for (i in 3 downTo 1) {
              if (tempval!![j][i - 1] == 0) {
                  tempval[j][i - 1] = tempval[j][i]
                  tempval[j][i]= 0
              }
              if (tempval!![j][i] == tempval!![j][i - 1]) {
                  Log.d("GameView", "$tempval[j][i] $tempval[j][i-1] $j $i")
                  tempval!![j][i - 1] += tempval[j][i]
                  tempval!![j][i] = 0
              } else {
                  Log.d("GameView", " else $tempval[j][i] $tempval[j][i-1] $j $i")
                  Log.d("GameView", " else $tempval!![j][i] $tempval!![j][i-1] $j $i")
              }
          }

        _dataObservable.value= tempval
        gameFlag=getNextNumber()
        return gameFlag

    }

    fun rightMoveData(): Boolean{
        val tempval = _dataObservable.value
        for (j in 0..3)
            for (i in 0..2 ) {
                if (tempval!![j][i + 1] == 0) {
                    tempval[j][i + 1] = tempval[j][i]
                    tempval[j][i]= 0
                }
                if (tempval!![j][i] == tempval!![j][i + 1]) {
                    Log.d("GameView", "$tempval[j][i] $tempval[j][i+1] $j $i")
                    tempval!![j][i + 1] += tempval[j][i]
                    tempval!![j][i] = 0
                } else {
                    Log.d("GameView", " else $tempval[j][i] $tempval[j][i-1] $j $i")
                    Log.d("GameView", " else $tempval!![j][i] $tempval!![j][i-1] $j $i")
                }
            }

        _dataObservable.value= tempval
        gameFlag=getNextNumber()
        return gameFlag

    }

    fun updateMatrix(row: Int, col: Int, rcval: Int){
        val tempval= _dataObservable.value
        tempval!![row][col]=rcval
        _dataObservable.value  = tempval
    }

    fun upMoveData(): Boolean{
        val tempval = _dataObservable.value
        for (j in 3 downTo 1)
            for (i in 0..3 ) {
                if (tempval!![j -1 ][i ] == 0) {
                    tempval[j-1][i] = tempval[j][i]
                    tempval[j][i]= 0
                }
                if (tempval!![j][i] == tempval!![j-1][i]) {
                    Log.d("GameView", "$tempval[j][i] $tempval[j-1][i] $j $i")
                    tempval!![j-1][i] += tempval[j][i]
                    tempval!![j][i] = 0
                } else {
                    Log.d("GameView", " else $tempval[j][i] $tempval[j-1][i] $j $i")
                    Log.d("GameView", " else $tempval!![j][i] $tempval!![j-1][i] $j $i")
                }
            }

        _dataObservable.value= tempval
        gameFlag=getNextNumber()
        return gameFlag

    }

    fun downMoveData(): Boolean{
        val tempval = _dataObservable.value
        for (j in 0..2)
            for (i in 0..3 ) {
                if (tempval!![j+1 ][i ] == 0) {
                    tempval[j+1][i] = tempval[j][i]
                    tempval[j][i]= 0
                }
                if (tempval!![j][i] == tempval!![j+1][i]) {
                    Log.d("GameView", "$tempval[j][i] $tempval[j+1][i] $j $i")
                    tempval!![j+1][i] += tempval[j][i]
                    tempval!![j][i] = 0
                } else {
                    Log.d("GameView", " else $tempval[j][i] $tempval[j+1][i] $j $i")
                    Log.d("GameView", " else $tempval!![j][i] $tempval!![j+1][i] $j $i")
                }
            }

        _dataObservable.value= tempval
        gameFlag=getNextNumber()
        return gameFlag

    }
}