/**
 * 外部应用相关配置
 */

/**
 * aplayer配置
 */
// BUG: 需要解决类型问题
const aplayerConfig: AplayerConfigType = {
    songServer: AplayerSongServerType.netease,
    songType: AplayerSongTypeType.playlist,
    songId: 6936632013,
    fixed: false,
    mini: false,
    autoplay: false,
    loop: AplayerLoopType.all,
    order: AplayerOrderType.random,
    preload: AplayerPreloadType.auto,
    volume: 0.7,
    mutex: true,
    lrcType: AplayerLrcTypeType.three
}

export { aplayerConfig };