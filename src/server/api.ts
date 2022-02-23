import { get, post } from "./ajax"

const api: ApiObject = {
    safe: async () => {
        await get('/safe');
    },
}

export default api;