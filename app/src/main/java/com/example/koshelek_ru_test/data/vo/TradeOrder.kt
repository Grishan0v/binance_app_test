package com.example.koshelek_ru_test.data.vo

data class TradeOrder (
    val price: Double,
    val quantity: Double
) {
    val total: Double = price * quantity
}