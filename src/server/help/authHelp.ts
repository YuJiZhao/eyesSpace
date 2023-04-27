import $utils from "@/utils/helper";
import { siteConfig } from "@/config/program";
import { AddHeaderType } from "@/d.ts/server/ajax";

export let addHeader: AddHeaderType = (existent) => {
    let config = {...existent};
    if($utils.getCookie(siteConfig.tokenHeader.sToken) == "" || $utils.getCookie(siteConfig.tokenHeader.lToken) == "") {
        return config;
    }
    config.headers = config.headers || {};
    config.headers[siteConfig.tokenHeader.sToken] = $utils.getCookie(siteConfig.tokenHeader.sToken);
    config.headers[siteConfig.tokenHeader.lToken] = $utils.getCookie(siteConfig.tokenHeader.lToken);
    return config;
};