package com.example.koshelek_ru_test.data.vo

data class JsonResponse(
    val asks: List<List<String>>,
    val bids: List<List<String>>,
    val lastUpdateId: Long
)