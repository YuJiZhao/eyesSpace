import axios from "axios";
import { start, close } from "@/utils/nprogress";
import utils from "@/utils/helper";
import { config } from "@/config/index";

const service = axios.create({
    baseURL: config.apiUrl,
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
        close();
        return Promise.reject(error);
    }
);

export async function post<T>(url: string, req: T) {
    const data: RespType = await service
        .post(url, req)
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
    const data: RespType = await service
        .get(url)
        .then((res) => {
            if (res.status == 200) return res.data;
            else return { code: "400", msg: "request error" };
        })
        .catch(() => {
            return { code: "502", msg: "request error" };
        });
    return data;
}
