package com.example.tfg_fatigapr.DAOs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.tfg_fatigapr.Utilidades.RoomDataBase
import com.example.tfg_fatigapr.clasesDatos.Ejercicio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Clase de tests de Ejercicios, en este test se comprobaran si añadir,modificar o eliminar ejercicios se
 * realiza de manera correcta
 *
 * @author Yeray Sardon Ibañez
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@SmallTest

class DAOEjerciciosTest {

    /**
     * Esta regla hace que todas las acciones se realizen de forma sincrona, cosa que mantiene los
     * resultados en orden y sin sorpresas
     *
     * @author Yeray Sardon Ibañez
     */
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RoomDataBase
    /**
     * Antes de realizar ningun test se inicializa la base de datos
     *
     * @author Yeray Sardon Ibañez
     */
    @Before
    fun initDb() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            RoomDataBase::class.java
        ).build()
    }
    /**
     * Despues de realizar los tests se cierra la base de datos para evitar perdidas de memoria
     *
     * @author Yeray Sardon Ibañez
     */
    @After
    fun closeDb() = database.close()

    /**
     * Se realizan los tests de insertar y eliminar ejercicios
     *
     * @author Yeray Sardon Ibañez
     */
    @Test
    fun insertaryeliminarEjercicio() {
        // GIVEN - Insert a task.
        val ejercicio = Ejercicio(0,"Press","Mod1","1-1-12","1")
        database.ejercicioDAO().añadirEjercicio(ejercicio)

        // WHEN - Get the task by id from the database.
        val loadedInsertar = database.ejercicioDAO().seleccionarEjercicios(ejercicio.dia)
        val loaded1=loadedInsertar[0]
        val size=database.ejercicioDAO().ejerciciosDia(ejercicio.dia,"1")

        // THEN - The loaded data contains the expected values.
        assertThat(size,`is`(loadedInsertar.size))
        assertThat<Ejercicio>(loaded1, notNullValue())
        assertThat(loaded1.id, `is`(ejercicio.id))
        assertThat(loaded1.dia, `is`(ejercicio.dia))
        assertThat(loaded1.modificaciones, `is`(ejercicio.modificaciones))
        assertThat(loaded1.nombre, `is`(ejercicio.nombre))

        database.ejercicioDAO().eliminarEjercicio(ejercicio)
        val loadedEliminar = database.ejercicioDAO().seleccionarEjercicios(ejercicio.dia)


        assertThat(loadedEliminar.size,`is`(0))
    }


}