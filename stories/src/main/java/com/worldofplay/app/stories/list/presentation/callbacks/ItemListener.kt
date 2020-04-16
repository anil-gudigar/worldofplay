package com.worldofplay.app.stories.list.presentation.callbacks

import com.worldofplay.app.stories.details.domain.StoriesResponse

interface ItemListener {
    fun onClicked(storiesResponse : StoriesResponse)
}