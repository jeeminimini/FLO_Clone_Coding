package com.example.flo

import java.util.ArrayList

data class Album(
        var title: String? ="",
        var singer: String? ="",
        var coverImg: Int? = null,
        var songs: ArrayList<Song>?=null //수록곡. 강의에서는 사용하지 않지만, 더 활용하려면 추가해주는게 좋음.
)
