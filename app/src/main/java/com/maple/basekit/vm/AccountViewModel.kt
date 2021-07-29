package com.maple.basekit.vm

import android.text.TextUtils
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.SPUtils
import com.maple.basekit.db.DBHelper
import com.maple.basekit.db.UserInfo
import com.maple.basekit.model.entity.LoginEntity
import com.maple.basekit.model.repository.CommonRepository
import com.maple.baselib.app.manager.SingleLiveEvent
import com.maple.baselib.base.BaseViewModel
import com.maple.baselib.http.error.ERROR
import com.maple.baselib.http.error.ResponseThrowable
import com.maple.baselib.utils.LogUtils
import com.maple.common.utils.ToastUtils
import java.util.*

class AccountViewModel: BaseViewModel(){

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }


    val account: ObservableField<String> = ObservableField("13717591366")
    val password: ObservableField<String> = ObservableField("096719")

    /// 登录按钮状态
    val loginState: ObservableField<Boolean> = ObservableField(false)

    /// 忘记密码
    val forgotPwdEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    /// 登录成功
    val loginEvent: SingleLiveEvent<Any> = SingleLiveEvent()


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

    fun onForgotPassword () {
        forgotPwdEvent.call()
    }

    private fun login (phone:String,pwd:String) {
        LogUtils.logGGQ("账号：${phone}--密码：${pwd}")

        launchOnlyResult(block =  {
            val params: WeakHashMap<String, Any> = WeakHashMap()
//            params.put("phone","15664284736")
//            params.put("pwd","136512")
            params.put("phone",phone)
            params.put("pwd",pwd)

            repository.loginPhone(params)
        }, success = {
            it?.let { data ->
                LogUtils.logGGQ("---登录--success--->>1-${data.toString()}")
                val user: UserInfo? = UserInfo(data.userId,data.name,data.phone,data.idNo,data.saToken)
                if(DBHelper.saveUser(user)) {
                    loginEvent.call()
                } else {
                    // 存储用户信息失败的情况
                   throw ResponseThrowable(ERROR.DATA_ERROR)
                }
            }?:let {
                // data 为空
                throw ResponseThrowable(ERROR.DATA_ERROR)
            }
        })
    }

}