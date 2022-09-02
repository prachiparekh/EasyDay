package com.civil.easyday.utils

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import java.io.File
import java.util.*

class IntentUtil {

    companion object {
        var PICK_IMAGE_CHOOSER_REQUEST_CODE = 200
        var imgPath: File? = null
        fun getPickImageChooserIntent(
            context: Context,
            activity: Activity
        ): Intent? {
            val allIntents: MutableList<Intent> = ArrayList()
            val packageManager = context.packageManager

            // collect all camera intents if Camera permission is available
            if (permission(activity)) {
                val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri())
                allIntents.add(captureIntent)
            }
            var galleryIntents =
                getGalleryIntents(packageManager, Intent.ACTION_GET_CONTENT)
            if (galleryIntents.isEmpty()) {
                // if no intents found for get-content try pick intent action (Huawei P9).
                galleryIntents =
                    getGalleryIntents(packageManager, Intent.ACTION_PICK)
            }
            allIntents.addAll(galleryIntents)
            val target: Intent
            if (allIntents.isEmpty()) {
                target = Intent()
            } else {
                target = allIntents[allIntents.size - 1]
                allIntents.removeAt(allIntents.size - 1)
            }

            // Create a chooser from the main  intent
            val chooserIntent = Intent.createChooser(target, "Choose source")

            // Add all other intents
            chooserIntent.putExtra(
                Intent.EXTRA_INITIAL_INTENTS, allIntents.toTypedArray<Parcelable>()
            )
            return chooserIntent
        }

        private fun getGalleryIntents(
            packageManager: PackageManager, action: String
        ): List<Intent> {
            val intents: MutableList<Intent> = ArrayList()
            val galleryIntent =
                if (action === Intent.ACTION_GET_CONTENT) Intent(action) else Intent(
                    action,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
            galleryIntent.type = "image/*"
            val listGallery = packageManager.queryIntentActivities(galleryIntent, 0)
            for (res in listGallery) {
                val intent = Intent(galleryIntent)
                intent.component =
                    ComponentName(res.activityInfo.packageName, res.activityInfo.name)
                intent.setPackage(res.activityInfo.packageName)
                intents.add(intent)
            }

            return intents
        }

        private fun permission(activity: Activity): Boolean {

            return ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED

        }


        private fun setImageUri(): Uri {
            // Store image in dcim
            val file = File(
                Environment.getExternalStorageDirectory().toString() + "/DCIM/",
                "image" + Date().time.toString() + ".png"
            )
            val imgUri: Uri = Uri.fromFile(file)
            this.imgPath = file
            return imgUri
        }

        fun getImagePath(): File? {
            return imgPath
        }



    }
}