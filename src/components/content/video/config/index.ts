import { VideoContextInterface, VideoFuncBarInterface } from "@/components/content/video/d.ts/config";
import Resource from "@/config/resource";
import {CardDirection, CardList, CardType } from "@/constant";

const videoContext: VideoContextInterface = {
    storageItemKey: "videoAnnounce",
    storageItemValue: "hello world",
    videoStep: "videoStep",
    videoLikeNum: "videoNum",
    videoAnnounce: 
`<pre>
本站视频均为站长多年来的珍藏，内容广泛。

基于站长理念，本页面的规则如下：
1. 仅向登录用户开放
2. 每位用户每天只能观看5个视频
3. 随机播放视频，无法自选
4. 无法控制视频播放状态(暂停、全屏除外)

如有侵权，请联系站长删除
</pre>`,
}

const videoSideCards = {
    direction: CardDirection.reverse,
    cardType: CardType.CardList,
    cardList: CardList.VideoCardList
}

const videoFuncBar: Array<VideoFuncBarInterface> = [
    {
        name: "likeBtn",
        icon: [Resource.like, Resource.likeActive],
        word: "点赞",
        clickFunc: "doLike"
    },
    {
        name: "commentBtn",
        icon: Resource.comment,
        word: "评论",
        clickFunc: "doComment"
    },
    {
        name: "changeBtn",
        icon: Resource.switch,
        word: "切换视频",
        clickFunc: "changeVideo"
    },
    {
        name: "screenBtn",
        icon: Resource.fullscreen,
        word: "全屏播放",
        clickFunc: "doFullScreen"
    },
    {
        name: "announceBtn",
        icon: Resource.announce,
        word: "打开公告",
        clickFunc: "openAnnounce"
    },
    {
        name: "clearBtn",
        icon: Resource.hide,
        word: ["关闭侧栏", "打开侧栏"],
        clickFunc: "doClear"
    }
];

export { videoContext, videoSideCards, videoFuncBar };