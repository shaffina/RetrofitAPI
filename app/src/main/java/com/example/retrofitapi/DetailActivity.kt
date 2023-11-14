package com.example.retrofitapi

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitapi.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // binding untuk menghubungkan dengan layout XML
        val characterName = intent.getStringExtra(EXTRA_CHARACTER_NAME)
        val characterImage = intent.getStringExtra(EXTRA_CHARACTER_IMAGE)

        // mendapatkan data karakter dari intent yang dikirimkan dari MainActivity
        supportActionBar?.title = characterName

        // menetapkan teks TextView dengan nama karakter menggunakan View Binding
        binding.tvCharacterName.text = characterName

        // Menggunakan Picasso untuk memuat dan menampilkan gambar karakter dari URL ke ImageView
        Picasso.get().load(characterImage).into(binding.ivCharacterImage)

        // intent untuk kembali ke MainActivity
        val backClick = findViewById<ImageView>(R.id.backButton)
        backClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // menyimpan konstanta kunci ekstra yang digunakan dalam intent
    companion object {
        const val EXTRA_CHARACTER_NAME = "extra_character_name"
        const val EXTRA_CHARACTER_IMAGE = "extra_character_image"
    }
}


