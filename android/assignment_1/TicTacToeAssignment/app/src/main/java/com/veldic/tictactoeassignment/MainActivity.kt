package com.veldic.tictactoeassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.veldic.tictactoeassignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonArray = arrayOf(
            arrayOf(binding.button00, binding.button01, binding.button02),
            arrayOf(binding.button10, binding.button11, binding.button12),
            arrayOf(binding.button20, binding.button21, binding.button22)
        )

        for (i in 0 until 3) {
            for (j in 0 until 3) {
                buttonArray[i][j].setOnClickListener { vm.clickButton(i, j) }
            }
        }

        binding.buttonReset.setOnClickListener {
            vm.resetGame()
        }

        vm.dataList.observe(this, {
            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    buttonArray[i][j].text = it[i][j]
                }
            }
        })

        vm.gameStatus.observe(this, {
            binding.textStatus.text = it
                binding.textTurn.visibility = if (it == MainViewModel.GAME_STATUS_PLAYING) View.VISIBLE else View.INVISIBLE

        })

        vm.turnData.observe(this, {
            binding.textTurn.text = if (it == MainViewModel.TURN_O) "O's turn" else "X's turn"
        })
    }
}