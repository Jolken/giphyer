package dev.jolken.giphyer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import dev.jolken.giphyer.R
import dev.jolken.giphyer.models.giphy.Gif
import dev.jolken.giphyer.utils.Event

class GifRecyclerViewAdapter(private val gifs: MutableLiveData<Event<MutableList<Gif>>>) :
    RecyclerView.Adapter<GifRecyclerViewAdapter.GifViewHolder>() {

    class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.gifImageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gif_item, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gif = gifs.value?.data?.get(position) ?: return

        holder.imageView.layoutParams.width = gif.images.previewGif.width.toInt()
        holder.imageView.layoutParams.height = gif.images.previewGif.height.toInt()

    }

    override fun getItemCount(): Int {
        return gifs.value?.data?.size ?: 0
    }
}