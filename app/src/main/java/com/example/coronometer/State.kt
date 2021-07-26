package com.example.coronometer

class State(
    var name: String,
    var confirmed: Int,
    var recovered: Int,
    var death: Int,
    var latestConf: Int,
    var latestRec: Int,
    var latestDeath: Int
) {
}