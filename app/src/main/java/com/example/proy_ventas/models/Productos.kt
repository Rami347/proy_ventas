package com.example.primeropasoskotlin.models

class Productos constructor(nombre:String, precio:Double){
    private var nombre:String = nombre
    private var precio:Double = precio


    //get
    fun getNombre(): String{
        return this.nombre
    }

    fun getPrecio():Double{
        return this.precio
    }

    fun calcularIVA(iva: Double):Double{
        var total:Double = precio*iva
        return total
    }


}