package es.ua.eps.filmoteca

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class FilmsArrayAdapter (context: Context?, resource:Int, objects: List<Film>): ArrayAdapter<Film>(context!!, resource, objects!!){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup) : View{

        val view: View = convertView?: LayoutInflater.from(this.context).inflate(R.layout.item_film, parent, false)

        val tvFilmName = view.findViewById(R.id.filmName) as TextView
        val tvDirectorName = view.findViewById(R.id.directorName) as TextView
        val ivFilmIcon = view.findViewById(R.id.filmIcon) as ImageView

        getItem(position) ?.let {
            tvFilmName.text = it.title
            tvDirectorName.text = it.director
            ivFilmIcon.setImageResource(it.imageResId)
        }
        return view
    }
}