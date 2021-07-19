package com.maple.basekit.vm

import android.text.TextUtils
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.SPUtils
import com.maple.basekit.model.repository.CommonRepository
import com.maple.baselib.base.BaseViewModel
import com.maple.baselib.utils.LogUtils
import com.maple.common.utils.ToastUtils
import java.util.*

class AccountViewModel: BaseViewModel(){

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }


    val account: ObservableField<String> = ObservableField("13717591366")
    val password: ObservableField<String> = ObservableField("096719")

    val loginState: ObservableField<Boolean> = ObservableField(false)

    fun onLogin() {
        onClickProxy {
            val phone = account.get()
            val pwd = password.get()

            if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)) {
                ToastUtils.showToast("请输入账号或密码")
                return@onClickProxy
            }
            login(phone!!,pwd!!)
        }
    }



    private fun login (phone:String,pwd:String) {
        LogUtils.logGGQ("账号：${phone}--密码：${pwd}")
        launchOnlyResult({
            val params: WeakHashMap<String, Any> = WeakHashMap()
//            params.put("phone","15664284736")
//            params.put("pwd","136512")

            params.put("phone",phone)
            params.put("pwd",pwd)

            repository.loginPhone(params)
        }, success = {
            it?.let { data ->
                LogUtils.logGGQ("---登录--success--->>${data.name}")
            }
        }, error = {
            LogUtils.logGGQ("---登录---error-->>${it.errMsg}")
        }, complete = {
            LogUtils.logGGQ("---登录---complete--")
        })
    }


}