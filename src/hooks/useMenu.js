import { h } from "vue";
import { NIcon } from "naive-ui";
import {
    DiceSharp,
    LibrarySharp,
    DocumentText,
    ChatboxEllipses,
    MusicalNotesSharp,
    Film,
    Cellular,
    FileTrayFullSharp,
    ReceiptSharp,
    PersonSharp
} from "@vicons/ionicons5";

function useRenderIcon(icon) {
    return () => h(NIcon, null, { default: () => h(icon) });
}

function useMenuOptions() {
    return [
        {
            label: "主控中心",
            key: "",
            icon: useRenderIcon(DiceSharp)
        },
        {
            label: "内容管理",
            key: "content",
            icon: useRenderIcon(LibrarySharp),
            children: [
                {
                    label: "博客",
                    key: "content/blog",
                    icon: useRenderIcon(DocumentText),
                },
                {
                    label: "说说",
                    key: "content/shuoshuo",
                    icon: useRenderIcon(ChatboxEllipses),
                },
                {
                    label: "习题",
                    key: "content/exercises",
                    icon: useRenderIcon(ReceiptSharp),
                }
            ]
        },
        {
            label: "音乐管理",
            key: "music",
            icon: useRenderIcon(MusicalNotesSharp)
        },
        {
            label: "视频管理",
            key: "video",
            icon: useRenderIcon(Film)
        },
        {
            label: "用户管理",
            key: "user",
            icon: useRenderIcon(PersonSharp)
        },
        {
            label: "数据分析",
            key: "analysis",
            icon: useRenderIcon(Cellular)
        },
        {
            label: "系统日志",
            key: "log",
            icon: useRenderIcon(FileTrayFullSharp)
        },
    ];
}

export { useRenderIcon, useMenuOptions };