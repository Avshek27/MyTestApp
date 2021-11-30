package com.test.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.R
import com.test.adapter.SearchAdapter
import com.test.adapter.SearchItemClickInterface
import com.test.storage.LocationSearch
import com.test.utility.Utility
import com.test.viewmodel.LSViewModel
import com.test.viewmodel.MainDataViewModel
import androidx.core.content.ContextCompat.getSystemService

import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat


private const val ARG_PARAM1 = "param1"

class SearchFragment : Fragment(), SearchItemClickInterface {
    private lateinit var vm: LSViewModel
    private lateinit var adapter: SearchAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainDataViewModel: MainDataViewModel

    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = getString(R.string.search_fragment)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        mainDataViewModel = ViewModelProvider(this).get(MainDataViewModel::class.java)
        vm = ViewModelProvider(this).get(LSViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var searchLayout = view.findViewById<LinearLayout>(R.id.searchLayout)
        val searchBox = view.findViewById<EditText>(R.id.et_search)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        view.findViewById<Button>(R.id.btn_search).setOnClickListener {
            (activity as MainActivity).hideKeyboard(view)
            if (searchBox.text.isBlank())
                Toast.makeText(activity, "Please enter text.", Toast.LENGTH_SHORT).show()
            else
                mainDataViewModel.getLocByName(searchBox.text.toString().trim())
                    ?.observe(viewLifecycleOwner, {
                        if (it != null && it.isNotEmpty()) {
                            adapter.submitList(it)
                        }
                    })
        }
        setUpRecyclerView()
        if (!param1.isNullOrBlank())
            mainDataViewModel.getLocByLatLong(param1.toString())?.observe(viewLifecycleOwner, {
                if (it != null && it.isNotEmpty()) {
                    adapter.submitList(it)
                }
            })

    }

    private fun setUpRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = SearchAdapter(this)
        recyclerView.adapter = adapter
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.search_history)
        if (item != null) item.isVisible = true
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onSearchItemClick(item: LocationSearch) {
        if (!vm.isLocationExist(item.woeid)) {
            vm.insert(
                LocationSearch(
                    item.title,
                    item.location_type,
                    item.woeid,
                    item.latt_long,
                    item.distance,
                    Utility.getTimeStamp(),
                    ""
                )
            )
        }
    }

    override fun onDeleteButtonClick(item: LocationSearch) {
        TODO("Not yet implemented")
    }
}