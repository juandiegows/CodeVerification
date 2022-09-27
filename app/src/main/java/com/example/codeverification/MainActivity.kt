package com.example.codeverification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
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
        lockKeyboard()
        getTextBoxList()[0].requestFocus()
        binding.btnDelete.setOnClickListener {
            delete()
        }
    }

    fun lockKeyboard(){
        getTextBoxList().forEach {
            it.inputType = InputType.TYPE_NULL
        }
    }
    // obtiene n
    fun getTextBoxList(): java.util.ArrayList<EditText> = with(binding) {
        return@with arrayListOf(txtNum1, txtNum2, txtNum3, txtNum4)
    }

    fun delete() {
        var text: EditText = getTextBoxList().filter {
            it.hasFocus()
        }.first()

        var index = getTextBoxList().indexOf(text)
        if (!text.text.isNullOrEmpty()) {
            text.setText("")
        }else{
            if (index > 0) {
                index--;
                getTextBoxList().get(index).setText("")
            }
        }
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
            text.transformationMethod = HideReturnsTransformationMethod.getInstance()
            Handler(Looper.myLooper()!!).postDelayed({
                text.transformationMethod = PasswordTransformationMethod.getInstance()
            },1000)

            var index = getTextBoxList().indexOf(text)
            if (index < getTextBoxList().size - 1) {
                index++;
                getTextBoxList().get(index).requestFocus()
            }
        }
    }
}