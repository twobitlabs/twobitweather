package com.twobit.webservice

import kotlinx.cinterop.*
import platform.darwin.NSObject
import platform.Foundation.*

actual class WebService actual constructor() {
    fun foo() {
        val session = NSURLSession.sessionWithConfiguration(NSURLSessionConfiguration.defaultSessionConfiguration, delegate = null, delegateQueue = NSOperationQueue.mainQueue)
    }
}
