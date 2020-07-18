package com.example.tfg_fatigapr



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.preference.PreferenceManager
import com.example.tfg_fatigapr.Fragmentos.*
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mFireBaseAuth:FirebaseAuth
    private lateinit var mAuthStateListener:FirebaseAuth.AuthStateListener
    private val RC_SIGN_IN=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFireBaseAuth=FirebaseAuth.getInstance()
        val providers = arrayListOf(
            EmailBuilder().build(),
            GoogleBuilder().build())
        mAuthStateListener= FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user=firebaseAuth.currentUser
            if(user!=null){
                onSingedInInitialize(user.displayName!!)
            }else{
               //onSignedOutCleanUp()
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder().setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .setTheme(R.style.Login).setLogo(R.mipmap.app_ic)
                        .build(), RC_SIGN_IN)
            }
        }


    }

    private fun onSingedInInitialize(username:String){
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        try {
            val fragmentoNoAnadido=supportFragmentManager.findFragmentById(R.id.fragment_container)!!.isAdded
            if(fragmentoNoAnadido)
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    FragmentoEjercicios()
                ).commit()
        }catch (ex:Exception){
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                FragmentoEjercicios()
            ).commit()
        }
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
        val str = sharedPreferences.getString(st, username)!!
        usuario.text = str


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN && resultCode== RESULT_CANCELED){
            finish()

        }
    }

    fun onSignedOutCleanUp(){
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
        val navHeader = navView.getHeaderView(0)
        val usuario = navHeader.findViewById<TextView>(R.id.nombreusuario)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val st = getString(R.string.key_editpref_nombre)
        val str = sharedPreferences.getString(st, "")!!
        usuario.text = str
    }

    override fun onResume() {
        super.onResume()
        mFireBaseAuth.addAuthStateListener(mAuthStateListener)
    }

    override fun onPause() {
        super.onPause()
        mFireBaseAuth.removeAuthStateListener(mAuthStateListener)

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
                R.id.nav_RMEstimada -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container,
                        FragmentoPR()
                    ).commit()
                }
                R.id.nav_opciones -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container,
                        FragmentoOpcionesUsuario()
                    ).commit()
                }
                R.id.nav_ayuda -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container,
                        FragmentoAyuda()
                    ).commit()
                }
                R.id.nav_signOut->{
                    AuthUI.getInstance().signOut(this)
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
