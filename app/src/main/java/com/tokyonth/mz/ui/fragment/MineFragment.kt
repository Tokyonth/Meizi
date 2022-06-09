package com.tokyonth.mz.ui.fragment

import android.content.Intent
import androidx.appcompat.widget.SwitchCompat

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.base.BaseFragment
import com.tokyonth.mz.databinding.FragmentMineBinding
import com.tokyonth.mz.ui.activity.PasswordActivity
import com.tokyonth.mz.utils.SPUtils.getSP
import com.tokyonth.mz.utils.ktx.snack

class MineFragment : BaseFragment() {

    private val binding: FragmentMineBinding by lazyBind()

    override fun setVbRoot() = binding

    override fun initData() {

    }

    override fun initView() {
        binding.switchPassword.setOnClickListener {
            val isChecked = (it as SwitchCompat).isChecked
            val mode = if (isChecked) {
                1
            } else {
                2
            }
            val intent = Intent(requireContext(), PasswordActivity::class.java).apply {
                putExtra(Constants.INTENT_PASSWORD_MODE, mode)
            }
            startActivity(intent)
        }
        binding.tvClearCache.setOnClickListener {
            snack("oh...")
        }
    }

    override fun onResume() {
        super.onResume()
        val needPwd = getSP(Constants.SP_NEED_PASSWORD, false)
        binding.switchPassword.isChecked = needPwd
    }

}
