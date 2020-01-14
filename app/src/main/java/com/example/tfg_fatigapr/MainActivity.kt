package com.example.tfg_fatigapr

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
            FragmentoOpcionesUsuario()).commit()
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        val navHeader=navView.getHeaderView(0)
        var usuario=navHeader.findViewById<TextView>(R.id.nombreusuario)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val st=getString(R.string.key_editpref_nombre)
        val str=sharedPreferences.getString(st,"")!!
        usuario.text=str
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    0)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
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
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_inicio -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    FragmentoEjercicios()).commit()
            }
            R.id.nav_graficas -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                        FragmentoGraficas()).commit()
            }
            R.id.nav_calendario -> {

            }
            R.id.nav_opciones -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    FragmentoOpcionesUsuario()).commit()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    fun retjson():String{
        return "[{id:5dc68d22f5c6b0cd58eb6ad0,nombre:Leonor,peso:64,dia:[{id:2014-11-30,"+
                    "nombre:Georgette,modificaciones: 0,ejercicios: [{id: 0,peso: 14,RPE: 8,reps: 1"+
                    "},{id: 1,peso: 96,RPE: 8,reps: 6},{id: 2,peso: 13,RPE: 1,reps: 2}]},{id: 2015-01-06," +
                "nombre: Bowers,modificaciones: 1,ejercicios: [{id: 0,peso: 162,RPE: 5,reps: 3},{id: 1,peso: 90,"+
                "RPE: 10,reps: 5},{id: 2,peso: 160,RPE: 8,reps: 5}]},{id: 2015-03-11,nombre: Hancock,modificaciones: 2,"+
                "ejercicios: [{id: 0,peso: 199,RPE: 4,reps: 8},{id: 1,peso: 37,RPE: 1,reps: 1},{id: 2,peso: 179,RPE: 1,reps: 1}]}]}]"
    }

    /*fun mostrarOcultarRecyclerSeries(view: View){
        val recyclerSeries=findViewById<RecyclerView>(R.id.recycler_series)
        val CLayout=findViewById(R.id.CL_ejercicio) as ConstraintLayout
        Log.d("Boton Pulsado","botonP")
        if (CLayout.visibility==View.GONE) {
            CLayout.visibility = View.VISIBLE
            recyclerSeries.visibility = View.VISIBLE
        }
        else{
            CLayout.visibility=View.GONE
            recyclerSeries.visibility=View.GONE
        }
    }*/

}
