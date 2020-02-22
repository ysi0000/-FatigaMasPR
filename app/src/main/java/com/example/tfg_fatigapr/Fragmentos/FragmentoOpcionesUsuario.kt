package com.example.tfg_fatigapr.Fragmentos


import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import com.example.tfg_fatigapr.R.xml.opciones_usuario_fragment
import com.example.tfg_fatigapr.Utilidades.RoomDataBase


/**
 * A simple [Fragment] subclass.
 */
class FragmentoOpcionesUsuario : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {
    private lateinit var db: RoomDataBase
    lateinit var sharedPreferences:SharedPreferences
    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(opciones_usuario_fragment)
        sharedPreferences = preferenceScreen.sharedPreferences
        val prefScreen: PreferenceScreen = preferenceScreen
        val count: Int = prefScreen.preferenceCount
        db= RoomDataBase.getInstance(context!!)!!

    }

    private fun setPreferenceSummary(
        preference: Preference,
        value: String
    ) {
        preference.summary = value
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        //val preference = findPreference(key)
        //if (null != preference) { // Updates the summary for the preference
            /*val value = sharedPreferences.getString(preference.key as String, "")
            when(preference.key){
                "Nombre"->usuariosDao.actualizarNombre(value!!, usuario.nombre)
                else->usuariosDao.actualizarPeso(value!!.toInt(), usuario.nombre)
            }

            setPreferenceSummary(preference, value)*/
        //}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        preferenceScreen.sharedPreferences
            .unregisterOnSharedPreferenceChangeListener(this)
    }

}
