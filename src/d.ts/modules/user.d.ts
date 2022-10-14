export declare interface UserInterface {
    readonly data: Partial<UserType>;
    status: number;
    init: (info: Partial<UserType>) => void;
}

export declare interface UserType {
    id: number,
    email: string,
    username: string,
    avatar: string,
    createTime: string
}