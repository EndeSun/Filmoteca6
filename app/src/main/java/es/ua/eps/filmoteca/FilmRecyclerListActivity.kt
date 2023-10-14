package es.ua.eps.filmoteca

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.ua.eps.filmoteca.databinding.ActivityFilmRecyclerListBinding


class FilmRecyclerListActivity: AppCompatActivity(){
    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_recycler_list)
        val binding = ActivityFilmRecyclerListBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Donde se pita la lista
        recyclerView = findViewById(R.id.listaRecycler)
//        Un manejador de layout lineal
        layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)

        val adapter = FilmAdapter(FilmDataSource.films)
//        Asignamos el adaptaddor que hemos creado
        recyclerView?.adapter = adapter
        this.adapter = adapter

        adapter.setOnItemClickListener {
            position ->  goFilm(position)
        }


    }
    private fun goFilm(position: Int){
        val intentFilm = Intent(this@FilmRecyclerListActivity, FilmDataActivity::class.java)
        intentFilm.putExtra("position", position)
        startActivity(intentFilm)
    }
}