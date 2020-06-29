package com.example.tfg_fatigapr.Fragmentos


import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import com.example.tfg_fatigapr.R.xml.opciones_usuario_fragment
import com.example.tfg_fatigapr.Utilidades.RoomDataBase


/**
 * Clase que resulelve la logica del fragmento de opciones de usuario
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
        // Go through all of the preferences, and set up their preference summary.
        // Go through all of the preferences, and set up their preference summary.
        for (i in 0 until count) {
            val p = prefScreen.getPreference(i)
            val value = sharedPreferences.getString(p.key, "")
            setPreferenceSummary(p, value!!)
        }
    }

    /**
     * Setter de las opciones de usuario
     */
    private fun setPreferenceSummary(
        preference: Preference,
        value: String
    ) {
        preference.summary = value
    }

    /**
     * Fucnion que al cambiar una de las opciones la guarda en un valor
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val preference:Preference? = findPreference(key)
        if (null != preference) { // Updates the summary for the preference
            val value = sharedPreferences.getString(preference.key as String, "")!!
            setPreferenceSummary(preference, value)
        }
    }

    /**
     * Se sobreescribe para que en la creacion del fragmente se registre el listener
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences
            .registerOnSharedPreferenceChangeListener(this)
    }

    /**
     * Se sobreescribe esta funcion para que al destriur el fragmento se quite el listener y evitar
     * perdidas de memoria
     */
    override fun onDestroy() {
        super.onDestroy()

        preferenceScreen.sharedPreferences
            .unregisterOnSharedPreferenceChangeListener(this)
    }

}
