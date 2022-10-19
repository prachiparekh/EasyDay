package com.app.easyday.app.sources.local.interfaces

interface FilterTypeInterface {
    fun onFilterTypeClick(position: Int)
    fun onFilterSingleChildClick(childList:ArrayList<String>,childPosition: Int)
    fun onFilterMultipleChildClick()
    fun onFilterFlagClick(redFlag:Boolean)
    fun onFilterDueDateClick(dateStr:String)
}