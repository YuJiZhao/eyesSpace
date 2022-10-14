import { UserInterface } from "@/d.ts/modules/user";

// 用户信息
const user: UserInterface = {
    data: {},
    status: 0,  // 登录状态，0：未登录，1：登录
    init: (userInfo) => {
        Object.assign(user.data, userInfo);
    },
};

export { user };