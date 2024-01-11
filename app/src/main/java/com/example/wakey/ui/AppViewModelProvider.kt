package com.example.wakey.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wakey.WakeyApplication
import com.example.wakey.ui.alarm.AlarmListViewModel
import com.example.wakey.ui.home.HomeViewModel
import com.example.wakey.ui.pattern.PatternListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel()
        }

        initializer {
            AlarmListViewModel(this.wakeyApplication().container.wakeyRepository)
        }

        initializer {
            PatternListViewModel(this.wakeyApplication().container.wakeyRepository)
        }
    }
}

fun CreationExtras.wakeyApplication(): WakeyApplication =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WakeyApplication