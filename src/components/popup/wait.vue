<template>
    <div class="wait" v-if="status"></div>
</template>

<script lang="ts">
/**
 * 请求等待动画
 * @param Boolean loadStatus
 */
import { defineComponent, reactive } from "vue";
export default defineComponent({
    props: {
        waitStatus: Boolean
    },
    setup(props, { emit }) {
        const state = reactive({
            ...props
        });
        return {
            status: state.waitStatus
        };
    },
});
</script>

<style lang="scss" scoped>
.wait {
}
</style>
