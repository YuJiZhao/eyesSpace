import { AplayerConfigType } from "@/d.ts/config";
import { AplayerSongServer, AplayerSongType, AplayerLoop, AplayerOrder, AplayerPreload, AplayerLrcType } from "@/constant/aplayerConstant";

/*******************
 * 外部应用相关配置 *
 *******************/

/**
 * aplayer配置
 */

const aplayerConfig: AplayerConfigType = {
    songServer: AplayerSongServer.Netease,
    songType: AplayerSongType.Playlist,
    songId: 6936632013,
    fixed: false,
    mini: false,
    autoplay: false,
    loop: AplayerLoop.All,
    order: AplayerOrder.Random,
    preload: AplayerPreload.Auto,
    volume: 0.7,
    mutex: true,
    lrcType: AplayerLrcType.Three
}

export { aplayerConfig };