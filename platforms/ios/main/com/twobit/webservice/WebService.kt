package com.twobit.webservice

import kotlinx.cinterop.*
import platform.darwin.NSObject
import platform.Foundation.*

actual class WebService actual constructor() {
    actual fun get(url: String, handler: (Any?) -> Unit) {
        val delegate = object : NSObject(), NSURLSessionDataDelegateProtocol {
            private val receivedData = NSMutableData()
            private var requestInProgress = false

            override fun URLSession(session: NSURLSession, dataTask: NSURLSessionDataTask, didReceiveData: NSData) {
                receivedData.appendData(didReceiveData)
            }

            override fun URLSession(session: NSURLSession, task: NSURLSessionTask, didCompleteWithError: NSError?) {
                requestInProgress = false // Only main thread accesses the fetcher, so it's safe to clear the flag here.
                val response = task.response
                if (response == null || response.reinterpret<NSHTTPURLResponse>().statusCode.toInt() != 200) {
                    handler(null)
                    return
                }
                if (didCompleteWithError != null) {
                    handler(null)
                    return
                }
                val jsonObject = memScoped {
                    val errorVar = alloc<ObjCObjectVar<NSError?>>()
                    var result = NSJSONSerialization.JSONObjectWithData(receivedData, 0, errorVar.ptr)
                    val error = errorVar.value
                    if (error != null) {
                        result = null
                    }
                    result
                }
                handler(jsonObject)
            }
        }
        val session = NSURLSession.sessionWithConfiguration(NSURLSessionConfiguration.defaultSessionConfiguration, delegate = delegate, delegateQueue = NSOperationQueue.mainQueue)
        session.dataTaskWithURL(NSURL(URLString = url)).resume()
    }
}
