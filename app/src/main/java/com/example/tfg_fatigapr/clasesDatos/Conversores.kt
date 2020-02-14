package com.example.tfg_fatigapr.clasesDatos

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


/*class ConversorDia {

    var gson:Gson=Gson()


    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Dia?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type =
            object : TypeToken<List<Dia?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Dia?>?): String? {
        return gson.toJson(someObjects)
    }
}

class ConversorEjericicio{
    var gson:Gson=Gson()
    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Ejercicio?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type =
            object : TypeToken<List<Ejercicio?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Ejercicio?>?): String? {
        return gson.toJson(someObjects)
    }
}

class ConversorSerie{
    var gson:Gson=Gson()
    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Serie?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type =
            object : TypeToken<List<Serie?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Serie?>?): String? {
        return gson.toJson(someObjects)
    }
}*/

