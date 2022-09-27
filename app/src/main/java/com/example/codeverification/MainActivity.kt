package com.example.codeverification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.codeverification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getTextBoxList()[0].requestFocus()
        binding.btnDelete.setOnClickListener {
            delete()
        }
    }

    fun getTextBoxList(): java.util.ArrayList<EditText> = with(binding) {
        return@with arrayListOf(txtNum1, txtNum2, txtNum3, txtNum4)
    }

    fun delete() {
        var text: EditText = getTextBoxList().filter {
            it.hasFocus()
        }.first()
        text.setText("")
        var index = getTextBoxList().indexOf(text)
        if (index > 0) {
            index--;
            getTextBoxList().get(index).requestFocus()
        }
    }

    fun toWrite(view: View) {
        if (view is Button) {
            var text: EditText = getTextBoxList().filter {
                it.hasFocus()
            }.first()
            text.setText(view.text.toString())
            var index = getTextBoxList().indexOf(text)
            if (index < getTextBoxList().size - 1) {
                index++;
                getTextBoxList().get(index).requestFocus()
            }
        }
    }
}