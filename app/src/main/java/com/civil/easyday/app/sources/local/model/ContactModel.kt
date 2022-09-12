package com.civil.easyday.app.sources.local.model

import android.graphics.Bitmap
import android.net.Uri


class ContactModel (
    var id: String? = null,
    var name: String? = null,
    var mobileNumber: String? = null,
    var photo: Bitmap? = null,
    var photoURI: String? = null
) {
    override fun toString(): String {
        return "ContactModel(name=$name, mobileNumber=$mobileNumber)"
    }
}