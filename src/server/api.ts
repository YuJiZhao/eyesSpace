import { ApiObject } from "@/d.ts/api";
import { get, post } from "./ajax"

const api: ApiObject = {
    safe: async () => {
        await get('/safe');
    },
    copywriting: async req => {
        return await post('/copywriting', req);
    }
}

export default api;