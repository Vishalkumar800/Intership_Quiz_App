package com.rach.quizappintership

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import com.rach.quizappintership.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var quizModellist : MutableList<QuizModel>

    private lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModellist = mutableListOf()

        getDataFromFirebase()


    }



    private fun getDataFromFirebase() {

        binding.progressBar.visibility = View.VISIBLE

        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener {datasnapshot ->

                if (datasnapshot.exists()){

                    for (snapshot in datasnapshot.children){

                        val quizModel = snapshot.getValue(QuizModel::class.java)
                        if (quizModel != null){
                            quizModellist.add(quizModel)
                        }

                    }

                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.adapter = QuizListAdapter(this,quizModellist)

                }

            }
            .addOnFailureListener {
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }

    }
}