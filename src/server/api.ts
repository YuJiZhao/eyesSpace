import { ApiObject } from "@/d.ts/api";
import { get, post } from "./ajax"

const api: ApiObject = {
    safe: async () => {
        await get('/safe');
    },
    copywriting: async () => {
        return await get('/copywriting');
    }
}

export default api;