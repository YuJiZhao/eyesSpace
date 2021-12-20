let loc = location.href;
let reg_shuoshuo = /artitalk/g;
if (reg_shuoshuo.test(loc)) {
    console.log("0");
    // 修正标题与封面
    let clear1 = setInterval(() => {
        if ($("#site-title").length) {
            $("#site-title").text("说说")
            $("#page-header").css("background-image", "url(https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/cover/54.jpg)")
            $("#footer").css("background-image", "url(https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/cover/54.jpg)")
            clearInterval(clear1);
            // console.log("1");
        }
    }, 100)
    // 隐藏登录发布说说按钮
    let clear2 = setInterval(() => {
        if ($(".power").length) {
            $(".power").css("display", "none");
            clearInterval(clear2);
            // console.log("2")
        }
    }, 500)
}