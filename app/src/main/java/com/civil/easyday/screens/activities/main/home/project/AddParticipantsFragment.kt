package com.civil.easyday.screens.activities.main.home.project

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.civil.easyday.R
import com.civil.easyday.app.sources.local.model.ContactModel
import com.civil.easyday.app.sources.remote.model.AddProjectRequestModel
import com.civil.easyday.databinding.FragmentAddParticipantsBinding
import com.civil.easyday.screens.activities.main.home.project.adapter.ParticipentAdapter
import com.civil.easyday.utils.DeviceUtils
import com.civil.easyday.utils.IntentUtil.Companion.contactPermission
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddParticipantsFragment : Fragment() {

    companion object {
        var contactList = ArrayList<ContactModel>()
        var binding: FragmentAddParticipantsBinding? = null
        var adapter: ParticipentAdapter? = null

        fun getPhotoUri(context: Context, id: String): Uri? {
            try {
                val cur: Cursor? = context.contentResolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + "=" + id + " AND "
                            + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,
                    null
                )
                if (cur != null) {
                    if (!cur.moveToFirst()) {
                        return null // no photo
                    }
                } else {
                    return null // error in cursor process
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
            val person =
                ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id.toLong())
            return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_participants, container, false)
        val model = arguments?.getSerializable("createProjectModel") as AddProjectRequestModel
        Log.e("model", model.toString())

        DeviceUtils.initProgress(requireContext())
        DeviceUtils.showProgress()
        if (contactPermission(requireActivity())) {
            AsyncTaskExample(requireContext()).execute()
        } else
            onPermission()



        return binding?.root
    }


    private fun onPermission() {

        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.READ_CONTACTS,
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if (p0?.areAllPermissionsGranted() == true) {
                        AsyncTaskExample(requireContext()).execute()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                }

            }).withErrorListener {}

            .check()
    }

    class AsyncTaskExample(val context: Context) : AsyncTask<Void, Void, Void>() {
        @SuppressLint("Range")
        override fun doInBackground(vararg p0: Void): Void? {
            val cr: ContentResolver = context.contentResolver
            val cur = cr.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC "
            )
            var lastnumber = ""

            val number = ArrayList<String>()
            while (cur?.moveToNext() == true) {

                val id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))
                val name =
                    cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                if (cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        .toInt() > 0
                ) {
                    val pCur = cr.query(
                        Phone.CONTENT_URI,
                        null,
                        Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    while (pCur?.moveToNext() == true) {
                        lastnumber = pCur.getString(pCur.getColumnIndex(Phone.NUMBER))
                        if (!number.contains(lastnumber)) {
                            number.add(lastnumber)

                            when (pCur.getInt(pCur.getColumnIndex(Phone.TYPE))) {
//                                Phone.TYPE_HOME -> Log.e("Not Inserted", "Not inserted")
                                Phone.TYPE_MOBILE -> {

                                    val mBitmapURI = getPhotoUri(context, id)
                                    contactList.add(
                                        ContactModel(
                                            id,
                                            name, "participant",
                                            lastnumber,
                                            mBitmapURI.toString()
                                        )
                                    )

                                }
//                                Phone.TYPE_WORK -> Log.e("Not Inserted", "Not inserted")
                            }
                        }
                    }
                    pCur?.close()
                }
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            adapter = ParticipentAdapter(
                context,
                contactList
            )
            binding?.participentRV?.adapter = adapter
            DeviceUtils.dismissProgress()

            binding?.mSearch?.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isEmpty())
                        binding?.searchImageView?.visibility = View.VISIBLE
                    else
                        binding?.searchImageView?.visibility = View.INVISIBLE
                    adapter?.filter?.filter(newText)
                    return true
                }
            })

            binding?.cta?.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelableArrayList("selectedParticipantList", adapter?.getList())
                val nav = binding?.root?.let { it1 ->
                    Navigation.findNavController(
                        it1
                    )
                }
                if (nav?.currentDestination != null && nav.currentDestination?.id == R.id.addParticipantsFragment) {
                    nav.navigate(R.id.add_participant_to_add_admin,bundle)
                }
            }
        }
    }


}