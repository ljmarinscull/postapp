package com.lj.postapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostObject(
    var id: Int? = -1,
    var userId: Int? = -1,
    var title: String? = "",
    var body:  String? = ""
): Parcelable
