package com.master.myexchanges.ui.fragments.exchange

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar

import com.master.myexchanges.R
import com.master.myexchanges.domain.CountryCurrency
import com.master.myexchanges.ui.BaseActivity
import com.master.myexchanges.ui.main.SharedViewModel
import kotlinx.android.synthetic.main.fragment_exchange.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class ExchangeFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private val exchangeViewModel: ExchangeViewModel by viewModel()
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exchange, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpSharedViewModel()
        setUpObservers()
        setUpListeners()
        exchangeViewModel.getCountryCurrencyList()
    }

    private fun setUpSharedViewModel() {
        activity?.let {
            sharedViewModel = ViewModelProviders.of(it).get(SharedViewModel::class.java)
        }
    }


    private fun setUpListeners() {

        valueToExchange.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                exchangeViewModel.setSourceValue(valueToExchange.text.toString())
        }
        spSourceCurrency.onItemSelectedListener = this
        spDestinationCurrency.onItemSelectedListener = this

        btnExchange.setOnClickListener {
            exchangeViewModel.doExchange(sharedViewModel.user)
        }
    }

    private fun setUpObservers() {
        activity?.let {
            if(it is BaseActivity){
                exchangeViewModel.observe(
                    this,
                    it::startWork,
                    it::finishWork,
                    it::showError
                )
            }
        }
        exchangeViewModel.sourceValue.observe(viewLifecycleOwner, Observer {
            it?.let {
                valueToExchange.setText(String.format(Locale.getDefault(), "%.2f", it)
                    .replace(',','.'))
            } ?: valueToExchange.setText("")
        })
        exchangeViewModel.sourceCurrencyPosition.observe(viewLifecycleOwner, Observer {
            spSourceCurrency.setSelection(it)
        })
        exchangeViewModel.destinationValue.observe(viewLifecycleOwner, Observer {
            it?.let {
                valueExchanged.setText(
                    String.format(Locale.getDefault(), "%.2f", it)
                        .replace(',','.')
                )
            } ?: valueExchanged.setText("")
        })
        exchangeViewModel.destinationCurrencyPosition.observe(viewLifecycleOwner, Observer {
            spDestinationCurrency.setSelection(it)
        })
        exchangeViewModel.exchangeButtonEnabled.observe(viewLifecycleOwner, Observer {
            btnExchange.isEnabled = it
        })
        exchangeViewModel.currencyList.observe(viewLifecycleOwner, Observer {
            activity?.let { context ->
                val sourceAdapter =
                    ArrayAdapter<CountryCurrency>(context, android.R.layout.simple_spinner_item, it)
                sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spSourceCurrency.adapter = sourceAdapter

                val destinationAdapter =
                    ArrayAdapter<CountryCurrency>(context, android.R.layout.simple_spinner_item, it)
                destinationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spDestinationCurrency.adapter = destinationAdapter
            }
        })
        exchangeViewModel.exchangeError.observe(viewLifecycleOwner, Observer {
            Snackbar.make(btnExchange, it, Snackbar.LENGTH_SHORT).show()
        })
        exchangeViewModel.fatalError.observe(viewLifecycleOwner, Observer {
            AlertDialog.Builder(activity)
                .setTitle(R.string.warning)
                .setMessage(it)
                .setNeutralButton(R.string.ok) { _: DialogInterface, _: Int ->
                    activity?.finish()
                }.show()
        })
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parent?.let {
            when (it.id) {
                R.id.spSourceCurrency -> exchangeViewModel.setSourceCurrency(position)
                R.id.spDestinationCurrency -> exchangeViewModel.setDestinationCurrency(position)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        parent?.let {
            when (it.id) {
                R.id.spSourceCurrency -> exchangeViewModel.setSourceCurrency(-1)
                R.id.spDestinationCurrency -> exchangeViewModel.setDestinationCurrency(-1)
            }
        }
    }
}
