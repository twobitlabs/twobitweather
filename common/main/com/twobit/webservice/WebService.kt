package com.twobit.webservice

expect class WebService() {
    fun get(url: String, handler: (Any?) -> Unit)
}
