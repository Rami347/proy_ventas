package com.example.primeropasoskotlin.models

class Ventas constructor(codVenta:Int,codProducto:String, cantidad:Int, precio_total:Double, cliente:String) {
    private var codVenta = codVenta
    private var codProducto = codProducto
    private var cantidad:Int = cantidad
    private var precio_total = precio_total
    private var cliente = cliente



    fun getcodVenta(): Int{
        return this.codVenta
    }
    fun getcodProducto():String{
        return this.codProducto
    }
    fun getcantidad(): Int{
        return this.cantidad
    }
    fun getPrecioTotal():Double{
        return this.precio_total
    }
    fun getCliente():String{
        return this.cliente
    }

    fun calcularTotal(productos: Productos):Double{
        var total:Double = productos.getPrecio()*cantidad
        return total
    }
}