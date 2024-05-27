package com.octagontechnologies.harrypotter.repo.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PotterResponse : ArrayList<PotterResponseItem>()