package com.kardia.membership.core.extension

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.kardia.membership.R
import com.google.android.material.textfield.TextInputLayout
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun View.cancelTransition() {
    transitionName = null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.setBackground() {
    this.visibility = View.GONE
}

fun View.extendTouch() {
    val parent = this.parent as View
    val extraSpace = 10.dp2px
    parent.post {
        val touchableArea = Rect()
        this.getHitRect(touchableArea)
        touchableArea.top -= extraSpace
        touchableArea.bottom += extraSpace
        touchableArea.left -= extraSpace
        touchableArea.right += extraSpace
        parent.touchDelegate = TouchDelegate(touchableArea, this)
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.loadFromUrl(
    url: String?,
    isAnimation: Boolean = true,
    isPlaceHolder: Boolean = false
) {
    if (url == null) return
    val requestBuilder = Glide.with(this.context.applicationContext).load(url).centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    if (isAnimation) requestBuilder.transition(DrawableTransitionOptions.withCrossFade())
//    if (isPlaceHolder) requestBuilder.placeholder(R.drawable.place_holder) else requestBuilder.placeholder(null)
    requestBuilder.into(this)
}


fun ImageView.loadFromUrlRounded(
    url: String?,
    radius: Float
) {
    if (url == null) return
    val options: RequestOptions = RequestOptions()
        .transform(
            CenterCrop(),
            RoundedCorners(
                convertDpToPixel(
                    radius,
                    context
                ).toInt()
            )
        )
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .dontAnimate()
    val requestBuilder = Glide.with(this.context.applicationContext).load(url).apply(options)
    requestBuilder.into(this)
}

fun convertDpToPixel(dp: Float, context: Context): Float {
    return dp * (context.resources
        .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun ImageView.loadFromLocal(
    resourceId: Int?,
    isAnimation: Boolean = true,
    isPlaceHolder: Boolean = false
) {
    if (resourceId == null) return
    val requestBuilder = Glide.with(this.context.applicationContext).load(resourceId).centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
    if (isAnimation) requestBuilder.transition(DrawableTransitionOptions.withCrossFade())
    requestBuilder.into(this)
}

fun ImageView.loadFromAnyWithoutCache(
    resourceId: Any?,
    isAnimation: Boolean = false,
    isPlaceHolder: Boolean = false
) {
    val requestBuilder = Glide.with(this.context.applicationContext).load(resourceId).centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
    if (isAnimation) requestBuilder.transition(DrawableTransitionOptions.withCrossFade())
    requestBuilder.into(this)
}

fun ImageView.loadFromAnyWithCache(
    resourceId: Any?
) {
    val requestBuilder = Glide.with(this.context.applicationContext).load(resourceId).centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
    requestBuilder.into(this)
}

fun ImageView.loadUrlAndPostponeEnterTransition(url: String, activity: FragmentActivity) {
    val target: Target<Drawable> = ImageViewBaseTarget(this, activity)
    Glide.with(context.applicationContext).load(url).into(target)
}

val Int.px2dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.dp2px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

private class ImageViewBaseTarget(var imageView: ImageView?, var activity: FragmentActivity?) :
    BaseTarget<Drawable>() {
    override fun removeCallback(cb: SizeReadyCallback) {
        imageView = null
        activity = null
    }

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        imageView?.setImageDrawable(resource)
        activity?.supportStartPostponedEnterTransition()
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        activity?.supportStartPostponedEnterTransition()
    }

    override fun getSize(cb: SizeReadyCallback) = cb.onSizeReady(SIZE_ORIGINAL, SIZE_ORIGINAL)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun TextView.showStrikeThrough(show: Boolean) {
    paintFlags =
        if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}

fun TextView.setTextViewDrawableColor(color: Int) {
    for (drawable in this.compoundDrawables) {
        if (drawable != null) {
            drawable.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(
                    this.context,
                    color
                ), PorterDuff.Mode.SRC_IN
            )
        }
    }
}

fun EditText.addSuffix(suffix: String) {
    val editText = this
    val formattedSuffix = " $suffix"
    var text = ""
    var isSuffixModified = false

    val setCursorPosition: () -> Unit =
        { Selection.setSelection(editableText, editableText.length - formattedSuffix.length) }

    val setEditText: () -> Unit = {
        editText.setText(text)
        setCursorPosition()
    }

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            val newText = editable.toString()

            if (isSuffixModified) {
                // user tried to modify suffix
                isSuffixModified = false
                setEditText()
            } else if (text.isNotEmpty() && newText.length < text.length && !newText.contains(
                    formattedSuffix
                )
            ) {
                // user tried to delete suffix
                setEditText()
            } else if (!newText.contains(formattedSuffix)) {
                // new input, add suffix
                text = "$newText$formattedSuffix"
                setEditText()
            } else {
                text = newText
            }
        }

        override fun beforeTextChanged(
            charSequence: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
            charSequence?.let {
                val textLengthWithoutSuffix = it.length - formattedSuffix.length
                if (it.isNotEmpty() && start > textLengthWithoutSuffix) {
                    isSuffixModified = true
                }
            }
        }

        override fun onTextChanged(
            charSequence: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
        }
    })
}

//fun TextInputLayout.markRequiredInRed() {
//    hint = buildSpannedString {
//        append(hint)
//        color(Color.RED) { append(" *") } // Mind the space prefix.
//    }
//}
//
//fun TextView.markRequiredInRed() {
//    buildSpannedString {
//        color(Color.RED) { append(" *") } // Mind the space prefix.
//    }
//}

fun ImageView.setTint(@ColorInt color: Int?) {
    if (color == null) {
        ImageViewCompat.setImageTintList(this, null)
        return
    }
    ImageViewCompat.setImageTintMode(this, PorterDuff.Mode.SRC_ATOP)
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
}

fun Double.formatDot(): String {
    val format: DecimalFormat = NumberFormat.getNumberInstance(Locale.FRENCH) as DecimalFormat
    format.maximumFractionDigits = 2
    return format.format(this)
}


fun View.margin(
    left: Float? = null,
    top: Float? = null,
    right: Float? = null,
    bottom: Float? = null
) {
    layoutParams<ConstraintLayout.LayoutParams> {
        left?.run { leftMargin = dpToPx(this) }
        top?.run { topMargin = dpToPx(this) }
        right?.run { rightMargin = dpToPx(this) }
        bottom?.run { bottomMargin = dpToPx(this) }
    }
}

inline fun <reified T : ConstraintLayout.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
fun Context.dpToPx(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()