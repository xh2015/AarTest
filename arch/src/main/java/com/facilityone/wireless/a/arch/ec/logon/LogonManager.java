package com.facilityone.wireless.a.arch.ec.logon;

import com.blankj.utilcode.util.AppUtils;
import com.facilityone.wireless.a.arch.ec.module.LogonResponse;
import com.facilityone.wireless.basiclib.app.FM;
import com.facilityone.wireless.basiclib.utils.GsonUtils;
import com.fm.tool.network.callback.JsonCallback;
import com.fm.tool.network.model.BaseResponse;
import com.lzy.okgo.OkGo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author：gary
 * Email: xuhaozv@163.com
 * description:登录管理
 * Date: 2018/5/28 下午2:41
 */
public class LogonManager {
    private static final String GRANT_TYPE_TOKEN = "password";
    private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    private static final String APP_TYPE = "android";

    private static class Holder {
        private static final LogonManager LOGON_MANAGER = new LogonManager();
    }

    public static LogonManager getInstance() {
        return Holder.LOGON_MANAGER;
    }

    public void logon(String username, String password, JsonCallback<BaseResponse<LogonResponse>> jsonCallback) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("source", "app");
        params.put("loginCode", username);
        params.put("loginPwd", password);
        params.put("appType", APP_TYPE);
        params.put("appVersion", AppUtils.getAppVersionName());
        String loginJson = "";
        try {
            loginJson = GsonUtils.toJson(params, false);
        } catch (Exception e) {
            loginJson = "";
        }
        OkGo.<BaseResponse<LogonResponse>>post(FM.getApiHost() + UserUrl.LOGON_URL)
                .isSpliceUrl(true)
                .upJson(loginJson)
                .execute(jsonCallback);
    }

    public void saveToken(LogonResponse data) {
        if (data == null) {
            return;
        }
        FM.getConfigurator().withUserId(data.userId);
    }
}
