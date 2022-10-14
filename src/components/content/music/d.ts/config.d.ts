export declare interface MusicContextInterface {
    storageItemKey: string;
    storageItemValue: string;
    lrcApiUrl: string;
    musicAnnounce: string;
    storageRangeKey: string;
    storageRangeValue: string;
}

export declare interface AplayerConfigInterface {
    fixed: boolean;
    mini: boolean;
    autoplay: boolean;
    theme: string;
    loop: "all" | "one" | "none";
    order: "list" | "random";
    preload: "none" | "metadata" | "auto";
    volume: number;
    lrcType: 0 | 3;
    listFolded: boolean;
}