package com.example.tfg_fatigapr


import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import com.example.tfg_fatigapr.DAOs.DAOUsuarios
import com.example.tfg_fatigapr.R.xml.opciones_usuario_fragment
import com.example.tfg_fatigapr.clasesDatos.Usuario

/**
 * A simple [Fragment] subclass.
 */
class FragmentoOpcionesUsuario : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {
    lateinit var usuario:Usuario
    lateinit var db:RoomDataBase
    lateinit var usuariosDao:DAOUsuarios
    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(opciones_usuario_fragment)

        val sharedPreferences = preferenceScreen.sharedPreferences
        val prefScreen: PreferenceScreen = preferenceScreen
        val count: Int = prefScreen.preferenceCount
        db= RoomDataBase.getInstance(context!!)!!
        usuariosDao=db.usuariosDAO()
        val usuarios=usuariosDao.seleccionarUsuarios()
        if(usuarios.isNotEmpty()){
            for (i in 0 until count) {
                val p: Preference = prefScreen.getPreference(i)
                val value = sharedPreferences.getString(p.key, "")
                setPreferenceSummary(p,value!!)
            }
        }else {
            usuario=Usuario(0,"",0, emptyList())
            usuariosDao.nuevoUsuario(usuario)
        }
    }

    private fun setPreferenceSummary(
        preference: Preference,
        value: String
    ) {
        preference.summary = value
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val preference = findPreference(key)
        if (null != preference) { // Updates the summary for the preference
            val value = sharedPreferences.getString(preference.key, "")
            when(preference.key){
                "Nombre"->usuario.nombre=value!!
                else->usuario.peso=value!! as Int
            }
            usuariosDao.actualizar(usuario)
            setPreferenceSummary(preference, value!!)
        }
    }

   /* override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.opciones_usuario, container, false)

    }*/

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
