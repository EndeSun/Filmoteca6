package es.ua.eps.filmoteca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmAdapter(val film: List<Film>) : RecyclerView.Adapter<FilmAdapter.ViewHolder?>() {

    private var listener: (position: Int) -> Unit = {}
    fun setOnItemClickListener(listener: (position:Int) -> Unit) {
        this.listener = listener // Guardamos una referencia al listener
    }
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var title: TextView
        private var director: TextView
        private var filmImage: ImageView

        fun bind(l: Film) {
            title.text = l.title
            director.text = l.director
            filmImage.setImageResource(l.imageResId)
        }
        init {
            title = v.findViewById(R.id.filmTitle)
            director = v.findViewById(R.id.filmDirector)
            filmImage = v.findViewById(R.id.ivList)
        }
    }
    //    Método para crear los diferentes items de la lista, hace referencia al layout de los items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film2, parent, false)
        val holder = ViewHolder(view)

        view.setOnClickListener {
            val position: Int = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener(position)
            }
        }

        return holder
    }

    //        Número total de datos que hay en la lista
    override fun getItemCount(): Int = FilmDataSource.films.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Muestra los datos en la posición indicada
        holder.bind(FilmDataSource.films[position])
    }
}