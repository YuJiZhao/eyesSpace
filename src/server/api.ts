import { get, post } from "./ajax"

const api: ApiObject = {
    safe: async () => {
        await get('/safe');
    },
    getCopywriting: async () => {
        return await get('/getCopywriting');
    },
    getMusicList: async () => {
        return await get('/getMusicList');
    },
}

export default api;