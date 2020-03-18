package com.master.myexchanges.ui.main

import androidx.lifecycle.ViewModel
import com.master.myexchanges.domain.User

class SharedViewModel(
) : ViewModel() {
    var user: User? = null
}