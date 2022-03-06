<template>
    <div class="load" v-if="status"></div>
</template>

<script lang="ts">
/**
 * 页面跳转加载动画
 * @param Boolean loadStatus
 */
import { defineComponent, reactive } from 'vue';
export default defineComponent({
    props: {
        loadStatus: Boolean,
    },
    setup(props, { emit }) {
        const state = reactive({
            ...props
        });
        return {
            status: state.loadStatus
        };
    }
});
</script>

<style scoped>
#load {
}
</style>
