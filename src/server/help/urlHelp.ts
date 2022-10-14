import { BuildGetUrlType, BuildMixGetUrlType } from "@/d.ts/server/ajax";

let buildParamUrl: BuildGetUrlType = (baseUrl, req) => {
    if (req == null) return baseUrl;
    let url = baseUrl + "?";
    let propertyList = Object.keys(req);
    for (let i = 0; i < propertyList.length; i++) {
        url += propertyList[i] + "=" + req[propertyList[i]];
        if (i != propertyList.length - 1) url += "&";
    }
    return url;
};

let buildPathUrl: BuildGetUrlType = (url, req) => {
    if (req == null) return url;
    req.forEach((v: string | number) => {
        url += ("/" + v);
    });
    return url;
};

let buildMixUrl: BuildMixGetUrlType = (url, req) => {
    if(req == null) return url;
    if(req.param == null) return buildPathUrl(url, req.path);
    if(req.path == null) return buildParamUrl(url, req.param);
    return buildParamUrl(
        buildPathUrl(url, req.path),
        req.param
    );
}

export default [buildParamUrl, buildPathUrl, buildMixUrl];