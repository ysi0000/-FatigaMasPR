package com.example.tfg_fatigapr

import android.app.Activity
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.testing.FragmentScenario
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.tfg_fatigapr.Fragmentos.FragmentoEjercicios
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.tfg_fatigapr.TestUtils.withRecyclerView

/**
 * Test en emulador realizado para comprobar el funcionamiento de botones y elementos de la
 * interfaz de la aplicacion
 *
 * @author Yeray Sardon Ibañez
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class TestUIEnd2End{
    /**
     * Antes de realizar ningun test se debe lanzar el fragmento en el que se inicia la aplicacion
     *
     * @author Yeray Sardon Ibañez
     */
    @Before
    fun setup() {
        FragmentScenario.launchInContainer(FragmentoEjercicios::class.java)
    }
    /**
     * Funcion que permite capturar el boton para acceder al menu
     *
     * @author Yeray Sardon Ibañez
     */
    fun <T : Activity> ActivityScenario<T>.getToolbarNavigationContentDescription()
            : String {
        var description = ""
        onActivity {
            description =
                it.findViewById<Toolbar>(R.id.toolbar).navigationContentDescription as String
        }
        return description
    }
    /**
     * Test para comprobar la apertura y el cierre de la barra de menu
     *
     * @author Yeray Sardon Ibañez
     */
    @Test
    fun comprobarNav_AbiertoCerrado(){
        val activityScenario=ActivityScenario.launch(MainActivity::class.java)
        //Nav View Cerrado
        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.START)))
        onView(
            withContentDescription(
                activityScenario
                    .getToolbarNavigationContentDescription()
            )
        ).perform(click())
        //Nav View Abierto
        onView(withId(R.id.drawer_layout))
            .check(matches(isOpen(Gravity.START)))
        pressBack()
        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.START)))
        activityScenario.close()
    }
    /**
     * Se abren todos los fragmentos para comprobar que no haya ningun fallo
     *
     * @author Yeray Sardon Ibañez
     */
    @Test
    fun comprobarAperturaFragmentos(){
        val activityScenario=ActivityScenario.launch(MainActivity::class.java)

        onView(
            withContentDescription(
                activityScenario
                    .getToolbarNavigationContentDescription()
            )
        ).perform(click())

        onView(withText("Inicio")).perform(click())
        onView(withId(R.id.bt_anadirEjercicio)).check(matches(isDisplayed()))
        onView(
            withContentDescription(
                activityScenario
                    .getToolbarNavigationContentDescription()
            )
        ).perform(click())
        onView(withText("Graficos")).perform(click())
        onView(withId(R.id.linechart)).check(matches(isDisplayed()))

        onView(
            withContentDescription(
                activityScenario
                    .getToolbarNavigationContentDescription()
            )
        ).perform(click())
        onView(withText("RM Estimada")).perform(click())
        onView(withId(R.id.lineChartPR)).check(matches(isDisplayed()))

        onView(
            withContentDescription(
                activityScenario
                    .getToolbarNavigationContentDescription()
            )
        ).perform(click())
        onView(withText("Opciones Usuario")).perform(click())
        onView(withText("Nombre")).check(matches(isDisplayed()))
        activityScenario.close()

    }

    /**
     * Se abren todos los fragmentos para comprobar que no haya ningun fallo
     *
     * @author Yeray Sardon Ibañez
     */

    class RecyclerViewItemCountAssertion(expCount:Int) : ViewAssertion {
         var expectedCount:Int=0


        init {
            expectedCount=expCount
        }

        @Override
        override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
            if(noViewFoundException!=null){
                throw noViewFoundException
            }
            val recyclerView=view as RecyclerView
            val adapter=recyclerView.adapter!!
            assertThat(adapter.itemCount, `is`(expectedCount))
        }
    }

    /**
     * Se comprueba que se puede añadir un ejercicio
     *
     * @author Yeray Sardon Ibañez
     */
    @Test
    fun comprobar_anadirEjercicio(){
        val activityScenario=ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.bt_anadirEjercicio)).perform(click())
        onView(withId(R.id.bt_aEjercicio)).check(matches(isDisplayed()))
        onView(withId(R.id.bt_nuevoEj)).perform(click())
        onView(withText("Tiron Vertical")).perform(click())
        onView(withText("Dominadas")).perform(click())
        onView(withId(R.id.bt_aEjercicio)).perform(scrollTo(),click())
        onView(withId(R.id.bt_anadirEjercicio)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_ejercicios))

        //onView(withId(R.id.recycler_ejercicios)).check(RecyclerViewItemCountAssertion(1))
        onView(withRecyclerView(R.id.recycler_ejercicios)
            .atPositionOnView(0, R.id.tv_anadirSerie))
            .perform(click())
        //onView(withId(R.id.recycler_ejercicios)).perform(RecyclerViewActions.actionOnItemAtPosition<AdaptadorEjercicios.MyViewHolder>(1,click()))
    }
    /**
     * Se comprueba que se puede añadir una serie
     *
     * @author Yeray Sardon Ibañez
     */
    @Test
    fun anadirSerie(){
        onView(withId(R.id.tv_anadirSerie)).perform(click())

    }
}