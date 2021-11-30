package com.test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.R
import com.test.model.Model
import com.test.utility.Utility
import com.test.viewmodel.LSViewModel
import com.test.viewmodel.MainDataViewModel


private const val ARG_PARAM1 = "param1"

class WeatherReportFragment : Fragment() {
    private var locId: Int? = null
    private lateinit var vm: LSViewModel
    private lateinit var mainDataViewModel: MainDataViewModel
    private lateinit var tvTitle: TextView
    private lateinit var tvReport: TextView
    private lateinit var tvRemainingTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = getString(R.string.weather_report_fragment) // for set screen title

        vm = ViewModelProvider(this).get(LSViewModel::class.java)
        mainDataViewModel = ViewModelProvider(this).get(MainDataViewModel::class.java)

        arguments?.let {
            locId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onResume() {
        super.onResume()
        checkCloseTime()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true) // for hiding action menu
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvTitle = view.findViewById(R.id.tv_title)
        tvReport = view.findViewById(R.id.tv_report)
        tvRemainingTime = view.findViewById(R.id.tv_remaining_time_to_close)

        val t = locId
        if (t != null) {
            mainDataViewModel.getWeatherReport(t)
                ?.observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        updateUI(it)
                    }
                })
        }

        view.findViewById<ImageButton>(R.id.btn_close).setOnClickListener {
            updateActiveRecord()
        }
    }

    private fun checkCloseTime() {
        val activeLoc = vm.getActiveLocation()
        if (activeLoc.isNotEmpty()) {
            val currentData = Utility.dateToTimeStamp(Utility.getTimeStamp())
            val endDate = Utility.add5Days(activeLoc[0].activeDate.toString(), Utility.DAYS)
            if (currentData > endDate) {
                updateActiveRecord()
            }

//            if (tvRemainingTime != null) {
//                val diff = endDate - currentData
//                tvRemainingTime.text = "${TimeUnit.MILLISECONDS.toHours(diff)/24} Days Remaining."
//            }
        }
    }

    private fun updateActiveRecord() {
        vm.update(locId!!, "", 0)
        (activity as MainActivity).appHome()
    }

    private fun updateUI(wr: Model.WeatherReport) {
        tvTitle.text = wr.title
        tvReport.text =
            "${wr.location_type}\n${wr.timezone}\nTemperature: ${wr.consolidated_weather[0].the_temp}"
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.title = getString(R.string.search_fragment)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.search_history)
        if (item != null) item.isVisible = false
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            WeatherReportFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}