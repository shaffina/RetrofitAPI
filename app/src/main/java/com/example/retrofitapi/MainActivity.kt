package com.example.retrofitapi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.retrofitapi.databinding.ActivityMainBinding
import com.example.retrofitapi.model.RickModel
import com.example.retrofitapi.network.ApiClient
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = ApiClient.getInstance()
        val response = client.getRick()

        // membuat kelas CustomAdapter untuk menangani tampilan daftar karakter
        class CustomAdapter(
            private val context: Context,
            private val names: List<String>,
            private val images: List<String>
        ) : BaseAdapter() {

            override fun getCount(): Int = names.size

            override fun getItem(position: Int): Any = names[position]

            override fun getItemId(position: Int): Long = position.toLong()

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.list_item_layout, null)

                val nameTextView: TextView = view.findViewById(R.id.tvCharacterName)
                val imageView: ImageView = view.findViewById(R.id.ivCharacterImage)

                // menetapkan nama karakter dan memuat gambar menggunakan picasso
                nameTextView.text = names[position]
                Picasso.get().load(images[position]).into(imageView)

                return view
            }
        }

        response.enqueue(object : Callback<RickModel> {
            override fun onResponse(call: Call<RickModel>, response: Response<RickModel>) {
                val thisResponse = response.body()
                val datas = thisResponse?.result ?: emptyList()

                // list untuk menyimpan nama dan URL gambar karakter
                val rickNames = ArrayList<String>()
                val rickImageView = ArrayList<String>()

                // mengisi list dengan nama dan URL gambar karakter dari response API
                for (i in datas) {
                    rickNames.add(i.name)
                    rickImageView.add(i.image)
                }

                // untuk menampilkan daftar karakter di ListView
                val customAdapter = CustomAdapter(this@MainActivity, rickNames, rickImageView)

                // menetapkan adapter ke ListView menggunakan View Binding
                binding.lvName.adapter = customAdapter

                // menambahkan OnClickListener pada item di ListView
                binding.lvName.setOnItemClickListener { _, _, position, _ ->
                    val selectedCharacterName = rickNames[position]
                    val selectedCharacterImage = rickImageView[position]

                    // intent untuk membuka DetailActivity dengan data karakter yang dipilih
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_CHARACTER_NAME, selectedCharacterName)
                    intent.putExtra(DetailActivity.EXTRA_CHARACTER_IMAGE, selectedCharacterImage)

                    startActivity(intent)
                }
            }
            override fun onFailure(call: Call<RickModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Connection error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
