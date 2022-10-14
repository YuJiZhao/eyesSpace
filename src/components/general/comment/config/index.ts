import { CommentContextInterface } from "@/components/general/comment/d.ts/config";

const commentContext: CommentContextInterface = {
    inputId: "commentInput",
    idPrefix: "commentItem",
    inputPlaceholder: `仅允许登录用户评论 \n评论支持markdown语法 \n暂不提供图片上传服务，如果需要展示图片，可使用在线图床图片转链接服务`,
}

export { commentContext };