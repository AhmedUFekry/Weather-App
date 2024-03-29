package com.example.weatherapp.DataLayer.Model.DataModels

data class AlertsItem(
	val start: Int? = null,
	val description: String? = null,
	val senderName: String? = null,
	val end: Int? = null,
	val event: String? = null,
	val tags: List<String?>? = null
)
