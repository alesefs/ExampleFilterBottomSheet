package com.example.searchandbottomsheet

interface TagListener {
    fun chooseToTag(item: MutableList<String>)
    fun chooseToAction(item: MutableList<String>)
}