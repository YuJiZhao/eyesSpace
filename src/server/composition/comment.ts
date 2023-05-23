import api from "@/server/api";
import { PublishCommentInterface, GetCommentListInterface, DelCommentInterface } from "@/d.ts/server/api";
import { CommentApiType } from "@/constant";

// 发表评论
export async function publishComment(type: CommentApiType, req: PublishCommentInterface) {
    return await [
        api.doBlogComment, 
        api.doShuoshuoComment,
        api.doBlogComment,
        api.doBlogComment,
        api.doAboutComment,
        api.doAnimeComment
    ][type](req);
}

// 获取评论
export async function getCommentList(type: CommentApiType, req: GetCommentListInterface) {
    return await [
        api.getBlogCommentList,
        api.getShuoshuoCommentList,
        api.getBlogCommentList,
        api.getBlogCommentList,
        api.getAboutCommentList,
        api.getAnimeCommentList
    ][type](req);
}

// 删除评论
export async function delComment(type: CommentApiType, req: DelCommentInterface) {
    return await [
        api.delBlogComment, 
        api.delShuoshuoComment,
        api.delBlogComment,
        api.delBlogComment,
        api.delAboutComment,
        api.delAnimeComment
    ][type](req);
}