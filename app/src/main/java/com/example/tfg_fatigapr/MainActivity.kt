package com.example.tfg_fatigapr


import android.os.Bundle
import android.util.Log
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.TextView
import androidx.preference.PreferenceManager
import java.lang.Exception


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
try {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val toolbar: Toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)

    val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
    val navView: NavigationView = findViewById(R.id.nav_view)
    supportFragmentManager.beginTransaction().replace(
        R.id.fragment_container,
        FragmentoEjercicios()
    ).commit()
    val toggle = ActionBarDrawerToggle(
        this,
        drawerLayout,
        toolbar,
        R.string.navigation_drawer_open,
        R.string.navigation_drawer_close
    )
    drawerLayout.addDrawerListener(toggle)
    toggle.syncState()
    navView.setNavigationItemSelectedListener(this)
    val navHeader = navView.getHeaderView(0)
    val usuario = navHeader.findViewById<TextView>(R.id.nombreusuario)
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    val st = getString(R.string.key_editpref_nombre)
    val str = sharedPreferences.getString(st, "")!!
    usuario.text = str
}catch (ex:Exception){
    Log.d("Hola",ex.message)
}


        }


    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //var intent=Intent(this,ActividadOpciones::class.java)
        when (item.itemId) {
            R.id.action_settings -> true//startActivity(intent)
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        try {
            // Handle navigation view item clicks here.
            when (item.itemId) {
                R.id.nav_inicio -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container,
                        FragmentoEjercicios()
                    ).commit()
                }
                R.id.nav_graficas -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container,
                        FragmentoGraficas()
                    ).commit()
                }
                R.id.nav_calendario -> {

                }
                R.id.nav_opciones -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container,
                        FragmentoOpcionesUsuario()
                    ).commit()
                }
            }
            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            drawerLayout.closeDrawer(GravityCompat.START)
        }catch (ex:Exception){
            Log.d("Hola",ex.message)
        }
        return true
    }

}
