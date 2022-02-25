import axios from "axios";
import { start, close } from "@/utils/nprogress";
import utils from "@/utils/helper";

const service = axios.create({
    baseURL: "https://blog.api.eyescode.top/",
    timeout: 6 * 1000,
    withCredentials: true,
    headers: {
        "safe-token": utils.getCookie("security"),
    },
});

service.interceptors.request.use(
    config => {
        start();
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

service.interceptors.response.use(
    response => {
        close();
        return response;
    },
    error => {
        return Promise.reject(error);
    }
);

export async function post<T>(url: string, req: T) {
    const data: resp_type = await service
        .post(config.blogUrl + url, req)
        .then((res) => {
            if (res.status == 200) return res.data;
            else return { code: "400", msg: "request error" };
        })
        .catch(() => {
            return { code: "502", msg: "request error" };
        });
    return data;
}

export async function get(url: string) {
    const data: resp_type = await service
        .get(config.blogUrl + url)
        .then((res) => {
            if (res.status == 200) return res.data;
            else return { code: "400", msg: "request error" };
        })
        .catch(() => {
            return { code: "502", msg: "request error" };
        });
    return data;
}
