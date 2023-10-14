package es.ua.eps.filmoteca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import es.ua.eps.filmoteca.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i("add","Prueba")

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
}