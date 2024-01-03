package com.example.babyblues

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

 var selectedCardViews = mutableListOf<Int>()

class OptionsAdapter(private val questions : Questions , private val number : Int) : RecyclerView.Adapter<OptionsAdapter.ViewHolder>() {




    private var options1 : List<String> = listOf(questions.option1,questions.option2,questions.option3) as List<String>
    private var options2 : List<String> = listOf(questions.option1,questions.option2) as List<String>
    private lateinit var options: List<String>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.options, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int ) {

        val option = options[position]
        holder.optionText.text = option
        holder.itemView.setOnClickListener {
            questions.useranswer = option
            selectedCardViews.add(position)
            notifyDataSetChanged()
        }
        if (questions.useranswer == option){

            holder.itemView.setBackgroundColor(R.drawable.border)

        }
        else{
            holder.itemView.setBackgroundResource(R.drawable.blue)

        }
        Log.d("ttss", questions.useranswer.toString())
        Log.d("arar", selectedCardViews.toString())
    }

    override fun getItemCount(): Int {
        if (number.equals(2)){options = options2}
        else{
            options = options1
        }
        return options.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val optionText: TextView = itemView.findViewById(R.id.optiontext)
    }
}
