package com.shivram.runningappdemo.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shivram.runningappdemo.R
import com.shivram.runningappdemo.viewmodels.MainViewModel
import com.shivram.runningappdemo.viewmodels.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StatisticsFragment: Fragment(R.layout.fragment_statistics) {


    private val viewModel: StatisticsViewModel by viewModels()
}