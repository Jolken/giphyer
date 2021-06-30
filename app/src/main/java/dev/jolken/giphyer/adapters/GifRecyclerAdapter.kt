package dev.jolken.giphyer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.jolken.giphyer.R
import dev.jolken.giphyer.models.giphy.Gif

class GifRecyclerAdapter(private val gifs: List<Gif>) :
    RecyclerView.Adapter<GifRecyclerAdapter.GifViewHolder>() {

    class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
//        return GifViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
//        )
        TODO()
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return gifs.size
    }
}