package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.DesignBinding

class Click(val stop:(Asteroid) -> Unit){
    fun onClick(asteroid: Asteroid) {
        stop(asteroid)
    }
}

class Adapter(val go:Click) : RecyclerView.Adapter<Holder>() {

    var aster :List<Asteroid> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       val binding: DesignBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),Holder.LAYOUT, parent, false)
        return Holder(binding)
    }


    override fun onBindViewHolder(holder: Holder, index: Int) {
        holder.bind.also {
           it.asteroid = aster[index]
            it.click = go
        }
    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int = aster.size
}

class Holder(val bind: DesignBinding) : RecyclerView.ViewHolder(bind.root){
    companion object {
        val LAYOUT = R.layout.design
    }
}
