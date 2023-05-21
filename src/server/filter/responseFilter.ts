import progress from "@/utils/nprogress";
import { codeConfig, siteConfig } from "@/config/program";
import { AxiosResponse } from "axios";
import $utils from "@/utils/helper";
import $process from "@/modules/process";
import { userCenterContext, siteContext } from "@/config/site";

export default (response: AxiosResponse<any, any>) => {
    progress.close();
    // 刷新token
    if(response.headers[siteConfig.tokenHeader.sToken] && response.headers[siteConfig.tokenHeader.lToken]) {
        $utils.setCookie(siteConfig.tokenHeader.sToken, response.headers[siteConfig.tokenHeader.sToken], siteConfig.tokenExpireTime);
        $utils.setCookie(siteConfig.tokenHeader.lToken, response.headers[siteConfig.tokenHeader.lToken], siteConfig.tokenExpireTime);
    }
    // token异常
    if(response.data && [
        codeConfig.authentication_error_illegal_jwt,
        codeConfig.authentication_expired,
        codeConfig.account_freezing,
        codeConfig.account_not_found
    ].includes(response.data.code)) {
        $process.tipShow.warn(response.data.msg);
        $utils.delCookie(siteConfig.tokenHeader.sToken);
        $utils.delCookie(siteConfig.tokenHeader.lToken);
        location.replace(`${userCenterContext.auth}?clientId=${siteContext.clientId}&redirectUrl=${process.env.VITE_SITE_URL + userCenterContext.redirectUrl}`);
    }
    // TODO: 限流
}