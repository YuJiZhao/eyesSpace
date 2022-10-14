import { ShuoContextInterface } from "@/components/content/shuoshuo/d.ts/shuoContext";

const shuoContext: ShuoContextInterface = {
    data: [],
    init: (cx) => {
        cx.forEach((v) => {
            shuoContext.data.push(v);
        });
    },
}

export { shuoContext };