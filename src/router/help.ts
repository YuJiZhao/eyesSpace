export declare interface MetaData {
    title: string,
    data: Array<any>;
}

const buildMeta = (title: string, keywords?: string, description?: string) => {
    return {
        title,
        data: [
            { keywords },
            { description }
        ]
    }
}

const writerMeta = (data: MetaData) => {
    const deleArr = [];
    let headTags = document.head.children;
    for(let i = 0; i < headTags.length; i++) {
        switch (headTags[i].tagName) {
            case "META":
                deleArr.push(headTags[i]);
                break;
            case "TITLE":
                document.title = data.title || document.title;
                break;
        }
    }

    deleArr.forEach(item => {
        document.head.removeChild(item);
    })

    // 全局添加定制meta
    const metas = document.createElement("META");
    const creatArr = [
        { charset: "utf-8" },
        { "http-equiv": "X-UA-Compatible", content: "IE=edge" },
        { name: "viewport", content: "width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no,minimal-ui" }
    ];

    // 局部meta和全局meta重复时，局部meta替换全局meta
    const tmpArr = data.data ? data.data.concat() : [];

    if (tmpArr.length > 0) {
        data.data.forEach((item, index) => {
            creatArr.forEach((ele, ind) => {
                if (Object.keys(item)[0] == Object.keys(ele)[0]) {
                    creatArr[ind] = JSON.parse(JSON.stringify(item));
                    tmpArr.splice(index, 1);
                }
            });
        });
    }

    const eleArr = creatArr.concat(tmpArr);
    const creatFrag = document.createDocumentFragment();
    eleArr.forEach(ele => {
        creatFrag.append(metas.cloneNode());
        Object.entries(ele).forEach(item => {
            // @ts-ignore
            creatFrag.lastChild.setAttribute(item[0], item[1]);
        });
    });

    document.head.prepend(creatFrag);
}

const clearMeta = () => {
    let headTags = document.head.children;
    for(let i = 0; i < headTags.length; i++) {
        if(headTags[i].tagName == "META")
        document.head.removeChild(headTags[i]);
    }
}

export { buildMeta, writerMeta, clearMeta };