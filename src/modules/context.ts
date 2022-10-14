import { ContextInterface } from "@/d.ts/modules/context";

const context: ContextInterface = {
    data: {},
    init: (cx) => {
        Object.assign(context.data, cx);
    },
}

export { context };