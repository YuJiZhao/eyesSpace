import $utils from "@/utils/helper";
import { siteConfig } from "@/config/program";
import { AddHeaderType } from "@/d.ts/server/ajax";

export let addHeader: AddHeaderType = (existent) => {
    let config = {...existent};
    if($utils.getCookie(siteConfig.tokenHeader) == "") return config;
    if(config.headers) {
        config.headers.Authorization = $utils.getCookie(siteConfig.tokenHeader);
    } else {
        config.headers = {
            Authorization: $utils.getCookie(siteConfig.tokenHeader)
        }
    }
    return config;
};