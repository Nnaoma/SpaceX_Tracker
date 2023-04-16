package com.adikob.spacextracker.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaunchInfo(
    @SerializedName("flight_number") val flightNumber: Int?,
    @SerializedName("mission_name") val missionName: String?,
    @SerializedName("launch_year") val launchYear: String?,
    @SerializedName("launch_success") val launchSuccess: Boolean?,
    @SerializedName("launch_date_utc") val launchDate: String?,
    @SerializedName("launch_date_source") val launchDateSource: String?,
    @SerializedName("last_date_update") val lastDateUpdate: String?,
    @SerializedName("static_fire_date_utc") val staticFireDate: String?,
    val details: String?,
    val upcoming: Boolean?,
    @SerializedName("mission_id") val missionId: List<String?>?,
    val ships: List<String?>?,
    val rocket: Rocket?,
    val links: Links?,
    @SerializedName("launch_site") val launchSite: LaunchSite?,
    ) : Parcelable{
        @Parcelize
        data class Rocket(
            @SerializedName("rocket_id") val id: String?,
            @SerializedName("rocket_name") val name: String?,
            @SerializedName("rocket_type") val type: String?
        ) : Parcelable

        @Parcelize
        data class LaunchSite(
            @SerializedName("site_name") val name: String?,
            @SerializedName("site_name_long") val nameLong: String?,
            @SerializedName("site_id") val id: String?
        ) : Parcelable

        @Parcelize
        data class Links(
            @SerializedName("mission_patch") val missionPatch: String?,
            @SerializedName("mission_patch_small") val missionPatchSmall: String?,
            @SerializedName("reddit_launch") val redditLaunch: String?,
            @SerializedName("article_link") val articleLink: String?,
            val wikipedia: String?,
            @SerializedName("video_link") val videoLink: String?
        ) : Parcelable

    override fun equals(other: Any?): Boolean {
        return if (other is LaunchInfo)
            other.missionName == this.missionName
        else
            false
    }

    override fun hashCode(): Int {
        var result = flightNumber ?: 0
        result = 31 * result + (missionName?.hashCode() ?: 0)
        result = 31 * result + (launchYear?.hashCode() ?: 0)
        result = 31 * result + (launchSuccess?.hashCode() ?: 0)
        result = 31 * result + (launchDate?.hashCode() ?: 0)
        result = 31 * result + (launchDateSource?.hashCode() ?: 0)
        result = 31 * result + (lastDateUpdate?.hashCode() ?: 0)
        result = 31 * result + (staticFireDate?.hashCode() ?: 0)
        result = 31 * result + (details?.hashCode() ?: 0)
        result = 31 * result + (upcoming?.hashCode() ?: 0)
        result = 31 * result + (missionId?.hashCode() ?: 0)
        result = 31 * result + (ships?.hashCode() ?: 0)
        result = 31 * result + (rocket?.hashCode() ?: 0)
        result = 31 * result + (links?.hashCode() ?: 0)
        result = 31 * result + (launchSite?.hashCode() ?: 0)
        return result
    }
}
