package es.ua.eps.filmoteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView.MultiChoiceModeListener
import android.widget.AdapterView
import android.widget.ListView
import es.ua.eps.filmoteca.databinding.ActivityFilmListBinding


var actionMode: ActionMode? = null

class FilmListActivity : AppCompatActivity() {
    companion object {
        private const val add_film = Menu.FIRST
        private const val about = Menu.FIRST + 1
    }
    // Utilizamos el primer id disponible (FIRST)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFilmListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val listFilm = findViewById<ListView>(R.id.filmList)
        listFilm.choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL
        refresh()

        listFilm.setOnItemClickListener{ parent: AdapterView<*>, view: View,
                                     position: Int, id: Long ->
            //val elemento = adapter.getItem(position)
            goFilm(position)
         }

        listFilm.setSelector(R.drawable.list_selector)
        listFilm.setMultiChoiceModeListener(

            object : MultiChoiceModeListener{
                override fun onCreateActionMode(mode: ActionMode,
                                                menu: Menu): Boolean {
                    val inflater = mode.menuInflater
                    inflater.inflate(R.menu.context_menu, menu)
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode,
                                                 menu: Menu): Boolean {
                    return false
                }

                override fun onActionItemClicked(mode: ActionMode,
                                                 item: MenuItem): Boolean {
                    return when (item.itemId) {
                        R.id.menuDeleteSelected -> {
                            deleteSelectedItems()
                            mode.finish()
                            true
                        }
                        else -> false
                    }
                }

                override fun onDestroyActionMode(mode: ActionMode) {}

                override fun onItemCheckedStateChanged(mode: ActionMode,
                                                       position: Int, id: Long, checked: Boolean) {
                    val count = listFilm.checkedItemCount
                    mode.title = "$count " + getString(R.string.selected)
                    if (checked) {
                        val selectedView = listFilm.getChildAt(position)
                        selectedView?.setBackgroundResource(R.drawable.list_selector)
                    } else {
                        val selectedView = listFilm.getChildAt(position)
                        selectedView?.setBackgroundResource(android.R.color.transparent)
                    }
                }
            }
        )
    }

    private fun deleteSelectedItems() {
        val selectedItems = ArrayList<Film>() // Lista para almacenar elementos seleccionados
        val listFilm = findViewById<ListView>(R.id.filmList)
        for (i in 0 until listFilm.count) {
            if (listFilm.isItemChecked(i)) {
                val film = listFilm.getItemAtPosition(i) as Film
                selectedItems.add(film)
            }
        }

        for (item in selectedItems) {
            // Elimina el elemento de tu lista de datos o de donde estés almacenando los elementos
            // Por ejemplo, si los elementos están en un ArrayList llamado films:
            FilmDataSource.films.remove(item)
        }

        // Notifica al adaptador de la lista que los datos han cambiado
        (listFilm.adapter as FilmsArrayAdapter).notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        // Identificador de grupo
        val groupId = Menu.NONE
        // Identificador único del elemento del menú.
        val itemId = add_film
        // Posición del elemento. Con NONE indicamos que nos es indiferente
        val itemOrder = Menu.NONE
        // Texto de la opción de menú
        val itemText = R.string.menuOptionAdd

        val groupId2 = Menu.NONE
        val itemId2 = about
        val itemOrder2 = Menu.NONE
        val itemText2 = R.string.menuOptionAbout

        val item1 = menu.add(groupId, itemId, itemOrder, itemText)
        val item2 = menu.add(groupId2, itemId2, itemOrder2, itemText2)

        item2.intent = Intent(this, AboutActivity::class.java)
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
            refresh()
            true
        }
        return true
    }

    private fun goFilm(position: Int){
        val intentFilm = Intent(this@FilmListActivity, FilmDataActivity::class.java)
        intentFilm.putExtra("position", position)
        startActivity(intentFilm)
    }

    private fun refresh(){
        val listFilm = findViewById<ListView>(R.id.filmList)
        val adapter = FilmsArrayAdapter(
            this,
            R.layout.item_film,
            FilmDataSource.films
        )
        listFilm.adapter = adapter
    }

}