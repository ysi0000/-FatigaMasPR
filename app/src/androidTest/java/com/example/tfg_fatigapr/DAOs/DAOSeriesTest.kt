package com.example.tfg_fatigapr.DAOs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.tfg_fatigapr.Utilidades.RoomDataBase
import com.example.tfg_fatigapr.clasesDatos.Serie
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Clase de tests de Series, en este test se comprobaran si añadir,modificar o eliminar series se
 * realiza de manera correcta
 *
 * @author Yeray Sardon Ibañez
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@SmallTest
class DAOSeriesTest {

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
            ApplicationProvider.getApplicationContext(),
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
     * Se realizan los tests de insertar y eliminar series a un ejercicio
     *
     * @author Yeray Sardon Ibañez
     */
    @Test
    fun insertaryEliminarSerie() {
        // GIVEN - Insert a task.
        val datosIniciales=0
        val serie = Serie(datosIniciales,datosIniciales,datosIniciales,datosIniciales,"1-1-20",datosIniciales)
        database.serieDAO().insertarSerie(serie)

        // WHEN - Get the task by id from the database.
        val loadedInsertar = database.serieDAO().seleccionarSeriesDia(serie.dia)
        val loaded1=loadedInsertar[0]


        // THEN - The loaded data contains the expected values.
        MatcherAssert.assertThat<Serie>(loaded1, notNullValue())
        MatcherAssert.assertThat(loaded1.id, `is`(serie.id))
        MatcherAssert.assertThat(loaded1.dia, `is`(serie.dia))
        MatcherAssert.assertThat(loaded1.reps,`is`(serie.reps))
        MatcherAssert.assertThat(loaded1.peso, `is`(serie.peso))
        MatcherAssert.assertThat(loaded1.idEjercicio, `is`(serie.idEjercicio))
        MatcherAssert.assertThat(loaded1.RPE, `is`(serie.RPE))


        database.serieDAO().eliminarSerie(serie)
        val loadedEliminar = database.serieDAO().seleccionarSeriesDia(serie.dia)


        MatcherAssert.assertThat(loadedEliminar.size, `is`(0))
    }

    /**
     * En este test se actualiza el peso, RPE y repeticiones de las Series
     *
     * @author Yeray Sardon Ibañez
     */
    fun actualizarDatosSerie(){
        val dia="1-1-20"
        val serie = Serie(0,0,0,0,"1-1-20",0)
        database.serieDAO().insertarSerie(serie)

        // WHEN - Get the task by id from the database.
        val loadedInsertar = database.serieDAO().seleccionarSeriesDia(serie.dia)

        val valorActualizado=12
        database.serieDAO().actualizarPeso(valorActualizado,dia,0,0)
        database.serieDAO().actualizarReps(valorActualizado,dia,0,0)
        database.serieDAO().actualizarRPE(valorActualizado,dia,0,0)

        val loaded1=loadedInsertar[0]

        // THEN - The loaded data contains the expected values.
        MatcherAssert.assertThat<Serie>(loaded1, notNullValue())
        MatcherAssert.assertThat(loaded1.reps,`is`(valorActualizado))
        MatcherAssert.assertThat(loaded1.peso, `is`(valorActualizado))
        MatcherAssert.assertThat(loaded1.RPE, `is`(valorActualizado))


    }

}