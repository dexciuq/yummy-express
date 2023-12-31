package com.dexciuq.yummy_express.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.common.hide
import com.dexciuq.yummy_express.common.setAttrs
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.databinding.ViewSettingsItemBinding

class SettingsItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewSettingsItemBinding.inflate(LayoutInflater.from(context), this)
    private var onArrowRightClickListener: () -> Unit = {}
    private var onSwitchCheckedChangeListener: (isChecked: Boolean) -> Unit = {}
    private var onTextClickListener: () -> Unit = {}

    init {
        setAttrs(attrs, R.styleable.SettingsItemView) {
            binding.text.text = it.getString(R.styleable.SettingsItemView_text)

            val image = it.getDrawable(R.styleable.SettingsItemView_icon)
            binding.image.setImageDrawable(image)

            val colorFilter = it.getColor(
                R.styleable.SettingsItemView_iconTint,
                ContextCompat.getColor(context, R.color.primary_dark)
            )
            binding.image.setColorFilter(colorFilter)

            val actionType = it.getInt(
                R.styleable.SettingsItemView_actionType,
                ActionType.NOTHING.ordinal
            )
            setActionType(ActionType.values()[actionType])
        }
    }

    private fun setActionType(actionType: ActionType) {
        when (actionType) {
            ActionType.NOTHING -> {
                binding.actionContainer.hide()
            }

            ActionType.ARROW_RIGHT -> {
                binding.actionContainer.show()
                binding.actionArrowRight.show()
                binding.actionArrowRight.setOnClickListener {
                    onArrowRightClickListener()
                }
            }

            ActionType.SWITCH -> {
                binding.actionContainer.show()
                binding.actionSwitch.show()
                binding.actionSwitch.setOnCheckedChangeListener { _, isChecked ->
                    onSwitchCheckedChangeListener(isChecked)
                }
            }
        }
    }

    fun setOnArrowRightClickListener(listener: () -> Unit) {
        onArrowRightClickListener = listener
    }

    fun setOnSwitchCheckedChangeListener(listener: (isChecked: Boolean) -> Unit) {
        onSwitchCheckedChangeListener = listener
    }

    fun setOnTextClickListener(listener: () -> Unit) {
        onTextClickListener = listener
    }
}