package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var contentView:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        contentView= ActivityMainBinding.inflate(layoutInflater)

        setContentView(contentView.root)

        val db = Firebase.firestore

        var r=""

        db.collection("student")
            //.whereNotEqualTo("Nombres", "")
            .get()
            .addOnSuccessListener { result ->

                var n=""
                var ln=""
                for (document in result) {
                    r += "Usuario: ${document.data.get("nombres").toString()}, ${document.data.get("apellidos").toString()} \n"
                    n=document.data.get("nombres").toString()
                    ln=document.data.get("apellidos").toString()

                    //  lista.add(Usuarios( registro.getString("nombres") , registro.getString("apellidos"), registro.getString("nick")))
                }

                contentView.txtPress.text="$n, $ln"
                contentView.txtResult.text=r
            }
            .addOnFailureListener { exception ->
                Log.i("result", "Error getting documents.", exception)
            }



        contentView.btnSave.setOnClickListener {

            if(contentView.etxtNombres.text.toString().isEmpty() || contentView.etxtApellidos.text.toString().isEmpty())
            {
                Toast.makeText(this,"Debe ingresar datos",Toast.LENGTH_SHORT).show()
                contentView.etxtNombres.requestFocus()
                return@setOnClickListener

            }

            val name=contentView.etxtNombres.text.toString()
            val lname=contentView.etxtApellidos.text.toString()

            Log.i("result","$name, $lname")


            //ESTRUTURAS DE DATOS:  ARRAY
            //  LISTAS, COLECCIONES, SET, MAPAS

            val nuevoUsuario = hashMapOf(
                "Nombres" to name,
                "Apellidos" to lname,
            )

            db.collection("usuarios")
                .add(nuevoUsuario)
                .addOnSuccessListener {
                    Toast.makeText(this,"El usuario: $name $lname se registro correctamente",Toast.LENGTH_SHORT).show()

                    contentView.etxtNombres.text.clear()
                    contentView.etxtApellidos.text.clear()
                    contentView.etxtNombres.requestFocus()

                }
                .addOnFailureListener{
                    Log.i("result","Error: $it")
                }

        }

        contentView.btnView.setOnClickListener{


        }

    }

}














