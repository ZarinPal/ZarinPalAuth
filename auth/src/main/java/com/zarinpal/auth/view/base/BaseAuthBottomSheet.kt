package com.zarinpal.auth.view.base

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zarinpal.auth.R
import com.zarinpal.provider.core.Font
import com.zarinpal.provider.core.ViewPumper
import com.zarinpal.provider.view.IAuthViewInflater
import com.zarinpal.provider.view.bottomSheets.IAuthBackPress


/**
 * Created by ImanX.
 * Asghar | Copyrights 2019 ZarinPal Crop.
 */
internal abstract class BaseAuthBottomSheet : BottomSheetDialogFragment(),
    IAuthViewInflater,
    IAuthBackPress {

    private lateinit var progressBar: ProgressBar
    private lateinit var contentView: View


    open val forceDismiss get() = false

    protected val bottomSheetBehavior: BottomSheetBehavior<View>?
        get() {
            val bottomSheet =
                (dialog as? BottomSheetDialog)?.findViewById<View>(R.id.design_bottom_sheet)

            return if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet)
            } else {
                null
            }
        }

//    override fun getTheme(): Int = if (instance?.nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
//        R.style.BottomSheetDialogThemeDark
//    } else {
//        R.style.BottomSheetDialogTheme
//    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) = BottomSheetDialog(context!!, theme)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val relativeLayout = RelativeLayout(context)

        //Add it
        //activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        view?.let {

        }
        contentView = inflater.inflate(getLayout(container), container, false)
        progressBar = ProgressBar(context)
//        progressBar.indeterminateDrawable.setColorFilter(
//            getCompactColor(
//                context!!,
//                (instance?.nightMode == AppCompatDelegate.MODE_NIGHT_YES).ternary(
//                    R.color.backgroundColorLight,
//                    R.color.backgroundColorDark
//                )
//            ), PorterDuff.Mode.MULTIPLY
//        )
        progressBar.visibility = View.GONE
        val progressBarParams = RelativeLayout.LayoutParams(200, 200)
        progressBarParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        relativeLayout.addView(progressBar, progressBarParams)
        relativeLayout.addView(contentView)
        ViewPumper.pump(contentView, Font.Bold)
        onCreateView(relativeLayout)



        return relativeLayout
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                onBackPress()
                return@setOnKeyListener false
            }
            return@setOnKeyListener true
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        preventNormalDismiss()
        adjustResizeView()
    }


    override fun onBackPress() {
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
        } else {
            dismissAllowingStateLoss()
        }
    }


    fun show(fm: FragmentManager) {
        //   super.show(fm, this.javaClass.simpleName)

        val ft = fm.beginTransaction()
        ft.add(this, this.javaClass.name)
        ft.commitAllowingStateLoss()
    }

    private fun adjustResizeView() {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        bottomSheetBehavior?.let {
            it.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }


    private fun preventNormalDismiss() {

        if (!forceDismiss) {
            return
        }

        bottomSheetBehavior?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

                if (slideOffset > -0.80000F) {
                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
                } else if (slideOffset <= -1.0f) {
                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
                    bottomSheet.postDelayed({ dismissAllowingStateLoss() }, 200)
                }

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }
        })
    }

}