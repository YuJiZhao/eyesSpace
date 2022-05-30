enum AplayerSongServer { 
    Netease = "netease", 
    Tencent = "tencent", 
    Kugou = "kugou", 
    Xiami = "xiami",
    Baidu = "baidu"
}

enum AplayerSongType { 
    Song = "song", 
    Playlist = "playlist", 
    Album = "album", 
    Search = "search", 
    Artist = "artist" 
}

enum AplayerLoop { 
    All = "all", 
    One = "one", 
    None = "none" 
}

enum AplayerOrder { 
    List = "list", 
    Random = "random",
}

enum AplayerPreload { 
    Auto = "auto", 
    Metadata = "metadata", 
    None = "none" 
}

enum AplayerLrcType { Zero, One, Two, Three }

export { AplayerSongServer, AplayerSongType, AplayerLoop, AplayerOrder, AplayerPreload, AplayerLrcType };