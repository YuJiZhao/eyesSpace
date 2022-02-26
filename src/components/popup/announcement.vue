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
import { defineComponent, reactive } from "vue";
import $ from 'jquery';
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
@import url();

.announcement {
    position: fixed;
    position: 999;
    top: 2.6667rem;
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
        background: rgba(2, 218, 247);
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

@keyframes annAni {
    10% {
    }

    90% {

    }
}
</style>
