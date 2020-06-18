package com.temporary.demoproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.temporary.custom.KotlinConstantValues
import com.temporary.custom.KotlinConstantValues.Companion.ONLY_BACK
import kotlinx.android.synthetic.main.activity_kotlin_base.*

abstract class KotlinBaseActivity : AppCompatActivity(), KotlinConstantValues {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_base)
        layoutInflater.inflate(getResId(), content_view, true)

        initTopbar()
        initView()
        init()
    }

    private fun initTopbar() {
        QMUIStatusBarHelper.translucent(this)
        topbar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        topbar.setTitle(resources.getString(getTopbarTitle()))
        when (getTopbarMode()) {
            ONLY_BACK -> {
                topbar.addLeftBackImageButton().setOnClickListener {
                    finish()
                }
            }
        }
    }

    abstract fun getResId(): Int

    abstract fun getTopbarTitle(): Int

    abstract fun initView()

    abstract fun init()

    abstract fun getTopbarMode(): Int
}
