import progress from "@/utils/nprogress";
import { codeConfig, siteConfig } from "@/config/program";
import { AxiosResponse } from "axios";
import $utils from "@/utils/helper";
import process from "@/modules/process";
import router from "@/router";

export default (response: AxiosResponse<any, any>) => {
    progress.close();
    // 登录时设置cookie以及刷新cookie
    if(response.config.url == "/auth/login/doLogin" || response.headers.authorization != null) {
        $utils.setCookie(siteConfig.tokenHeader, response.headers.authorization, siteConfig.tokenExpireTime);
    }
    // token异常
    if([
        codeConfig.authentication_error_illegal_jwt,
        codeConfig.authentication_expired,
        codeConfig.account_freezing
    ].includes(response.data.code)) {
        process.tipShow.warn(response.data.msg);
        $utils.delCookie(siteConfig.tokenHeader);
        router.push("/login");
    }
}