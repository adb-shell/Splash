package com.karthik.splash.aboutscreen


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.karthik.splash.Models.Library.Library
import com.karthik.splash.R

import java.util.ArrayList

/**
 * Created by karthikrk on 03/12/17.
 */

class LibraryAdapter(private val libraries: ArrayList<Library>) : RecyclerView.Adapter<LibraryAdapter.LibraryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.library_row,
                parent, false)
        return LibraryHolder(row)
    }

    override fun onBindViewHolder(holder:LibraryHolder,position: Int) {
        val context = holder.libtitle.context
        val library = libraries[position]
        holder.libtitle.text = library.libName
        holder.libversion.text = "${context.getString(R.string.Version)} ${library.version}"
    }

    override fun getItemCount() = libraries.size

    inner class LibraryHolder:RecyclerView.ViewHolder {
        val libtitle:TextView
        val libversion:TextView
        constructor(itemView: View):super(itemView){
            libtitle = itemView.findViewById(R.id.libtitle)
            libversion = itemView.findViewById(R.id.libversion)
        }
    }
}
