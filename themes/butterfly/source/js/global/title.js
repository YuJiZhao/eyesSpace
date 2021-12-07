var originalTitle = document.title;
document.addEventListener("visibilitychange", function () {
    if (document.hidden) {
        // cheat
        document.title = '啊呀！≥﹏≤页面崩溃啦！';
    } else {
        // back
        document.title = '咦！(￣▽￣)~*又好了！';
        setTimeout(function () {
            if(!document.hidden){
                document.title = originalTitle;
            }
        }, 1500)
    }
});