package dev.jolken.giphyer.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dev.jolken.giphyer.R
import dev.jolken.giphyer.adapters.GifRecyclerViewAdapter
import dev.jolken.giphyer.databinding.MainFragmentBinding
import dev.jolken.giphyer.models.viewmodels.MainViewModel
import dev.jolken.giphyer.utils.Status
import dev.jolken.giphyer.utils.hideKeyboard


class MainFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        binding.apply {
            toolbar.apply {
                inflateMenu(R.menu.search_menu)
                title = "Giphyer"
                menu.findItem(R.id.app_bar_search)?.apply {
                    val search = this.actionView as SearchView
                    search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            hideKeyboard()
                            viewModel.loadGifs(query)
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            if (newText.isNullOrEmpty()) {
                                viewModel.setIsTrending(true)
                            }
                            return true
                        }

                    })
                }

            }


            gifListRecyclerView.apply {
                layoutManager = FlexboxLayoutManager(context).apply {
                    flexDirection = FlexDirection.ROW
                    justifyContent = JustifyContent.SPACE_AROUND
                }

                adapter = GifRecyclerViewAdapter(viewModel.gifsList) { gif, position ->
                    viewModel.setSelectedGif(gif)
                    viewModel.setClickedGifPosition(position)
                }
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (!recyclerView.canScrollVertically(1) && viewModel.isLoadingGifs.value == false) {
                            viewModel.loadGifs()
                        }
                    }
                })
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
                    ?.addToBackStack(null)
                    ?.commit()

            })
            loadGifs()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}