package com.app.easyday.screens.activities.main.home.task

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.navigation.Navigation
import com.app.easyday.R
import com.app.easyday.app.sources.local.interfaces.FilterCloseInterface
import com.app.easyday.app.sources.local.interfaces.FilterTypeInterface
import com.app.easyday.app.sources.local.interfaces.TagInterface
import com.app.easyday.app.sources.local.model.Media
import com.app.easyday.screens.base.BaseFragment
import com.app.easyday.screens.dialogs.AddTagBottomSheetDialog
import com.app.easyday.screens.dialogs.DueDateBottomSheetDialog
import com.app.easyday.utils.FileUtil
import com.passiondroid.imageeditorlib.ImageEditActivity
import com.passiondroid.imageeditorlib.ImageEditor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_task.*
import java.io.File

@AndroidEntryPoint
class CreateTaskFragment : BaseFragment<CreateTaskViewModel>(), FilterTypeInterface, TagInterface,
    FilterCloseInterface {

    override fun getContentView() = R.layout.fragment_create_task
    private var filterTypeList = arrayListOf<String>()
    private var drawableList = arrayListOf<Drawable>()
    private var priorityList = arrayListOf<String>()
    var taskAdapter: TaskFilterAdapter? = null
    private var imgAdapter: BottomImageAdapter? = null
    var selectedUriList = ArrayList<Media>()
    var mediaAdapter: MediaAdapter? = null

//    *****************

    private var selectedTagList = arrayListOf<String>()
    private var selectedPriority: String? = null
    var redFlag = false
    var selectedDate: String? = null

//    *****************

    override fun getStatusBarColor()=R.color.black

    override fun initUi() {
//        requireActivity().window.statusBarColor = requireContext().resources.getColor(R.color.black)
        selectedUriList = arguments?.getParcelableArrayList<Media>("uriList") as ArrayList<Media>

        mediaAdapter = MediaAdapter(
            requireContext(),
            onItemClick = { isVideo, uri ->
                if (isVideo) {
                    val play =
                        Intent(Intent.ACTION_VIEW, uri).apply { setDataAndType(uri, "video/mp4") }
                    startActivity(play)
                }
            },
            onDeleteClick = { isEmpty, uri ->
                if (!isEmpty) {
                    val resolver = requireContext().applicationContext.contentResolver
                    resolver.delete(uri, null, null)
                }
            }
        )

        pagerPhotos.apply {
            adapter = mediaAdapter?.apply { submitList(selectedUriList) }
//            onPageSelected { page -> currentPage = page }
        }

        filterTypeList.add(requireContext().resources.getString(R.string.f_priority))
        filterTypeList.add(requireContext().resources.getString(R.string.f_tag))
        filterTypeList.add(requireContext().resources.getString(R.string.f_red_flag))
        filterTypeList.add(requireContext().resources.getString(R.string.f_space))
        filterTypeList.add(requireContext().resources.getString(R.string.f_zone))
        filterTypeList.add(requireContext().resources.getString(R.string.f_due_date))

        drawableList.add(requireContext().resources.getDrawable(R.drawable.ic_priority))
        drawableList.add(requireContext().resources.getDrawable(R.drawable.ic_tag))
        drawableList.add(requireContext().resources.getDrawable(R.drawable.ic_flag))
        drawableList.add(requireContext().resources.getDrawable(R.drawable.ic_buliding))
        drawableList.add(requireContext().resources.getDrawable(R.drawable.ic_zone))
        drawableList.add(requireContext().resources.getDrawable(R.drawable.ic_calender))

        priorityList.add(requireContext().resources.getString(R.string.none))
        priorityList.add(requireContext().resources.getString(R.string.low))
        priorityList.add(requireContext().resources.getString(R.string.normal))
        priorityList.add(requireContext().resources.getString(R.string.high))
        selectedPriority = priorityList[0]

        close.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }

        taskAdapter =
            TaskFilterAdapter(requireContext(), filterTypeList, priorityList, drawableList, this)
        filterRV.adapter = taskAdapter

//        val bottomImageList = ArrayList<Media>()
//        bottomImageList.add(0, Media(null, false, System.currentTimeMillis()))
//        bottomImageList.addAll(selectedUriList)

        imgAdapter =
            BottomImageAdapter(requireContext(), selectedUriList, onItemClick = { position, item ->
                    pagerPhotos.currentItem = position
            })
        imgRV.adapter = imgAdapter

        edit.setOnClickListener {

            val imagePath = selectedUriList[pagerPhotos.currentItem].uri?.let { it1 ->
                FileUtil.getPath(
                    it1,
                    requireContext()
                )
            }
            if (imagePath?.let { it1 -> File(it1).exists() } == true) {
                val intent = Intent(context, ImageEditActivity::class.java)
                intent.putExtra(ImageEditor.EXTRA_IS_PAINT_MODE, true)
                intent.putExtra(ImageEditor.EXTRA_IS_STICKER_MODE, false)
                intent.putExtra(ImageEditor.EXTRA_IS_TEXT_MODE, true)
                intent.putExtra(ImageEditor.EXTRA_IS_CROP_MODE, false)
                intent.putExtra(ImageEditor.EXTRA_HAS_FILTERS, false)
                intent.putExtra(ImageEditor.EXTRA_IMAGE_PATH, imagePath)
                startActivityForResult(intent, ImageEditor.RC_IMAGE_EDITOR)
            } else {
                Toast.makeText(context, "Invalid image path", Toast.LENGTH_SHORT).show()
            }

        }

        delete.setOnClickListener {
            Log.e("currentItem", pagerPhotos.currentItem.toString())
            selectedUriList.removeAt(pagerPhotos.currentItem)
            mediaAdapter?.notifyDataSetChanged()
            imgAdapter?.notifyDataSetChanged()
        }

        imgAdd.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }

    }

    override fun setObservers() {

    }

    override fun onFilterTypeClick(position: Int) {
        when (position) {
            1 -> {
                //Tag
                val tagList = arrayListOf<String>()
                tagList.add("Tag1")
                tagList.add("Tag2")
                tagList.add("Tag3")
                tagList.add("Tag4")
                tagList.add("Tag5")

                val selectedTagList = arrayListOf<String>()
                selectedTagList.add(tagList[0])
                selectedTagList.add(tagList[1])

                val fragment =
                    AddTagBottomSheetDialog(requireContext(), tagList, selectedTagList, this, this)
                childFragmentManager.let {
                    fragment.show(it, "tags")
                }
            }
        }
    }

    override fun onFilterSingleChildClick(childList: ArrayList<String>, childPosition: Int) {
        selectedPriority = childList[childPosition]
    }

    override fun onFilterMultipleChildClick() {
    }

    override fun onFilterFlagClick(redFlag: Boolean) {
        this.redFlag = redFlag
    }

    override fun onFilterDueDateClick(dateStr: String) {
        val fragment =
            DueDateBottomSheetDialog(requireContext(), dateStr, this)
        childFragmentManager.let {
            fragment.show(it, "due_date")
        }
    }

    override fun onClickTag(selectedTagList: ArrayList<String>) {
        this.selectedTagList = selectedTagList
    }

    override fun onCloseClick() {
        taskAdapter?.closeFilter()
    }

    override fun onDateClick(datestr: String) {
        selectedDate = datestr
        taskAdapter?.dueDateFilter(datestr)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            ImageEditor.RC_IMAGE_EDITOR ->
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val imagePath: String? = data.getStringExtra(ImageEditor.EXTRA_EDITED_PATH)
                    val mfile = imagePath?.let { File(it) }
                    Log.e("new:", mfile?.path.toString())
                    selectedUriList[pagerPhotos.currentItem].uri = Uri.fromFile(mfile)
                    mediaAdapter?.notifyItemChanged(pagerPhotos.currentItem)
                    imgAdapter?.notifyItemChanged(pagerPhotos.currentItem + 1)
                }
        }
    }
}