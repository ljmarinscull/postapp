package com.lj.postapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentObject(
    var postId: Int? = -1,
    var id: Int? = -1,
    var name: String? = "",
    var email:  String? = "",
    var body: String? = ""
): Parcelable
