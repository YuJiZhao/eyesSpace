import { get, post } from "./ajax"

const api: ApiObject = {
    safe: async () => {
        await get('/safe');
    },
    getContext: async () => {
        return await get('/getContext');
    },
    getMusicList: async () => {
        return await get('/getMusicList');
    },
}

export default api;