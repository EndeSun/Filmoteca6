package es.ua.eps.filmoteca

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment

class FilmListFragment : ListFragment() {

    var callback: OnItemSelectedListener? = null
    interface OnItemSelectedListener {
        fun onItemSelected(position: Int)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = try {
            context as OnItemSelectedListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString()
                    + " debe implementar OnItemSelectedListener")
        }
//        callback = try {
//            context as OnItemSelectedListener
//        } catch (e: ClassCastException) {
//            throw ClassCastException(context.toString()
//                    + " debe implementar OnItemSelectedListener")
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter: ArrayAdapter<Film> =
            FilmsArrayAdapter(activity, R.layout.item_film, FilmDataSource.films)
        //Es el parámetro del listFragment
        listAdapter = adapter

    }
    override fun onListItemClick(l: ListView, v: View,
                                 position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        callback?.onItemSelected(position)
    }
}