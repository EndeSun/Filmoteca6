package es.ua.eps.filmoteca

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.NavUtils
import es.ua.eps.filmoteca.databinding.ActivityAboutBinding


class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goWeb.setOnClickListener{
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"))
            if(webIntent.resolveActivity(packageManager) != null){
                startActivity(webIntent)
            }
        }

        binding.goSupport.setOnClickListener{
            val supportIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:1195562121ende@gmail.com"))
            if(supportIntent.resolveActivity(packageManager) != null){
                startActivity(supportIntent)
            }
        }

        binding.goBack.setOnClickListener {
            finish()
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

    fun onFunctionalismClick(view: View){
        // val button = findViewById<Button>(R.id.button3) así se toma una referencia de los componentes de la actividad para importar alt + enter

        mostrarToast(getString(R.string.message))
    }
    private fun mostrarToast(mensaje:String){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}