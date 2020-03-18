package com.master.myexchanges.ui.fragments.history


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar

import com.master.myexchanges.R
import com.master.myexchanges.ui.BaseActivity
import com.master.myexchanges.ui.adapters.SearchAdapter
import com.master.myexchanges.ui.main.SharedViewModel
import kotlinx.android.synthetic.main.fragment_exchange.*
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private val historyViewModel: HistoryViewModel by viewModel()
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpSharedViewModel()
        setUpObservers()
        historyViewModel.loadUserSearchs(sharedViewModel.user)
    }

    private fun setUpSharedViewModel() {
        activity?.let {
            sharedViewModel = ViewModelProviders.of(it).get(SharedViewModel::class.java)
        }
    }
    private fun setUpObservers() {
        activity?.let {
            if(it is BaseActivity){
                historyViewModel.observe(
                    this,
                    it::startWork,
                    it::finishWork,
                    it::showError
                )
            }
        }
        historyViewModel.searchList.observe(viewLifecycleOwner, Observer {
            val searchAdapter = SearchAdapter(it)
            rvHistoryList.apply {
                layoutManager = LinearLayoutManager(this.context!!)
                adapter = searchAdapter
            }
        })
        historyViewModel.minorError.observe(viewLifecycleOwner, Observer {
            Snackbar.make(btnExchange, it, Snackbar.LENGTH_SHORT).show()
        })
        historyViewModel.fatalError.observe(viewLifecycleOwner, Observer {
            AlertDialog.Builder(activity)
                .setTitle(R.string.warning)
                .setMessage(it)
                .setNeutralButton(R.string.ok) { _: DialogInterface, _: Int ->
                    activity?.finish()
                }.show()
        })
    }

}
