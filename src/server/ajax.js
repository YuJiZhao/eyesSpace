import axios from "axios";
import { urlConfig, programConfig } from "@/config";
import $utils from "@/utils/helper";

const service = axios.create({
    baseURL: urlConfig.baseUrl,
    timeout: 60 * 1000,
    withCredentials: true,
    headers: {
        sAuth: programConfig.sToken,
        lAuth: programConfig.lToken
    }
});

service.interceptors.request.use(
    config => {
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

service.interceptors.response.use(
    response => {
        return response.data;
    },
    error => {
        return Promise.reject(error);
    }
);

export function get(url, req) {
    return service.get($utils.buildGetUrl(url, req));
}

export function post(url, req) {
    return service.post(url, req);
}

export function put(url, req) {
    return service.put(url, req);
}

export function del(url, req) {
    return service.delete(url, {data: req});
}