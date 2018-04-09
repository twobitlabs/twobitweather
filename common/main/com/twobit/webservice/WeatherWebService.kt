package com.twobit.webservice

class WeatherWebService {
    fun getForecast(handler: (Any?) -> Unit) {
        WebService().get("https://api.weather.gov/gridpoints/MTR/87,126/forecast", { result ->
            handler(result)
        })
    }
}
