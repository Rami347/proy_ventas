package com.example.proy_ventas

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.primeropasoskotlin.db.AdminSQLiteOpenHelper
import com.example.primeropasoskotlin.models.Productos

class MainActivity : AppCompatActivity() {
    lateinit var btnCal: Button
    lateinit var txtPrecio: EditText
    lateinit var tvResul: TextView
    lateinit var spList: Spinner
    lateinit var txtNom: EditText
    lateinit var listProd: ListView

    lateinit var btnBuscar: Button

    lateinit var listaProductosMutable:MutableList<String>
    lateinit var arrayAdapterProducto: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //codigo
        cargarR()
        estadoButon()
        cargarLista()

        val listaPaises= arrayOf("USA","BOL","ESP")
        val listaIVAAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listaPaises)
        spList.adapter=listaIVAAdapter
    }
    //funcion para cargar R
    fun cargarR (){
        btnCal = findViewById(R.id.btnCalcular)
        txtPrecio = findViewById(R.id.txtPrecio)
        tvResul = findViewById(R.id.tvResultado)
        spList = findViewById(R.id.spListaPaises)
        txtNom = findViewById(R.id.txtNombre)
        listProd = findViewById(R.id.listProducto)

        btnBuscar = findViewById(R.id.btnBuscarPord)
    }

    //estados de botton
    fun estadoButon(){
        btnCal.setOnClickListener(){
            var precio: Double = txtPrecio.text.toString().toDouble()
            var laptop = Productos(txtNom.text.toString(), precio)
            when(spList.selectedItem.toString()){
                "USA" -> listaProductosMutable.add(laptop.getNombre() + ", "+laptop.calcularIVA(0.03).toString())
                "BOL" -> listaProductosMutable.add(laptop.getNombre() + ", "+laptop.calcularIVA(0.13).toString())
                "ESP" -> listaProductosMutable.add(laptop.getNombre() + ", "+laptop.calcularIVA(0.05).toString())
            }
            listProd.adapter = arrayAdapterProducto

        }
        btnBuscar.setOnClickListener{
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select nombre,precio from producto where id_producto=${txtNom.text.toString()}", null)
            if (fila.moveToFirst()) {
                txtNom.setText(fila.getString(0))
                txtPrecio.setText(fila.getString(1))
            } else
                Toast.makeText(this, "Código no valido",  Toast.LENGTH_SHORT).show()
            bd.close()

        }
    }

    fun cargarLista(){
        //var listaArrayProducto = arrayOf("laptop", "computadora")
        listaProductosMutable = mutableListOf<String>()
        arrayAdapterProducto = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listaProductosMutable)
        listProd.adapter = arrayAdapterProducto
    }
}