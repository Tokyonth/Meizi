package com.tokyonth.mz.ui.activity

import android.content.Intent
import android.graphics.Color
import androidx.core.view.WindowCompat

import com.tokyonth.bt.utils.ktx.delay
import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.R
import com.tokyonth.mz.base.BaseActivity
import com.tokyonth.mz.databinding.ActivityPasswordBinding
import com.tokyonth.mz.utils.SPUtils.getSP
import com.tokyonth.mz.utils.SPUtils.putSP
import com.tokyonth.mz.utils.ktx.snack
import com.tokyonth.mz.utils.ktx.string
import com.tokyonth.mz.view.PwdKeyboardView

class PasswordActivity : BaseActivity() {

    private val binding: ActivityPasswordBinding by lazyBind()

    private var passwordMode: Int = 0

    override fun setVbRoot() = binding

    override fun initData() {
        passwordMode = intent.getIntExtra(Constants.INTENT_PASSWORD_MODE, 0)
    }

    override fun initView() {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            window.navigationBarColor = Color.WHITE
        }
        if (passwordMode == 2) {
            binding.tvPassword.setText(R.string.text_pwd_verified)
        }
        binding.etPassword.setOnTextInputListener {
            when (passwordMode) {
                0 -> verifiedPwd(it)
                1 -> savePwd(it)
                2 -> removePwd(it)
            }
        }
        binding.keyBoard.setOnKeyListener(object : PwdKeyboardView.OnKeyListener {
            override fun onInput(text: String) {
                binding.etPassword.append(text)
            }

            override fun onDelete() {
                val content: String = binding.etPassword.text.toString()
                if (content.isNotEmpty()) {
                    binding.etPassword.setText(content.substring(0, content.length - 1))
                }
            }
        })
    }

    private fun verifiedPwd(pwd: String) {
        if (pwd == getSP(Constants.SP_APP_PASSWORD, "")) {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(Constants.INTENT_KEY_VERIFIED, true)
            }
            startActivity(intent)
            finish()
        } else {
            clearPwd()
        }
    }

    private fun savePwd(pwd: String) {
        putSP(Constants.SP_APP_PASSWORD, pwd)
        putSP(Constants.SP_NEED_PASSWORD, true)
        finish()
    }

    private fun removePwd(pwd: String) {
        if (pwd == getSP(Constants.SP_APP_PASSWORD, "")) {
            putSP(Constants.SP_APP_PASSWORD, "")
            putSP(Constants.SP_NEED_PASSWORD, false)
            finish()
        } else {
            clearPwd()
        }
    }

    private fun clearPwd() {
        delay(500) {
            binding.etPassword.setText("")
        }
        snack(string(R.string.text_pwd_error))
    }

}
