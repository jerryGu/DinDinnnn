package com.example.dindinn.network

import dindinn.BuildConfig
import okhttp3.*

/**
 * Mock data, for test only
 */
class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url().uri().toString()
            val responseString = when {
                uri.endsWith("orders") -> ordersJson
                uri.contains("category") -> {
                    if (chain.request().url().queryParameter("keyword")?.isNotEmpty() == true) {
                        Ingredient_4
                    } else {
                        when (chain.request().url().queryParameter("id")) {
                            "1" -> Ingredient_1
                            "2" -> Ingredient_2
                            "3" -> Ingredient_3
                            else -> ""
                        }
                    }


                }
                else -> ""
            }


            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    ResponseBody.create(
                        MediaType.parse("application/json"),
                        responseString.toByteArray()
                    )
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }

}

const val ordersJson = """
[{
"id": 10,
"title": "Special extra large fried rice",
"addon": [{
"id": 21,
"title": "Fried Egg",
"quantity": 3
}],
"quantity": 1,
"created_at": "2021-06-10T15:00+00Z",
"alerted_at": "2021-06-10T15:03+00Z",
"expired_at": "2021-06-10T15:05+00Z"
}, {
"id": 11,
"title": "Chicken Noodle",
"addon": [{
"id": 26,
"title": "Extra chicken",
"quantity": 2
}, {
"id": 27,
"title": "Sambal",
"quantity": 1
}],
"quantity": 1,
"created_at": "2021-06-10T15:10+00Z",
"alerted_at": "2021-06-10T15:13+00Z",
"expired_at": "2021-06-10T15:15+00Z"
}]
"""

const val Ingredient_1 = """
[{
"id": 110,
"title": "Special extra large fried rice",
"quantity": 1
},
 {
"id": 126,
"title": "Watering fish",
"quantity": 2
},{
"id": 111,
"title": "Chicken Noodle",
"quantity": 1
}]
"""

const val Ingredient_2 = """
[{
"id": 210,
"title": "Fried Egg",
"quantity": 1
},
 {
"id": 226,
"title": "Extra chicken",
"quantity": 2
},{
"id": 211,
"title": "Lemonade",
"quantity": 1
}]
"""

const val Ingredient_3 = """
[{
"id": 310,
"title": "Nasi Lemak",
"quantity": 1
},
 {
"id": 326,
"title": "Pasta",
"quantity": 2
},{
"id": 311,
"title": "Chicken Noodle",
"quantity": 1
}]
"""

const val Ingredient_4 = """
[{
"id": 410,
"title": "Search item1",
"quantity": 1
},
 {
"id": 426,
"title": "Search item2",
"quantity": 2
},{
"id": 411,
"title": "Search item3",
"quantity": 1
}]
"""

