package com.example.proy_ventas

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.primeropasoskotlin.db.AdminSQLiteOpenHelper
import com.example.primeropasoskotlin.models.Productos

class Productos : AppCompatActivity() {

    lateinit var txtNomProd: EditText
    lateinit var txtPrecioProd: EditText
    lateinit var txtCodProd: EditText
    lateinit var btnRegProd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_productos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cargarR()
        estadoBoton()
    }
    fun cargarR(){
        txtNomProd = findViewById(R.id.txtNombProducto)
        txtPrecioProd = findViewById(R.id.txtPreciProducto)
        txtCodProd = findViewById(R.id.txtCodigoProducto)
        btnRegProd = findViewById(R.id.btnRegistrarPorducto)
    }
    fun estadoBoton(){
        btnRegProd.setOnClickListener{
            val obj = Productos(txtNomProd.getText().toString(),txtPrecioProd.getText().toString().toDouble())
            val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("id_producto",txtCodProd.getText().toString())
            registro.put("nombre", obj.getNombre())
            registro.put("precio", obj.getPrecio())
            bd.insert("producto", null, registro)
            bd.close()
            txtCodProd.setText(" ")
            txtNomProd.setText(" ")
            txtPrecioProd.setText(" ")
            Toast.makeText(this, "Se cargaron los datos del producto", Toast.LENGTH_SHORT).show()
        }
    }
}