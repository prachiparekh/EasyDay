package com.civil.easyday.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

class FileUtil {
    companion object{
        fun loadJSONFromAsset(context: Context): String? {
            var json: String? = null
            json = try {
                val `is`: InputStream = context.assets.open("Country_Code.json")
                val size = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                String(buffer, StandardCharsets.UTF_8)
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }
            return json
        }
    }
}