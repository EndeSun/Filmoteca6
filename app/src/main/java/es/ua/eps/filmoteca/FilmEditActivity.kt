package es.ua.eps.filmoteca

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import es.ua.eps.filmoteca.databinding.ActivityFilmEditBinding

private var position = 0
private const val IMAGE_PICK = 1
private const val REQUEST_IMAGE_CAPTURE = 2

@Suppress("DEPRECATION")
class FilmEditActivity : AppCompatActivity() {

    @SuppressLint("IntentReset")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFilmEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        position = intent.extras!!.getInt("position")
        val film = FilmDataSource.films[position]
        binding.bladeRunnerImageEdit.setImageResource(film.imageResId)
        binding.inputFilmTitle.setText(film.title)
        binding.inputComment.setText(film.comments)
        binding.inputLinkIMDB.setText(film.imdbUrl)
        binding.inputDirectorName.setText(film.director)
        binding.inputYear.setText(film.year.toString())


        binding.save.setOnClickListener {
            val intentInfoChange = Intent()
            intentInfoChange.putExtra("inputFilmTitle", binding.inputFilmTitle.text.toString())
            intentInfoChange.putExtra("inputDirectorName",binding.inputDirectorName.text.toString())
            intentInfoChange.putExtra("inputYear",binding.inputYear.text.toString().toInt())
            intentInfoChange.putExtra("inputLink",binding.inputLinkIMDB.text.toString())
            val genderOption = binding.genderOption.selectedItem.toString()
            val formatOption = binding.formatOption.selectedItem.toString()
            intentInfoChange.putExtra("inputGender",genderOption)
            intentInfoChange.putExtra("inputFormat",formatOption)
            intentInfoChange.putExtra("inputComment",binding.inputComment.text.toString())

//            val drawable =  binding.bladeRunnerImageEdit
//            val bitmap = (drawable as BitmapDrawable).bitmap
//            val stream = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//            val byteArray = stream.toByteArray()
//            intentInfoChange.putExtra("inputPhoto", byteArray)
            setResult(Activity.RESULT_OK, intentInfoChange)
            finish()
        }

        binding.cancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        binding.selectImage.setOnClickListener {
            val photoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            photoIntent.type = "image/* video/*"
            if(Build.VERSION.SDK_INT >= 30) {
                startForResult.launch(photoIntent)
            }
            else {
                @Suppress("DEPRECATION")
                startActivityForResult(photoIntent, IMAGE_PICK)
            }
        }

        binding.catchPhoto.setOnClickListener {
            val cameraIntent =  Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if(Build.VERSION.SDK_INT >= 30) {
                startForResultPhoto.launch(cameraIntent)
            }
            else {
                @Suppress("DEPRECATION")
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
            }
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

    private val startForResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            onActivityResult(IMAGE_PICK, result.resultCode, result.data)
        }

    private val startForResultPhoto =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            onActivityResult(REQUEST_IMAGE_CAPTURE, result.resultCode, result.data)
        }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val bitmap = this.getBitmapFromUri(selectedImageUri)

            if (bitmap != null) {
                findViewById<ImageView>(R.id.bladeRunnerImageEdit).visibility = View.VISIBLE
                findViewById<ImageView>(R.id.bladeRunnerImageEdit).setImageBitmap(bitmap)
            }
        }
        else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            val imageBitmap = data.extras?.get("data") as Bitmap
            findViewById<ImageView>(R.id.bladeRunnerImageEdit).setImageBitmap(imageBitmap)
            findViewById<ImageView>(R.id.bladeRunnerImageEdit).visibility = View.VISIBLE
        }
    }
    private fun getBitmapFromUri(uri: Uri?): Bitmap? {
        return try {
            val inputStream = contentResolver.openInputStream(uri!!)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}


