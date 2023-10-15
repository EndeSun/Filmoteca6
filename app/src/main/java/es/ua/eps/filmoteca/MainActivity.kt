package es.ua.eps.filmoteca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import es.ua.eps.filmoteca.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FilmListFragment.OnItemSelectedListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (findViewById<View?>(R.id.fragment_container) != null) {
            // Si se está restaurando, no hace falta cargar el fragmento
            if (savedInstanceState != null) return
            // Creamos el fragmento
            val fragment = FilmListFragment()
            // Pasamos los extras del intent al fragmento
            fragment.arguments = intent.extras

            // Añadimos el fragmento al contenedor
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment).commit()
        }
    }

    override fun onItemSelected(position: Int) {
        var detalleFragment = supportFragmentManager.findFragmentById(R.id.fragment_data) as FilmDataFragment?

        if (detalleFragment != null) {
            // Tipo estático: actualizamos directamente el fragmento
            detalleFragment.setDetalleItem(position)
        } else {
            // Tipo dinámico: hacemos transición al nuevo fragmento
            detalleFragment = FilmDataFragment()

//            val args = Bundle()
//            args.putInt(PARAM_POSICION, position)
//            detalleFragment.arguments = args

            val t = supportFragmentManager.beginTransaction()
            t.replace(R.id.fragment_container, detalleFragment)
            t.addToBackStack(null)
            t.commit()
        }
    }

}