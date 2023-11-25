import { FriendDataCardConfigInterface } from "@/components/content/friend/d.ts/config";

const friendDataCardConfig: Array<FriendDataCardConfigInterface> = [
    {
        title: "有效友链",
        name: "validChain"
    },
    {
        title: "失效友链",
        name: "invalidChain"
    },
    {
        title: "审核中友链",
        name: "verifyingChain"
    }
];

const statusConvert = {
    0: "有效",
    1: "审核中",
    2: "失效",
    3: "已删除",
    4: "暂存"
}

export { friendDataCardConfig, statusConvert };