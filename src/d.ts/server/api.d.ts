// 接口返回格式
export declare interface RespInterface {
    code: number;
    msg: string;
    [propName: string]: any;
}

// 统一api管理
export declare interface ApiObject extends 
    CommonInterface, 
    SiteInterface, 
    UserApiInterface, 
    MusicInterface, 
    VideoInterface,
    ShuoshuoInterface,
    BlogInterface
{}

// 分页
interface PageInterface {
    [page]: number;
    [pageSize]: number;
}

/*
 ***************************************************************************************
 *                               composition——comment
 ***************************************************************************************
 */

export declare interface PublishCommentInterface {
    objectId: number | string;
    landlord?: number;
    replyId?: number;
    originalComment: string;
    comment: string;
}

export declare interface GetCommentListInterface extends PageInterface {
    id: number | string;
}

export declare type DelCommentInterface = Array<number>; 

/*
 ***************************************************************************************
 *                                    common
 ***************************************************************************************
 */
export declare interface CommonInterface {
    getImgCode: () => Promise<RespInterface>;
    getEmailCode: (req: GetEmailCodeReqInterface) => Promise<RespInterface>;
    login: (req: LoginReqInterface) => Promise<RespInterface>;
}

interface GetEmailCodeReqInterface {
    email: string;
    imgCode: string;
    key: string;
}

interface LoginReqInterface {
    email: string;
    emailCode: string;
}

/*
 ***************************************************************************************
 *                                    site
 ***************************************************************************************
 */
export declare interface SiteInterface {
    getContext: () => Promise<RespInterface>;
    getHomeList: (req: GetHomeListReqInterface) => Promise<RespInterface>;
    addSpaceVisit: (req: AddSpaceVisitReqInterface) => Promise<RespInterface>;
    doAboutComment: (req: PublishCommentInterface) => Promise<RespInterface>;
    getAboutCommentList: (req: GetCommentListInterface) => Promise<RespInterface>;
    delAboutComment: (req: DelCommentInterface) => Promise<RespInterface>;
}

interface GetHomeListReqInterface extends PageInterface {}

interface AddSpaceVisitReqInterface {
    path: string;
}

/*
 ***************************************************************************************
 *                                    blog
 ***************************************************************************************
 */
export declare interface BlogInterface {
    getBlogListInfo: () => Promise<RespInterface>;
    getBlogList:(req: BlogListReqInterface) => Promise<RespInterface>;
    getBlogInfo: (req: Array<any>) => Promise<RespInterface>;
    getBlogCategory: () => Promise<RespInterface>;
    getBlogLabel: () => Promise<RespInterface>;
    doBlogLike: (req: DoBlogLikeReqInterface) => Promise<RespInterface>;
    doBlogCollect: (req: DoBlogCollectReqInterface) => Promise<RespInterface>;
    doBlogComment: (req: PublishCommentInterface) => Promise<RespInterface>;
    getBlogCommentList: (req: GetCommentListInterface) => Promise<RespInterface>;
    delBlogComment: (req: DelCommentInterface) => Promise<RespInterface>;
}

interface BlogListReqInterface extends PageInterface {}

interface DoBlogLikeReqInterface {
    id: any;
}

interface DoBlogCollectReqInterface {
    id: any;
}

/*
 ***************************************************************************************
 *                                    shuoshuo
 ***************************************************************************************
 */
export declare interface ShuoshuoInterface {
    getShuoshuoList: (req: ShuoshuoListReqInterface) => Promise<RespInterface>;
    getShuoshuoListInfo: () => Promise<RespInterface>;
    getShuoshuoSingleInfo: (req: ShuoshuoSingleInfoReqInterface) => Promise<RespInterface>;
    doShuoshuoComment: (req: PublishCommentInterface) => Promise<RespInterface>;
    getShuoshuoCommentList: (req: GetCommentListInterface) => Promise<RespInterface>;
    delShuoshuoComment: (req: DelCommentInterface) => Promise<RespInterface>;
}

interface ShuoshuoListReqInterface extends PageInterface {}

interface ShuoshuoSingleInfoReqInterface {
    id: string;
}

/*
 ***************************************************************************************
 *                                    video
 ***************************************************************************************
 */
export declare interface VideoInterface {
    getVideoInfo: () => Promise<RespInterface>;
    doVideoUserLike: (req: DoVideoUserLikeReqInterface) => Promise<RespInterface>;
}

interface DoVideoUserLikeReqInterface {
    id: string;
}

/*
 ***************************************************************************************
 *                                    music
 ***************************************************************************************
 */
export declare interface MusicInterface {
    getMusicInfo: () => Promise<RespInterface>;
    doMusicUserLike: (req: DoMusicUserLikeReqInterface) => Promise<RespInterface>;
}

interface DoMusicUserLikeReqInterface {
    id: string;
}

/*
 ***************************************************************************************
 *                                    user
 ***************************************************************************************
 */
export declare interface UserApiInterface {
    getUserInfo: () => Promise<RespInterface>;
    updateUserInfo: (req: UpdateUserInfoReqInterface) => Promise<RespInterface>;
}

interface UpdateUserInfoReqInterface {
    name: string
}