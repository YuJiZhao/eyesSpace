import axios from 'axios';
axios.defaults.withCredentials = true;

function getCookie(name: string) {
    let arr,
        reg = new RegExp('(^| )' + name + '=([^;]*)(;|$)');
    if ((arr = document.cookie.match(reg))) return unescape(arr[2]);
    else return 'null';
}

// 获取配置信息
function getConfig() {
    return {
        timeout: 60000,
        headers: {
            'safe-token': getCookie('security')
        },
        withCredentials: true
    };
}

export async function post<T>(url: string, req: T) {
    const data: resp_type = await axios
    .post(config.blogUrl + url, req, getConfig())
        .then(res => {
            if (res.status == 200) return res.data;
            else return { code: '400', msg: 'request error' };
        })
        .catch(() => {
            return { code: '502', msg: 'request error' };
        });
    return data;
}

export async function get(url: string) {
    const data: resp_type = await axios
    .get(config.blogUrl + url, getConfig())
        .then(res => {
            if (res.status == 200) return res.data;
            else return { code: '400', msg: 'request error' };
        })
        .catch(() => {
            return { code: '502', msg: 'request error' };
        });
    return data;
}
