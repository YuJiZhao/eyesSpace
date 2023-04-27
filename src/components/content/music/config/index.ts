import { MusicContextInterface, AplayerConfigInterface } from "@/components/content/music/d.ts/config";

const musicContext: MusicContextInterface = {
    storageItemKey: "musicAnnounce",
    storageItemValue: "hello world",
    storageRangeKey: "musicRange",
    storageRangeValue: "hello world",
    lrcApiUrl: `${process.env.VITE_API_DOMAIN}/music/getMusicLrc?id=`,
    musicAnnounce: 
`<pre>
本站音乐均为站长多年来的珍藏，内容广泛。

基于站长理念，本页面的规则如下：
1. 仅向登录用户开放
2. 每位用户每天只能播放5首歌曲
3. 音乐随机播放，无法自选

如有侵权，请联系站长删除
</pre>`,
}

const aplayerConfig: AplayerConfigInterface = {
    fixed: false,
    mini: false,
    autoplay: false,
    theme: "rgba(64,161,193)",
    loop: "none",
    order: "list",
    preload: "auto",
    volume: 0.7,
    lrcType: 3,
    listFolded: true,
}

export { musicContext, aplayerConfig };