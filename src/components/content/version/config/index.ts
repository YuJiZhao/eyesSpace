import { versionDataCardConfigInterface } from "@/components/content/version/d.ts/config";

const versionDataCardConfig: Array<versionDataCardConfigInterface> = [
    {
        title: "整站版本",
        name: "siteVersion"
    },
    {
        title: "前端版本",
        name: "frontendVersion"
    },
    {
        title: "后端版本",
        name: "backendVersion"
    },
    {
        title: "总版本数",
        name: "versionNum"
    }
];

const versionTypeConvert: Array<string> = ["整站", "前端", "后端"];

export { versionDataCardConfig, versionTypeConvert };