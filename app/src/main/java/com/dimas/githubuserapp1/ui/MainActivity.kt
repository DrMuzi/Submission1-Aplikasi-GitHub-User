package com.dimas.githubuserapp1.ui

import android.content.Intent
import android.content.res.Configuration
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimas.githubuserapp1.R
import com.dimas.githubuserapp1.adapter.OnItemClickCallback
import com.dimas.githubuserapp1.adapter.UserAdapter
import com.dimas.githubuserapp1.databinding.ActivityMainBinding
import com.dimas.githubuserapp1.data.User

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var dataName: Array<String>
    private lateinit var dataUsername: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataRepo: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataFollowers: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataPhoto: TypedArray
    private var users = arrayListOf<User>()
    private lateinit var toolbar: Toolbar
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        setToolbar()
        prepare()
        addItem()
    }

    private fun setAdapter() {
        adapter = UserAdapter()
        with(binding) {
            rvList.adapter = adapter
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                rvList.layoutManager = GridLayoutManager(this@MainActivity, 2, GridLayoutManager.HORIZONTAL, false)
            } else {
                rvList.layoutManager = LinearLayoutManager(this@MainActivity)
            }
            rvList.setHasFixedSize(true)
        }
        adapter.setOnItemClickCallback(object : OnItemClickCallback{
            override fun onItemClicked(user: User) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.KEY_USER, user)
                startActivity(intent)
            }

        })
    }

    private fun setToolbar() {
        toolbar = binding.toolbar
        toolbar.setTitleTextColor(resources.getColor(R.color.black_3333, theme))
        setSupportActionBar(toolbar)
    }

    private fun prepare() {
        dataName = resources.getStringArray(R.array.name)
        dataUsername = resources.getStringArray(R.array.username)
        dataPhoto = resources.obtainTypedArray(R.array.avatar)
        dataLocation = resources.getStringArray(R.array.location)
        dataRepo = resources.getStringArray(R.array.repository)
        dataCompany = resources.getStringArray(R.array.company)
        dataFollowers = resources.getStringArray(R.array.followers)
        dataFollowing = resources.getStringArray(R.array.following)
    }

    private fun addItem() {
        for (position in dataName.indices) {
            val user = User(
                dataUsername[position],
                dataName[position],
                dataLocation[position],
                dataCompany[position],
                dataRepo[position],
                dataFollowers[position],
                dataFollowing[position],
                dataPhoto.getResourceId(position, -1)
            )
            users.add(user)
        }
        adapter.users = users
    }
}