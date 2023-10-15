package es.ua.eps.filmoteca

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FilmDataFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val buttonEdit = activity?.findViewById<Button>(R.id.filmEdit)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film_data, container, false)
    }

    fun setDetalleItem(position: Int){
        val tvDirectorName = activity?.findViewById<TextView>(R.id.nameDirectorBladeRunner)
        val tvTitle = activity?.findViewById<TextView>(R.id.filmData)
        val tvYear = activity?.findViewById<TextView>(R.id.yearPublicationBladeRunner)
        val tvGender = activity?.findViewById<TextView>(R.id.filmGenderBladeRunner)
        val tvFormat = activity?.findViewById<TextView>(R.id.filmFormatBladeRunner)
        val tvImage = activity?.findViewById<ImageView>(R.id.bladeRunnerImage)
        val tvComments = activity?.findViewById<TextView>(R.id.filmComment)
        val filmLink = activity?.findViewById<Button>(R.id.IMDBLink)



        val film = FilmDataSource.films[position]

        tvTitle!!.text = film.title
        tvDirectorName!!.text = film.director
        tvYear!!.text = film.year.toString()
        tvComments!!.text = film.comments


        val resources: Resources = resources
        val genderOptions = resources.getStringArray(R.array.genderOption)
        tvGender!!.text = genderOptions[film.genre]

        val formatOptions = resources.getStringArray(R.array.formatOption)
        tvFormat!!.text = formatOptions[film.format]

        tvImage!!.setImageResource(film.imageResId)

        filmLink!!.setOnClickListener {
            val linkIntent = Intent(Intent.ACTION_VIEW, Uri.parse(film.imdbUrl))
            startActivity(linkIntent)
        }
    }
    private fun getGenreIndex(genre: String): Int {
        val resources = resources
        val generos = resources.getStringArray(R.array.genderOption)

        for (i in generos.indices) {
            if (generos[i] == genre) {
                return i
            }
        }

        return -1 // Valor predeterminado si no se encuentra el género
    }
    private fun getFormatIndex(format: String): Int {
        val resources = resources
        val formatos = resources.getStringArray(R.array.formatOption)
        for (i in formatos.indices) {
            if (formatos[i] == format) {
                return i
            }
        }
        return -1 // Valor predeterminado si no se encuentra el formato
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FilmDataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}