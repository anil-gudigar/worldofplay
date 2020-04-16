package com.worldofplay.app.stories.details.domain

data class StoriesResponse(
    val `by`: String,
    val descendants: Int,
    val id: Int,
    val kids: List<Int>,
    val score: Int,
    val time: Int,
    val title: String,
    val type: String,
    val url: String
){
    override fun toString(): String {
        return "StoriesResponse(`by`='$`by`', descendants=$descendants, id=$id, kids=$kids, score=$score, time=$time, title='$title', type='$type', url='$url')"
    }
}