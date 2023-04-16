package com.adikob.spacextracker.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adikob.spacextracker.R
import com.adikob.spacextracker.databinding.FragmentDetailsBinding
import com.adikob.spacextracker.models.LaunchInfo
import com.adikob.spacextracker.utility.LAUNCH_INFO_ARGUMENT_KEY
import com.adikob.spacextracker.utility.filterNull
import com.adikob.spacextracker.utility.toDate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    companion object{
        fun newInstance(launchInfo: LaunchInfo): DetailsFragment{
            return DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LAUNCH_INFO_ARGUMENT_KEY, launchInfo)
                }
            }
        }
    }

    private var detailsBinding: FragmentDetailsBinding? = null
    private var launchInfo: LaunchInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchInfo = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            arguments?.getParcelable(LAUNCH_INFO_ARGUMENT_KEY, LaunchInfo::class.java)
        else
            arguments?.getParcelable(LAUNCH_INFO_ARGUMENT_KEY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsBinding = FragmentDetailsBinding.bind(view)

        detailsBinding?.apply {
            Glide.with(launchImage)
                .load(launchInfo?.links?.missionPatchSmall)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.loading)
                .error(R.drawable.launchpad)
                .into(launchImage)

            missionName.text = filterNull(launchInfo?.missionName)
            launchYear.text = resources.getString(R.string.launch_year, filterNull(launchInfo?.launchYear))
            flightNumber.text = resources.getString(R.string.flight_number, filterNull(launchInfo?.flightNumber?.toString()))
            details.text = filterNull(launchInfo?.details)
            rocketName.text = resources.getString(R.string.rocket_name, filterNull(launchInfo?.rocket?.name))
            rocketType.text = resources.getString(R.string.rocket_type, filterNull(launchInfo?.rocket?.type))
            launchSite.text = resources.getString(R.string.launch_site, filterNull(launchInfo?.launchSite?.nameLong))
            launchDate.text = resources.getString(R.string.launch_date, filterNull(launchInfo?.launchDate?.toDate()))
            lastUpdate.text = resources.getString(R.string.last_update, filterNull(launchInfo?.lastDateUpdate?.toDate()))
            redditLink.text = resources.getString(R.string.reddit_link, filterNull(launchInfo?.links?.redditLaunch))
            articleLink.text = resources.getString(R.string.article_link, filterNull(launchInfo?.links?.articleLink))
            wikiLink.text = resources.getString(R.string.wiki_link, filterNull(launchInfo?.links?.wikipedia))
            videoLink.text = resources.getString(R.string.video_link, filterNull(launchInfo?.links?.videoLink))
        }
    }
}