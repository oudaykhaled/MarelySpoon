package com.ouday.marelyspoon.main.presentation.ui

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ouday.marelyspoon.R
import com.ouday.marelyspoon.main.data.model.Recipe
import com.ouday.marelyspoon.main.presentation.ui.adapter.MAX_TAGS_TO_SHOW

fun bindTags(constraintLayoutRoot: ConstraintLayout, cpTags: ChipGroup, item: Recipe, showAll: Boolean = false) {
    if (item.tags != null) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayoutRoot)
        constraintSet.setVerticalBias(R.id.tvRecipeName, 0.0f)
        constraintSet.applyTo(constraintLayoutRoot)

        item.tags?.let {tags ->
            if (!showAll) tags.take(MAX_TAGS_TO_SHOW)?.forEach {addTags(cpTags, it) }
            else tags?.forEach {addTags(cpTags, it) }
        }

    } else {
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayoutRoot)
        constraintSet.setVerticalBias(R.id.tvRecipeName, 0.5f)
        constraintSet.applyTo(constraintLayoutRoot)
    }
}

fun addTags(chipGroup: ChipGroup, tagName: String) {
    chipGroup.addView(Chip(chipGroup.context).apply {
        text = tagName
        isClickable = false
        isCheckable = false
        chipEndPadding = 0f
        chipStartPadding = 0f
    })
}