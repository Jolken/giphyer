package dev.jolken.giphyer.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.EdgeEffectCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dev.jolken.giphyer.R
import dev.jolken.giphyer.adapters.GifRecyclerViewAdapter
import dev.jolken.giphyer.databinding.FragmentGifBinding
import dev.jolken.giphyer.databinding.MainFragmentBinding
import dev.jolken.giphyer.models.viewmodels.MainViewModel
import dev.jolken.giphyer.utils.Status
import dev.jolken.giphyer.utils.pxToDp

class GifFragment : Fragment() {
    private var _binding: FragmentGifBinding? = null
    private val binding get() = _binding!!
    val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGifBinding.inflate(inflater, container, false)

        binding.apply {
            toolbar.apply {
                inflateMenu(R.menu.gif_menu)
//                menu.findItem(R.id.app_bar_share)?.apply { //Не работает, помогла бы помощь =)
                menu.getItem(0)?.setOnMenuItemClickListener {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_SUBJECT, "Sharing GIFs URL!")
                        putExtra(Intent.EXTRA_TEXT, viewModel.selectedGif.value?.bitlyUrl)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, "Share URL")
                    startActivity(shareIntent)
                    return@setOnMenuItemClickListener true

                }
                setNavigationIcon(R.drawable.ic_white_arrow_back_24)
                setNavigationOnClickListener {
                    activity?.supportFragmentManager?.popBackStack()
                }

            }

            gifTitle.text = viewModel.selectedGif.value?.title

            gifListRecyclerView.apply {
                layoutManager = FlexboxLayoutManager(context).apply {
                    flexDirection = FlexDirection.ROW
                    justifyContent = JustifyContent.SPACE_AROUND
                    setPadding(0, 0, 0, 10)
                }

                adapter = GifRecyclerViewAdapter(viewModel.gifsList) { gif, position ->
                    viewModel.selectedGif.postValue(gif)
                    viewModel.clickedGifPosition.value = position
                }

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (!recyclerView.canScrollVertically(-1) && viewModel.isLoadingGifs.value == false) {
                            viewModel.loadGifs()
                        }
                    }
                })

                scrollToPosition(viewModel.clickedGifPosition.value ?: 0)


            }
            gifImage.layoutParams.apply {
                viewModel.selectedGif.value?.images?.downsized.let {
                    width = context?.pxToDp(it?.width?.toInt() ?: 1) ?: 1
                    height = context?.pxToDp(it?.height?.toInt() ?: 1) ?: 1

                }
            }

        }


        viewModel.apply {
            gifsList.observe(viewLifecycleOwner, {
                if (it.status == Status.ERROR) {
                    Toast.makeText(context, it.error.toString(), Toast.LENGTH_LONG).show()
                } else {
                    if (it.data?.size == 0) {
                        binding.gifListRecyclerView.adapter?.notifyDataSetChanged()
                    } else {
                        val oldDataCount = binding.gifListRecyclerView.adapter?.itemCount ?: 0
                        val changedCount = it.data?.size ?: 0 - oldDataCount
                        binding.gifListRecyclerView.adapter?.notifyItemRangeChanged(
                            oldDataCount - 1,
                            changedCount
                        )
                    }
                }
            })

            selectedGif.observe(viewLifecycleOwner, {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out)
                    ?.replace(R.id.container, GifFragment())
                    ?.commit()
            })
        }

        Glide.with(binding.root).asGif()
            .load(viewModel.selectedGif.value?.images?.downsized?.url)

            .into(binding.gifImage)

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}