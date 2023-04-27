import { DataCardConfigInterface, BlogDetailConfigInterface } from "@/components/content/blogDetail/d.ts/config";

const blogDetailConfig: BlogDetailConfigInterface = {
    editorId: "eyesEditor"
}

const DataCardConfig: Array<DataCardConfigInterface> = [
    {
        title: "阅读量",
        name: "views"
    },
    {
        title: "评论量",
        name: "comments"
    },
    {
        title: "字数",
        name: "words"
    },
    {
        title: "发表日期",
        name: "createTime"
    },
]

export { blogDetailConfig, DataCardConfig };