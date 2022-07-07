package ru.netology.nmedia.data

import java.io.Serializable

data class BundleProperties(
    val text: String,
    val url: String?
) : Serializable