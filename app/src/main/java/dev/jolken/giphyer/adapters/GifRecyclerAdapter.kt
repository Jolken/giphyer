package dev.jolken.giphyer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.jolken.giphyer.R
import dev.jolken.giphyer.models.giphy.Gif
import dev.jolken.giphyer.utils.Event
import dev.jolken.giphyer.utils.pxToDp

class GifRecyclerViewAdapter(
    private val gifs: MutableLiveData<Event<MutableList<Gif>>>,
    val onItemClickListener: (gif: Gif, position: Int) -> Unit
) : RecyclerView.Adapter<GifRecyclerViewAdapter.GifViewHolder>() {


    class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.gifImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gif_item, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gif = gifs.value?.data?.get(position) ?: return


        holder.imageView.layoutParams.width = holder.itemView.context.pxToDp(gif.images.previewGif.width.toIntOrNull() ?: 100)
        holder.imageView.layoutParams.height = holder.itemView.context.pxToDp(gif.images.previewGif.height.toIntOrNull() ?: 200)
        holder.imageView.setOnClickListener {
            onItemClickListener(gifs.value?.data?.get(position) ?: return@setOnClickListener, position)
        }
        Glide.with(holder.itemView).asGif().load(gif.images.previewGif.url).into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return gifs.value?.data?.size ?: 0
    }
}