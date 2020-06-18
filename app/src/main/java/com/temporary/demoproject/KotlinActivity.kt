package com.temporary.demoproject

import android.content.Context
import android.content.Intent
import android.view.View
import com.temporary.custom.KotlinConstantValues.Companion.ONLY_BACK
import com.vise.log.ViseLog

class KotlinActivity : KotlinBaseActivity() {
    override fun getTopbarMode(): Int {
        return ONLY_BACK
    }

    override fun getResId(): Int {
        return R.layout.activity_kotlin
    }

    override fun getTopbarTitle(): Int {
        return R.string.kotlin_tip
    }

    override fun initView() {
    }

    override fun init() {
    }

    companion object {
        fun getIntent(context: Context): Intent {
            var intent = Intent(context, KotlinActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            return intent
        }
    }

    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.click_btn ->
                ViseLog.d("onViewClicked")
            R.id.recycler_btn -> startActivity(KotlinRecyclerActivity.getIntent(this))
        }
    }
}
