package com.example.weatherappassessment.dataExceptionHandling

import kotlin.jvm.internal.Intrinsics.Kotlin

class DataException<T, Boolean, E: Exception>(
    var data : T? = null,
    var loading : kotlin.Boolean? = null,
    var e :E? = null
) {
}