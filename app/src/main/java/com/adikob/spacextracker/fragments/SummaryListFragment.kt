package com.adikob.spacextracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.adikob.spacextracker.MainActivity
import com.adikob.spacextracker.R
import com.adikob.spacextracker.adapters.CallbackItemClicked
import com.adikob.spacextracker.adapters.SummaryRecyclerViewAdapter
import com.adikob.spacextracker.api.LoadingState
import com.adikob.spacextracker.databinding.FragmentSummaryListBinding
import com.adikob.spacextracker.models.LaunchInfo
import com.adikob.spacextracker.utility.LAUNCH_INFO_ARGUMENT_KEY
import com.adikob.spacextracker.utility.filterNull
import com.adikob.spacextracker.viewmodels.SummaryFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SummaryListFragment : Fragment(), CallbackItemClicked {

//    companion object {
//        @JvmStatic
//        fun newInstance() = SummaryListFragment()
//    }

    private var summaryListFragmentBinding: FragmentSummaryListBinding? = null
    private lateinit var recyclerViewAdapter: SummaryRecyclerViewAdapter

    private val viewModel: SummaryFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_summary_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        summaryListFragmentBinding = FragmentSummaryListBinding.bind(view)

        recyclerViewAdapter = SummaryRecyclerViewAdapter(this)
        summaryListFragmentBinding?.apply {
            launchSummaryList.apply {
                adapter = recyclerViewAdapter
                setHasFixedSize(true)
            }
            swipeToRefresh.apply {
                isRefreshing = true
                setOnRefreshListener {
                    isRefreshing = true
                    viewModel.refreshData()
                }
            }
        }

        viewModel.getData().observe(viewLifecycleOwner) { launchInfoWrapper ->
            summaryListFragmentBinding?.swipeToRefresh?.isRefreshing = false
            if (launchInfoWrapper.state == LoadingState.DONE){
                recyclerViewAdapter.clear()
                recyclerViewAdapter.notifyDataSetChanged()
                for (i in launchInfoWrapper.launchInfoList) {
                    recyclerViewAdapter.add(i)
                    recyclerViewAdapter.notifyItemInserted(launchInfoWrapper.launchInfoList.indexOf(i))
                }
            }else if (launchInfoWrapper.state == LoadingState.ERROR){
                Snackbar.make(view, filterNull(launchInfoWrapper.exception?.message), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        summaryListFragmentBinding = null
    }

    override fun onViewHolderClicked(launchInfo: LaunchInfo) {
        if(resources.getBoolean(R.bool.isTablet)){
            val activity = requireActivity()
            if (activity is MainActivity)
                activity.onViewHolderClicked(launchInfo)
        }else{
            findNavController().navigate(R.id.action_SummaryFragment_to_DetailFragment, Bundle().apply {
                putParcelable(LAUNCH_INFO_ARGUMENT_KEY, launchInfo)
            })
        }
    }
}