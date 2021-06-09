package com.jonathan.mixeddoublesgame.game.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jonathan.mixeddoublesgame.R
import com.jonathan.mixeddoublesgame.databinding.GameFragmentBinding




class GameFragment: Fragment() {

    private val viewModel: GameViewModel by viewModels()
    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)

        Log.d("GameFragment", "GameFragment created/re-created!")
        Log.d("GameFragment", "Word: ${viewModel.score.value} ")

        return binding.root



    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the viewModel for data binding - this allows the bound layout access
        // to all the data in the VieWModel
        binding.gameViewModel = viewModel
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        // Setup a click listener for the Submit and Skip buttons.
        binding.endButton.setOnClickListener{ onEndGame()}
        binding.buttonLeft.setOnClickListener{ onLeftMove()}
        binding.buttonRight.setOnClickListener{ onRightMove()}
        binding.buttonUp.setOnClickListener{ onRightMove()}
        binding.buttondown.setOnClickListener{ onRightMove()}

    }

    private fun onEndGame(){
        viewModel.reinitializeData()
    }

    private fun onLeftMove(){
        val flag:Boolean
        flag=viewModel.LeftMoveData()
        if (flag == false) {
            Toast.makeText(activity, "Game Over",Toast.LENGTH_LONG).show()
        }
    }
    private fun onRightMove(){
        var flag:Boolean
        flag = viewModel.nextNumber()
        if (flag == false) {
            Toast.makeText(activity, "Game Over",Toast.LENGTH_LONG).show()
        }
    }
}