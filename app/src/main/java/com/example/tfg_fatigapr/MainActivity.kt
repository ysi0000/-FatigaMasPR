package com.example.tfg_fatigapr

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:Int=0
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    inline fun <reified T> genericType() = object: TypeToken<T>() {}.type
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val usuarios:List<Usuario>
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        var gson=Gson()
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
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
        } else {
            // Permission has already been granted
            /*var gson=Gson()
            var f=File("/sdcard/Download/file.json").readText()*/
           /* val bufferedReader: BufferedReader = f.bufferedReader()
            val inputString = bufferedReader.use { it.readText() }*/
            var jsonPrueba=retjson()

            try {

                /*val turnsType = object : TypeToken<List<Usuario>>() {}.type
                var post= gson.fromJson<List<Usuario>>(inputString, turnsType)*/
                val tipoUsurio = genericType<List<Usuario>>()
                usuarios = Gson().fromJson<List<Usuario>>(jsonPrueba, tipoUsurio)
                viewManager = LinearLayoutManager(this)
                viewAdapter = AdaptadorEjercicios(usuarios[0].dia)
                var dia=findViewById<TextView>(R.id.dia)
                dia.text=usuarios[0].dia[0].id
                recyclerView = findViewById<RecyclerView>(R.id.recycler_ejercicios).apply {
                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    setHasFixedSize(true)

                    // use a linear layout manager
                    layoutManager = viewManager

                    // specify an viewAdapter (see also next example)
                    adapter = viewAdapter
                }
                Log.d("TAG","TAG "+usuarios[0].nombre)
            }catch (e:Exception){
                Log.d("TAG","Error json")
            }
        }

        val permissionCheck = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )




        }

        /*var f=File("/sdcard/Download/file.json")
        val bufferedReader: BufferedReader = f.bufferedReader()
        //gson.fromJson<>()
        val inputString = bufferedReader.use { it.readText() }
        var post = gson.fromJson(inputString, Usuario::class.java)*/


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
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_graficas -> {
                var graficaIntent=Intent(this,Graficas::class.java)
                startActivity(graficaIntent)
            }
            R.id.nav_calendario -> {

            }
            R.id.nav_opciones -> {

            }
            /*R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }*/
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
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    var gson=Gson()
                    var f=File("/sdcard/Download/file.json")
                    val bufferedReader: BufferedReader = f.bufferedReader()
                    //gson.fromJson<>()
                    val inputString = bufferedReader.use { it.readText() }
                    var post = gson.fromJson(inputString, Usuario::class.java)
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
