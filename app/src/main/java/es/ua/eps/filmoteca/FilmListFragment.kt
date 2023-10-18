package es.ua.eps.filmoteca

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.ListFragment

class FilmListFragment : ListFragment(){
    var callback: OnItemSelectedListener? = null
    companion object {
        private const val add_film = Menu.FIRST
        private const val about = Menu.FIRST + 1
    }
    //Variable global del adaptador del FilmArrayAdapter
    lateinit var adapter: FilmsArrayAdapter

    //Interfaz para comunicar con el filmData
    interface OnItemSelectedListener {
        fun onItemSelected(position: Int, listView: ListView)
    }

    //Para pasar al filmData
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = try {
            context as OnItemSelectedListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString()
                    + " debe implementar OnItemSelectedListener")
        }
    }
    //Creamos el fragmento
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        adapter = FilmsArrayAdapter(activity, R.layout.item_film, FilmDataSource.films)
        listAdapter = adapter
    }

    override fun onListItemClick(l: ListView, v: View,
                                 position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        callback?.onItemSelected(position, listView)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Identificador de grupo
        val groupId = Menu.NONE
        // Identificador único del elemento del menú.
        val itemId = FilmListFragment.add_film
        // Posición del elemento. Con NONE indicamos que nos es indiferente
        val itemOrder = Menu.NONE
        // Texto de la opción de menú
        val itemText = R.string.menuOptionAdd
        val groupId2 = Menu.NONE
        val itemId2 = FilmListFragment.about
        val itemOrder2 = Menu.NONE
        val itemText2 = R.string.menuOptionAbout
        val item1 = menu.add(groupId, itemId, itemOrder, itemText)
        val item2 = menu.add(groupId2, itemId2, itemOrder2, itemText2)
        item2.intent = Intent(context, AboutActivity::class.java)
        item1.setOnMenuItemClickListener {
            val f5 = Film()
            f5.title = getString(R.string.film5)
            f5.director = "Kenneth Branagh"
            f5.imageResId = R.mipmap.ic_launcher
            f5.comments = ""
            f5.format = Film.FORMAT_DIGITAL
            f5.genre = Film.GENRE_ACTION
            f5.imdbUrl = "https://www.imdb.com/title/tt0800369/?ref_=fn_al_tt_1"
            f5.year = 2011
            FilmDataSource.films.add(f5)
            listAdapter = adapter
            true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}