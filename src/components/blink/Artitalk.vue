<template>
    <div id="artitalk_main" />
</template>

<script>
export default {
    mounted() {
        function addScript(url) {
            let s = document.createElement("script");
            s.id = "at";
            url.indexOf("appId") == -1 ? (s.src = url) : (s.innerHTML = url);
            document.head.appendChild(s);
        }
    // TODO: 需要抽离相关参数进配置文件
    addScript(`
        new Artitalk({
            appId: 'ogP8qj3veMh0LFpFWMPOyF0X-MdYXbMMI',
            appKey: 'nHXLd3N3Jgh460t2iRQKWAtr',
            shuoPla: 'Demo页密码：123456',
            bgImg: 'https://cdn.jsdelivr.net/gh/drew233/cdn/20200409110727.webp',
            atEmoji: {
                huaji: 'https://cdn.jsdelivr.net/gh/moezx/cdn@3.1.9/img/Sakura/images/smilies/icon_huaji.gif',
                baiyan: 'https://cdn.jsdelivr.net/gh/Artitalk/Artitalk-emoji/baiyan.png',
                bishi: 'https://cdn.jsdelivr.net/gh/Artitalk/Artitalk-emoji/bishi.png',
                bizui: 'https://cdn.jsdelivr.net/gh/Artitalk/Artitalk-emoji/bizui.png',
                chan: 'https://cdn.jsdelivr.net/gh/Artitalk/Artitalk-emoji/chan.png'          
            },
        })
    `);
    },
    destroyed() {
        document
            .querySelectorAll("#at")
            .forEach(element => element.parentNode.removeChild(element));
        delete window.AV;
    }
};
</script>