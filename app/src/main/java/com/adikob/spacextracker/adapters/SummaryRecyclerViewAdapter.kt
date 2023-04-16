package com.adikob.spacextracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adikob.spacextracker.R
import com.adikob.spacextracker.databinding.SummaryDetailsBinding
import com.adikob.spacextracker.models.LaunchInfo
import com.adikob.spacextracker.utility.filterNull
import com.adikob.spacextracker.utility.toDate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SummaryRecyclerViewAdapter(private val callbackItemClicked: CallbackItemClicked) : RecyclerView.Adapter<SummaryRecyclerViewAdapter.SummaryRecyclerViewHolder>(){

    private var dataList = ArrayList<LaunchInfo>(0)
    private var prevSelectedViewPosition: Int? = null;

    constructor(launchList: ArrayList<LaunchInfo>, callbackItemClicked: CallbackItemClicked) : this(callbackItemClicked) {
        dataList = launchList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryRecyclerViewHolder {
        val binding = SummaryDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SummaryRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SummaryRecyclerViewHolder, position: Int) {
        val info = dataList[position]
        holder.bind(info, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun add(launchInfo: LaunchInfo){
        dataList.add(launchInfo)
    }

    fun add(launchInfo: LaunchInfo, position: Int){
        dataList.add(position, launchInfo)
    }

    fun addAll(infoList: ArrayList<LaunchInfo>){
        dataList.addAll(infoList)
    }

    fun addAll(infoList: ArrayList<LaunchInfo>, position: Int){
        dataList.addAll(position, infoList)
    }

    fun clear(){
        dataList.clear()
    }

    inner class SummaryRecyclerViewHolder(private val binding: SummaryDetailsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(info: LaunchInfo, position: Int){
            binding.apply {
                selectableBackground.isSelected = position == prevSelectedViewPosition
                Glide.with(launchImage)
                    .load(info.links?.missionPatchSmall)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.loading)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .error(R.drawable.launchpad)
                    .into(launchImage)
                missionName.text = filterNull(info.missionName)
                rocketName.text = filterNull(info.rocket?.name)
                launchSite.text = filterNull(info.launchSite?.name)
                date.text = filterNull(info.launchDate?.toDate())
                root.setOnClickListener {
                    selectableBackground.isSelected = true
                    callbackItemClicked.onViewHolderClicked(info, prevSelectedViewPosition)
                    prevSelectedViewPosition = position
                }
            }
        }
    }
}

interface CallbackItemClicked {
    fun onViewHolderClicked(launchInfo: LaunchInfo, position: Int?)
}