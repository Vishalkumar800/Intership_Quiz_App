package com.rach.quizappintership

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rach.quizappintership.databinding.QuizItemLayoutBinding

class QuizListAdapter(private val context: Context, val list: List<QuizModel>) :
    RecyclerView.Adapter<QuizListAdapter.QuizViewHolder>() {

    inner class QuizViewHolder(val binding: QuizItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = QuizItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return QuizViewHolder(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {

        val data = list[position]

        holder.binding.quizTitleText.text = data.title
        holder.binding.quizSubtitleText.text = data.subtitle
        holder.binding.time.text = data.time+" min"

        holder.itemView.setOnClickListener {

            val intent = Intent(context,QuizActivity::class.java)
            QuizActivity.questionModelList = data.questionList
            QuizActivity.time = data.time
            context.startActivity(intent)

        }



    }


}