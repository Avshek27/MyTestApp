package com.test.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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


class SearchHistoryFragment : Fragment(), SearchItemClickInterface {

    private lateinit var vm: LSViewModel
    private lateinit var adapter: SearchAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainDataViewModel: MainDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainDataViewModel = ViewModelProvider(this).get(MainDataViewModel::class.java)
        vm = ViewModelProvider(this).get(LSViewModel::class.java)
        arguments?.let {
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.title = getString(R.string.search_history_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_history, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var empty = view.findViewById<TextView>(R.id.tv_empty)
        recyclerView = view.findViewById(R.id.recycler_view)
        setUpRecyclerView()
        vm.getAllLocation().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                adapter.submitList(it)
            } else {
                empty.text = "No records found."
                adapter.submitList(it)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.title = getString(R.string.search_fragment)
    }

    private fun setUpRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = SearchAdapter(this)
        recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchHistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.search_history)
        if (item != null) item.isVisible = false
    }

    override fun onSearchItemClick(item: LocationSearch) {
        vm.update(item.woeid, Utility.getTimeStamp(), 1)
        (activity as MainActivity).removeFragmentFromStack(getString(R.string.search_history_fragment))
        (activity as MainActivity).addFragmentToActivity(WeatherReportFragment.newInstance(item.woeid), "")

    }

    override fun onDeleteButtonClick(item: LocationSearch) {
        vm.delete(item)
    }
}