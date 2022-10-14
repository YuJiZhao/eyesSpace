import { 
    HeaderConfigType, 
    SiteDataType, 
    FooterConfigInterface, 
    ErrorPathInterface, 
    ErrorConfigInterface, 
    SiteContextInterface,
    ContactMeConfigType
} from "@/d.ts/config/site";
import resource from "./resource";
import { pathConfig } from "@/config/program";

/*
 ***************************************************************************************
 *                                    site
 ***************************************************************************************
 */
const siteContext: SiteContextInterface = {
    siteName: "瞳孔的个人空间",
    siteNameEn: "eyesSpace",
    spaceVersion: "3.0.0",
    ownerEmail: "eyesyeager@gmail.com",
    siteVideoBV: "BV1Fb4y1n7zq",
    commentMaxLen: 1000
};

/*
 ***************************************************************************************
 *                                    meta
 ***************************************************************************************
 */
const metaInfo = {
    home: {
        title: siteContext.siteName + " | 首页",
        data: [
            { keywords: "瞳孔的个人空间，个人网站，瞳孔，eyes，eyesyeager，程序员，博客，说说，音乐，视频" },
            { description: "这是瞳孔的个人空间，是一个个人网站" }
        ]
    },
    blog: {
        title: siteContext.siteName + " | 博客",
        data: [
            { keywords: "瞳孔的个人空间，瞳孔，eyes，eyesyeager，程序员，博客，Java，JavaScript，Flutter，Python，PHP，Unity" },
            { description: "这是瞳孔的个人空间，是一个个人网站，这是博客页面" }
        ]
    },
    shuoshuo: {
        title: siteContext.siteName + " | 说说",
        data: [
            { keywords: "瞳孔的个人空间，瞳孔，eyes，eyesyeager，生活，代码，程序员，音乐，视频" },
            { description: "这是瞳孔的个人空间，是一个个人网站，这是说说页面" }
        ]
    },
    music: {
        title: siteContext.siteName + " | 音乐",
        data: [
            { keywords: "瞳孔的个人空间，瞳孔，eyes，eyesyeager，音乐" },
            { description: "这是瞳孔的个人空间，是一个个人网站，这是音乐页面" }
        ]
    },
    video: {
        title: siteContext.siteName + " | 视频",
        data: [
            { keywords: "瞳孔的个人空间，瞳孔，eyes，eyesyeager，视频" },
            { description: "这是瞳孔的个人空间，是一个个人网站，这是视频页面" }
        ]
    },
    about: {
        title: siteContext.siteName + " | 关于",
        data: [
            { keywords: "瞳孔的个人空间，瞳孔，eyes，eyesyeager，生活，代码，程序员，音乐，视频" },
            { description: "这是瞳孔的个人空间，是一个个人网站，这是关于页面，一个懒得动弹的宅男" }
        ]
    },
    login: {
        title: siteContext.siteName + " | 登录",
        data: [
            { keywords: "瞳孔的个人空间，个人网站，瞳孔，eyes，eyesyeager，程序员，博客，说说，音乐，视频，登录" },
            { description: "这是瞳孔的个人空间，是一个个人网站，这是登录页面" }
        ]
    },
}

/*
 ***************************************************************************************
 *                                    popup
 ***************************************************************************************
 */

// 轻提示图标配置
const tipType: Array<string> = [
    resource.success,
    resource.info,
    resource.warn,
    resource.error
];

// 联系站长弹窗配置
const contactMeConfig: ContactMeConfigType = [
    {
        title: "可以发邮件",
        content: siteContext.ownerEmail,
        btnIcon: resource.copy,
        btnWord: "点击复制",
        clickFunc: "copy"
    },
    {
        title: "也可以去B站",
        content: siteContext.siteVideoBV,
        btnIcon: resource.bilibili,
        btnWord: "点击前往",
        clickFunc: "goBilibli"
    },
]

/*
 ***************************************************************************************
 *                                    header
 ***************************************************************************************
 */
const headerConfig: HeaderConfigType = [
    {
        path: pathConfig.home,
        icon: resource.home,
        word: "主页"
    },
    {
        path: pathConfig.blog,
        icon: resource.blog,
        word: "博客"
    },
    {
        path: pathConfig.shuoshuo,
        icon: resource.shuoshuo,
        word: "说说"
    },
    {
        path: pathConfig.music,
        icon: resource.music,
        word: "音乐"
    },
    {
        path: pathConfig.video,
        icon: resource.video,
        word: "视频"
    },
    {
        path: pathConfig.about,
        icon: resource.about,
        word: "关于",
    }
];

/*
 ***************************************************************************************
 *                                    Card
 ***************************************************************************************
 */
const siteData: SiteDataType = [
    {
        key: 0,
        name: "本站访问量"
    },
    {
        key: 1,
        name: "本站总访客"
    },
    {
        key: 2,
        name: "本站用户数"
    },
    {
        key: 3,
        name: "本站运行时间"
    }
]

/*
 ***************************************************************************************
 *                                    footer
 ***************************************************************************************
 */
const footerConfig: FooterConfigInterface = {
    copyright: "©2022 By 瞳孔",
    theme: "eyes",
    techStack: "vue+springcloud",
    zwfwCode: "赣ICP备2022006255号"
}

/*
 ***************************************************************************************
 *                                    preload
 ***************************************************************************************
 */
const preloadList: Array<string> = [
    resource.loading
]

/*
 ***************************************************************************************
 *                                    error
 ***************************************************************************************
 */
const errorPath: ErrorPathInterface = pathConfig.errorPath;

const errorConfig: ErrorConfigInterface = {
    errorRoute: {
        title: "未找到该页面",
        items: [
            {
                icon: resource.before,
                word: "返回前页",
                clickFunc: "goBack"
            },
            {
                icon: resource.home,
                word: "返回首页",
                clickFunc: "goIndex"
            }
        ],
        process: []
    },
    errorContext: {
        title: "服务器好像宕机了 <br> 请稍后重试，或者联系站长",
        items: [
            {
                icon: resource.refresh,
                word: "刷新页面",
                clickFunc: "refreshPage"
            },
            {
                icon: resource.email,
                word: "联系站长",
                clickFunc: "contactMe"
            }
        ],
        process: [false]
    }
}

export { siteContext, metaInfo, tipType, contactMeConfig, headerConfig, siteData, footerConfig, preloadList, errorPath, errorConfig };