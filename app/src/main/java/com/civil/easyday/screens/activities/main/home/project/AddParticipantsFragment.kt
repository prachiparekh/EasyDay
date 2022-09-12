package com.civil.easyday.screens.activities.main.home.project

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.civil.easyday.R
import com.civil.easyday.app.sources.local.model.ContactModel
import com.civil.easyday.app.sources.remote.model.AddProjectRequestModel
import com.civil.easyday.databinding.FragmentAddParticipantsBinding
import com.civil.easyday.screens.activities.main.home.project.adapter.ParticipentAdapter
import com.civil.easyday.utils.IntentUtil.Companion.contactPermission
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class AddParticipantsFragment : Fragment() {

    var contactList = ArrayList<ContactModel>()
    var binding: FragmentAddParticipantsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_participants, container, false)
        val model = arguments?.getSerializable("createProjectModel") as AddProjectRequestModel
        Log.e("model", model.toString())

        if (contactPermission(requireActivity())) {
            getContactList()
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
                        getContactList()
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


    @SuppressLint("Range")
    private fun getContactList() {
        val cr: ContentResolver = requireContext().contentResolver
        val cur = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC "
        )
        var lastnumber: String? = "0"


            while (cur?.moveToNext() == true) {
                var number: String? = null
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
                    while (pCur!!.moveToNext()) {
                        number = pCur.getString(pCur.getColumnIndex(Phone.NUMBER))
                        if (lastnumber != null) {
                            Log.e("lastnumber ", lastnumber)
                        }
                        Log.e("number", number)
                        if (number == lastnumber) {
                        } else {
                            lastnumber = number
                            Log.e("lastnumber ", lastnumber)
                            val type = pCur.getInt(pCur.getColumnIndex(Phone.TYPE))
                            when (type) {
                                Phone.TYPE_HOME -> Log.e("Not Inserted", "Not inserted")
                                Phone.TYPE_MOBILE -> contactList.add(ContactModel(
                                    id,
                                    name,
                                    lastnumber,
                                    null,null
                                ))
                                Phone.TYPE_WORK -> Log.e("Not Inserted", "Not inserted")
                            }
                        }
                    }
                    pCur.close()
                }
            }

        binding?.participentRV?.adapter = ParticipentAdapter(
            requireContext(),
            contactList
        )

    }

}