export declare interface CommentItemInterface {
    id: number;
    avatar: string;
    name: string;
    comment: string;
    createTime: string;
    owner: boolean;
    comments: number;
    children: Array<CommentItemReplyInterface>;
}

export declare interface CommentItemReplyInterface {
    id: number;
    replyId: number;
    replyName: string;
    avatar: string;
    name: string;
    comment: string;
    createTime: string;
    owner: boolean;
    comments: number;
}