package com.example.proy_ventas

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.primeropasoskotlin.db.AdminSQLiteOpenHelper
import com.example.primeropasoskotlin.models.Productos
import com.example.primeropasoskotlin.models.Ventas

class Ventas : AppCompatActivity() {

    lateinit var txtCodProd:EditText
    lateinit var txtCodVenta:EditText
    lateinit var txtCantidad:EditText
    lateinit var txtCliente:EditText
    lateinit var txtPreciTotal:EditText
    lateinit var btnBuscarProd:Button
    lateinit var btnRegVenta:Button
    lateinit var listVts: ListView

    lateinit var ventas: ArrayList<String>
    lateinit var arrayAdapterVentas: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ventas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cargarR()
        estadoBoton()
    }
    fun cargarR(){
        txtCodProd = findViewById(R.id.txtCodigoProducto)
        txtCodVenta = findViewById(R.id.txtCodigoVenta)
        txtCantidad = findViewById(R.id.txtCantidad)
        txtCliente = findViewById(R.id.txtCliente)
        txtPreciTotal = findViewById(R.id.txtPrecioTotal)
        btnBuscarProd = findViewById(R.id.btnBuscarProd)
        btnRegVenta = findViewById(R.id.btnRegistrarVenta)
        listVts = findViewById(R.id.listVentas)

        ventas = ArrayList()
        arrayAdapterVentas = ArrayAdapter(this, android.R.layout.simple_list_item_1,ventas)
        listVts.adapter = arrayAdapterVentas

    }

    fun estadoBoton(){
        btnBuscarProd.setOnClickListener{
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select nombre,precio from producto where id_producto=${txtCodProd.text.toString()}", null)
            if (fila.moveToFirst()) {
                txtCodProd.setText(fila.getString(0))
                txtPreciTotal.setText(fila.getString(1))
            } else
                Toast.makeText(this, "No existe un producto con dicho código",  Toast.LENGTH_SHORT).show()
            bd.close()
        }
        btnRegVenta.setOnClickListener{

            val precioTotal = txtPreciTotal.text.toString().toDouble()
            val producto = Productos(txtCodProd.text.toString(), precioTotal)

            val obj = Ventas(
                txtCodVenta.getText().toString().toInt(),
                txtCodProd.getText().toString(),
                txtCantidad.getText().toString().toInt(),
                txtPreciTotal.getText().toString().toDouble(),
                txtCliente.getText().toString())

            val totalCalculado = obj.calcularTotal(producto)


            val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("id_venta",obj.getcodVenta())
            registro.put("id_producto", obj.getcodProducto())
            registro.put("cantidad", obj.getcantidad())
            registro.put("precio_total", totalCalculado)
            registro.put("cliente", obj.getCliente())
            bd.insert("ventas", null, registro)
            bd.close()


            txtCodVenta.setText("")
            txtCodProd.setText("")
            txtCantidad.setText("")
            txtPreciTotal.setText("")
            txtCliente.setText("")
            listVts.adapter = arrayAdapterVentas

            cargarVentas()
        }
    }
    @SuppressLint("Range")
    private fun cargarVentas() {
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd = admin.readableDatabase
        val cursor = bd.rawQuery("SELECT * FROM ventas", null)
        ventas.clear()

        if (cursor.moveToFirst()) {
            do {
                val idVenta = cursor.getInt(cursor.getColumnIndex("id_venta"))
                val NomProducto = cursor.getString(cursor.getColumnIndex("id_producto"))
                val cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"))
                val precioTotal = cursor.getDouble(cursor.getColumnIndex("precio_total"))
                val cliente = cursor.getString(cursor.getColumnIndex("cliente"))

                val ventaStr = "Venta ID: $idVenta, \nProducto: $NomProducto, \nCantidad: $cantidad, \nTotal: $precioTotal, \nCliente: $cliente"
                ventas.add(ventaStr)
            } while (cursor.moveToNext())
        }
        cursor.close()
        bd.close()
        arrayAdapterVentas.notifyDataSetChanged()
    }
}