package com.example.imdbfilmapp.util

interface ToolbarChangesListener {
    fun toolbarName(title: String)
    fun toolbarBackButton(isDetailFragment: Boolean)
}