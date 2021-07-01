package dev.jolken.giphyer.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dev.jolken.giphyer.R
import dev.jolken.giphyer.adapters.GifRecyclerViewAdapter
import dev.jolken.giphyer.databinding.MainFragmentBinding
import dev.jolken.giphyer.models.viewmodels.MainViewModel
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
        binding.toolbar.inflateMenu(R.menu.search_menu)
        binding.toolbar.title = "Giphyer"
        binding.toolbar.menu.findItem(R.id.app_bar_search)?.apply {
            val search = this.actionView as SearchView
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()
                    viewModel.loadFakeGifs()

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true//TODO
                }

            })

        }


        val view = binding.root

        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.SPACE_AROUND
        binding.gifListRecyclerView.layoutManager = layoutManager

        binding.gifListRecyclerView.adapter = GifRecyclerViewAdapter(viewModel.gifsList)

        viewModel.gifsList.observe(viewLifecycleOwner, {
            val oldDataCount = binding.gifListRecyclerView.adapter?.itemCount ?: 0
            val changedCount = it.data?.size ?: 0 - oldDataCount
            binding.gifListRecyclerView.adapter?.notifyItemRangeChanged(oldDataCount-1, changedCount)
        })

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}