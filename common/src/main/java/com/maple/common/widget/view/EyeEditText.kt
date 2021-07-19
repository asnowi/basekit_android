package com.maple.common.widget.view
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.appcompat.widget.AppCompatEditText
import com.maple.common.R

class EyeEditText:AppCompatEditText {

    private var isOpen: Boolean = false


    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }


    private val eyeOpenDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_eye_24, null) as Drawable
    private val eyeCloseDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_ueye_24, null) as Drawable

    private var eyeButtonImage: Drawable = eyeCloseDrawable

    private fun eyeDrawableButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, eyeButtonImage, null)
    }


    private fun hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isClearButtonVisible() && wasClearButtonTouched(event)) {
            onClearButtonTouched(event)
            return true
        }

        return super.onTouchEvent(event)
    }

    override fun onTextChanged(
            text: CharSequence?,
            start: Int,
            lengthBefore: Int,
            lengthAfter: Int
    ) {
        if (text?.isNotEmpty() == true) {
            eyeDrawableButton()
        } else {
            hideClearButton()
        }
    }

    private fun isClearButtonVisible(): Boolean {
        return compoundDrawablesRelative[2] != null
    }

    private fun wasClearButtonTouched(event: MotionEvent): Boolean {
        val isClearButtonAtTheStart = layoutDirection == View.LAYOUT_DIRECTION_RTL

        return if (isClearButtonAtTheStart) {

            val clearButtonEnd = paddingStart + eyeButtonImage.intrinsicWidth
            event.x < clearButtonEnd

        } else {

            val clearButtonStart = width - eyeButtonImage.intrinsicWidth - paddingEnd
            event.x > clearButtonStart

        }
    }

    private fun onClearButtonTouched(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                if(isOpen) {
                    eyeButtonImage = eyeOpenDrawable
                    this.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    this.isOpen = false
                } else {
                    eyeButtonImage = eyeCloseDrawable
                    this.transformationMethod = PasswordTransformationMethod.getInstance()
                    this.isOpen = true
                }
                this.setSelection(this.text.toString().length)
                eyeDrawableButton()
                listener?.onClearClick()
            }
        }
    }

    interface OnClickListener{
        fun onClearClick()
    }
}