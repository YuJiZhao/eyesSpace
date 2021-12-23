// 听歌模式  0为随机模式， 1为点歌模式
let keyMode = 0;

// 获得搜索数据
let getData = value => {
    $.post(
        "https://www.sinsyth.com/yyjx/",
        {
            input: value,
            filter: "name",
            type: "netease",
            page: "1"
        },
        data => {
            console.log("data:", data);
        }
    )
}

// 节流封装
let throttle = (fn,delay) => {
    let timer;
    return function(){
        if(timer) return;
        timer = setTimeout(() => {
            fn();
            timer = null;
        }, delay)
    }
}

// 查看公告
let key1 = 0;
$(".announce").click(() => {
    if (key1 == 0) {
        $(".announce").css({
            "transform": "translateX(230px)",
        })
        $(".choose").css({
            "transform": "translateX(-120%)"
        })
        $(".random").css({
            "transform": "translateX(-120%)"
        })
        $(".myannounce").css({
            "transform": "translateX(10px)"
        })
        key1 = 1;
    } else {
        if (key1 == 1) {
            $(".announce").css({
                "transform": "translateX(0)"
            })
            $(".choose").css({
                "transform": "translateX(0)"
            })
            $(".random").css({
                "transform": "translateX(0)"
            })
            $(".myannounce").css({
                "transform": "translateX(-100%)"
            })
            key1 = 0;
        }
    }
})

// 拉出搜索框
let key2 = 0;
$(".choose").click(() => {
    if (key2 == 0) {
        $(".announce").css({
            "transform": "translateX(-120%)"
        })
        $(".choose").css({
            "transform": "translateX(230px)"
        })
        $(".random").css({
            "transform": "translateX(-120%)",
        })
        $(".mysearch").css({
            "transform": "translateX(10px)"
        })
        key2 = 1;
    } else {
        if (key2 == 1) {
            $(".announce").css({
                "transform": "translateX(0)"
            })
            $(".choose").css({
                "transform": "translateX(0)"
            })
            $(".random").css({
                "transform": "translateX(0)"
            })
            $(".mysearch").css({
                "transform": "translateX(-100%)"
            })
            key2 = 0;
        }
    }
})

// 拉出随机栏
let key3 = 0;
$(".random").click(() => {
    if (key3 == 0) {
        $(".announce").css({
            "transform": "translateX(-120%)",
        })
        $(".choose").css({
            "transform": "translateX(-120%)",
        })
        $(".random").css({
            "transform": "translateX(120px)",
        })
        $(".myrandom").css({
            "transform": "translateX(10px)"
        })
        key3 = 1;
    } else {
        if (key3 == 1) {
            $(".announce").css({
                "transform": "translateX(0)"
            })
            $(".choose").css({
                "transform": "translateX(0)"
            })
            $(".random").css({
                "transform": "translateX(0)"
            })
            $(".myrandom").css({
                "transform": "translateX(-120%)"
            })
            key3 = 0;
        }
    }
})

// 搜索歌曲&&切换搜索模式
$(".mysearch input").keydown(e => {
    let theEvent = e || window.event;
    let code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13 && key2 == 1) {
        throttle(getData($("input").val()), 1000)
        // 切换为点歌模式
        keyMode = 1;
        $(".choose").css("background-image", "linear-gradient(#6eeed2, #00c3ff)");
        $(".random").css("background-image", "linear-gradient(#b39cff, #8e74ff)");
        $(".myrandom").css("cursor", "pointer");
    }
})

// 切换随机模式
$(".myrandom").click(() => {
    if (keyMode == 0) {
        return false;
    } else {
        keyMode = 0;
        $(".random").css("background-image", "linear-gradient(#6eeed2, #00c3ff)");
        $(".choose").css("background-image", "linear-gradient(#b39cff, #8e74ff)");
        $(".myrandom").css("cursor", "none");
    }
});