package com.english.vocab.domain.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Keep
@Parcelize
data class CountryModel(
	@Keep
	@field:SerializedName("zip")
	val zip: String? = null,
	@Keep
	@field:SerializedName("country")
	val country: String? = null,
	@Keep
	@field:SerializedName("city")
	val city: String? = null,
	@Keep
	@field:SerializedName("org")
	val org: String? = null,
	@Keep
	@field:SerializedName("timezone")
	val timezone: String? = null,
	@Keep
	@field:SerializedName("regionName")
	val regionName: String? = null,
	@Keep
	@field:SerializedName("isp")
	val isp: String? = null,
	@Keep
	@field:SerializedName("query")
	val query: String? = null,
	@Keep
	@field:SerializedName("lon")
	val lon: Double? = null,
	@Keep
	@field:SerializedName("aS")
	val aS: String? = null,
	@Keep
	@field:SerializedName("countryCode")
	val countryCode: String? = null,
	@Keep
	@field:SerializedName("region")
	val region: String? = null,
	@Keep
	@field:SerializedName("lat")
	val lat: Double? = null,
	@Keep
	@field:SerializedName("status")
	val status: String? = null
): Parcelable
