package com.example.shopnest.api

import okhttp3.MediaType
import okhttp3.ResponseBody

object ErrorParser {
    private const val MAX_CHARS = 8_192

    fun parseMessage(body: ResponseBody?): String? {
        if (body == null) return null
        return runCatching {
            val ct: MediaType? = body.contentType()
            if (ct != null && ct.type != "text" && ct.subtype != "json") return null
            body.source().buffer.clone().readUtf8().take(MAX_CHARS)
        }.getOrNull()
    }

    fun rawTruncated(body: ResponseBody?): String? {
        if (body == null) return null
        return runCatching {
            body.string().take(MAX_CHARS)
        }.getOrNull()
    }
}