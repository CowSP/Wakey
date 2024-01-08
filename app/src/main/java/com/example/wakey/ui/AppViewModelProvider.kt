package com.example.wakey.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wakey.WakeyApplication
import com.example.wakey.ui.alarm.AlarmListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            AlarmListViewModel(this.wakeyApplication().container.wakeyRepository)
        }
    }
}

fun CreationExtras.wakeyApplication(): WakeyApplication =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WakeyApplication