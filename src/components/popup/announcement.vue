<template>
    <div class="announcement">
        <div class="line"></div>
        <div class="point"></div>
        <div class="content">
            <div class="title">{{title}}</div>
            <div class="value">{{value}}</div>
        </div>
    </div>
</template>

<script lang="ts">
/**
 * 公告组件
 * @desc 每个用户初次使用时打开一次，公告更新则刷新
 */
import { defineComponent, reactive } from "vue";
export default defineComponent({
    components: {},
    props: {
        title: String,
        value: String
    },
    setup(props, { emit }) {
        const state = reactive({
            ...props
        });
        return {
            ...state
        };
    }
});
</script>

<style lang="scss" scoped>
// @import url();
// TODO：实现响应式，实现色彩抽离

.announcement {
    position: fixed;
    position: 999;
    // top: -3.4667rem;
    top: 3.4667rem;
    left: 50%;
    transform: translateX(-50%);
    width: 6.6667rem;
    min-height: 2.6667rem;
    border-radius: .2667rem;
    background: rgb(175, 177, 176);
    .point {
        position: absolute;
        top: -1.2rem;
        left: 50%;
        transform: translateX(-50%);
        width: .2667rem;
        height: .2667rem;
        border-radius: 50%;
        background: #000;
    }
    .line {
        position: absolute;
        top: -.8rem;
        left: 50%;
        transform: translateX(-50%) rotate(45deg);
        width: 1.5467rem;
        height: 1.5467rem;
        background: none;
        border-top: .0267rem solid #000;
        border-left: .0267rem solid #000;
    }
    .content {
        padding: .2667rem;
        padding-top: .1333rem;
        .title {
            height: .8rem;
            text-align: center;
            line-height: .8rem;
            font-size: .5333rem;
        }
        .value {
        }
    }
}

.annAni {
    animation: annAni 5s;
}

@keyframes annAni {
    10% {
        top: 2.6667rem;
    }
    90% {
        top: 2.6667rem;
    }
    100% {
        top: -3.4667rem;
    }
}
</style>
