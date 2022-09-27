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


    /**
     *  bloquea el teclado en todos los EditText
     * */
    fun lockKeyboard() {
        //recorremos todos los editText
        getTextBoxList().forEach {
            //bloqueamos el teclado
            it.inputType = InputType.TYPE_NULL
        }
    }

    /**
     *  obtiene una lista con todos los EditText
     * */
    fun getTextBoxList(): java.util.ArrayList<EditText> = with(binding) {
        return@with arrayListOf(txtNum1, txtNum2, txtNum3, txtNum4)
    }

    /**
     *  Elimina el text del edit que tiene focus  y si esta vacio entonces elimina el texto de edittext anterior
     * */
    fun delete() {
        //buscando el EdiText que tiene el focus
        var text: EditText = getTextBoxList().filter {
            it.hasFocus()
        }.first()

        //obtenemos el index que ocupa el edittext en el array de editText
        var index = getTextBoxList().indexOf(text)
        //si no esta vacio limpiamos el texto
        if (!text.text.isNullOrEmpty()) {
            text.setText("")
        } else {
            //si esta vacio miramos,y si podemos avanzar hacia atras
            if (index > 0) {
                // si se puede avanzar hacia atras entonces lo hacemos y borramos el texto de esa posicion
                index--;
                getTextBoxList().get(index).apply {
                    setText("")
                    requestFocus()
                }
            }
        }

    }

    /**
     * EScribe el texto de boton que se le dio clic en el edit que tiene el focus
     * */
    fun toWrite(view: View) {
        //comprobamos si es un boton
        if (view is Button) {
            //obtenemos el editText que tiene el focus
            var text: EditText = getTextBoxList().filter {
                it.hasFocus()
            }.first()
            //escribimos el texto que tiene el boton en el editText
            text.setText(view.text.toString())
            //cambiamos el tipo de caja de editText a normal
            text.transformationMethod = HideReturnsTransformationMethod.getInstance()
            //cambiamos el tipo de editText a password despues de un segundo
            Handler(Looper.myLooper()!!).postDelayed({
                text.transformationMethod = PasswordTransformationMethod.getInstance()
            }, 1000)
            //obtenemos el index de la caja que tiene el focus
            var index = getTextBoxList().indexOf(text)
            //comprobamos que no se vaya a desbordar el vector
            if (index < getTextBoxList().size - 1) {
                //cambiamos al siguiente editText y le damos focus
                index++;
                getTextBoxList().get(index).requestFocus()
            }
        }
    }
}