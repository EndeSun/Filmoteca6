package es.ua.eps.filmoteca

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import es.ua.eps.filmoteca.databinding.ActivityFilmDataBinding

private const val MOVIE = 123
private var position = 0
class FilmDataActivity : AppCompatActivity() {

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
    private fun printFilmData(position: Int){
        val film = FilmDataSource.films[position]
        val filmData = findViewById<TextView>(R.id.filmData)
        val filmDirectorName = findViewById<TextView>(R.id.nameDirectorBladeRunner)
        val filmYear = findViewById<TextView>(R.id.yearPublicationBladeRunner)
        val filmGender = findViewById<TextView>(R.id.filmGenderBladeRunner)
        val filmFormat = findViewById<TextView>(R.id.filmFormatBladeRunner)
        val filmComment = findViewById<TextView>(R.id.filmComment)


        filmData.text = film.title
        filmDirectorName.text = film.director
        filmYear.text = film.year.toString()
        filmComment.text = film.comments

        val resources: Resources = resources
        val genderOptions = resources.getStringArray(R.array.genderOption)
        filmGender.text = genderOptions[film.genre]

        val formatOptions = resources.getStringArray(R.array.formatOption)
        filmFormat.text = formatOptions[film.format]


        val filmLink = findViewById<Button>(R.id.IMDBLink)
        filmLink.setOnClickListener {
            val linkIntent = Intent(Intent.ACTION_VIEW, Uri.parse(film.imdbUrl))
            startActivity(linkIntent)
        }

        val filmImage = findViewById<ImageView>(R.id.bladeRunnerImage)
        filmImage.setImageResource(film.imageResId)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFilmDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        position = intent.extras!!.getInt("position")
        printFilmData(position)
        //FILM EDIT
        binding.filmEdit.setOnClickListener {
            val filmEditIntent = Intent(this@FilmDataActivity, FilmEditActivity::class.java)

            filmEditIntent.putExtra("position", position)

            if(Build.VERSION.SDK_INT >= 30) {
                startForResult.launch(filmEditIntent)
            }
            else {
                @Suppress("DEPRECATION")
                startActivityForResult(filmEditIntent, MOVIE)
            }
        }
        //HOME
        binding.backToHome.setOnClickListener {
            goHome()
        }
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == android.R.id.home) { // ID especial para botón "home"
            NavUtils.navigateUpTo(this,
                Intent(this, FilmListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goHome(){
        val backToHomeIntent = Intent(this@FilmDataActivity, FilmListActivity::class.java)
        backToHomeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(backToHomeIntent)
    }

    private val startForResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            onActivityResult(MOVIE, result.resultCode, result.data)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MOVIE){
            if (resultCode == Activity.RESULT_OK){
                val film = FilmDataSource.films[position]

                val title = data?.getStringExtra("inputFilmTitle")
                val director = data?.getStringExtra("inputDirectorName")
                val year = data?.getIntExtra("inputYear", R.string.yearPublicationBladeRunner)
                if (year != null) {
                    film.year = year
                }
                val imdbUrl = data?.getStringExtra("inputLink")
                val gender = data?.getStringExtra("inputGender")
                val format = data?.getStringExtra("inputFormat")
                val comments = data?.getStringExtra("inputComment")

                film.genre = getGenreIndex(gender!!)
                film.format = getFormatIndex(format!!)
                film.title = title
                film.director = director
                film.imdbUrl = imdbUrl
                film.comments = comments

                printFilmData(position)
            }
            else if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this,"Edición cancelada", Toast.LENGTH_LONG).show()
            }
        }
    }
}