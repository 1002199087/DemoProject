package com.temporary.demoproject

import android.content.Context
import android.content.Intent
import com.temporary.custom.KotlinConstantValues.Companion.ONLY_BACK
import kotlinx.android.synthetic.main.activity_kotlin_recycler.*


class KotlinRecyclerActivity : KotlinBaseActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            var intent = Intent(context, KotlinRecyclerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            return intent
        }
    }

    override fun getTopbarMode(): Int {
        return ONLY_BACK
    }

    override fun getResId(): Int {
        return R.layout.activity_kotlin_recycler
    }

    override fun getTopbarTitle(): Int {
        return R.string.kotlin_recycler_tip
    }

    override fun initView() {
        refresh_layout.setOnRefreshListener {

        }
        refresh_layout.setOnLoadMoreListener {

        }
    }

    override fun init() {

    }
}
