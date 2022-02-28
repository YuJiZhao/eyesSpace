<template>
    <div class="mask"></div>
</template>

<script lang="ts">
/**
 * 遮罩组件
 * @param string color
 */
import { defineComponent, reactive } from "vue";
import $ from 'jquery';
export default defineComponent({
    components: {},
    props: {
        color: String
    },
    setup(props, { emit }) {
        const state = reactive({
            ...props
        });
        $(".mask").css("background", props.color);
        return {
            ...state
        };
    }
});
</script>

<style lang="scss" scoped>
// @import url();

.mask {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
}
</style>
