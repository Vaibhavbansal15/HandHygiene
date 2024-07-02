package com.jaypeehealthcare.handhygiene

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IcuTypeAdapter(private val icuList : ArrayList<IcuType>) : RecyclerView.Adapter<IcuTypeAdapter.IcuTypeViewHolder>() {

    private lateinit var mListener : onItemClickListener
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IcuTypeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_listitem, parent, false)
        return IcuTypeViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: IcuTypeViewHolder, position: Int) {
        val currItem = icuList[position]
        holder.icu.text = currItem.icuType
    }

    override fun getItemCount(): Int {
        return icuList.size
    }

    class IcuTypeViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val icu : TextView = itemView.findViewById(R.id.icu_type)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}