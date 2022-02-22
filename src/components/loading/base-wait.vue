<template>
    <div class="wait" v-if="wait">
        <img src="../../assets/image/common/loading.png" width="30" class="loading" />
    </div>
</template>

<script lang="ts">
import { defineComponent, inject } from 'vue';
export default defineComponent({
    setup() {
        const wait = inject<Wait>('$wait')!;
        return {
            // wait: wait.status
            wait: false
        };
    }
});
</script>

<style lang="scss" scoped>
.wait {
    position: fixed;
    width: 100%;
    height: 100%;
    top: 0;
    z-index: 999999;
    display: flex;
    justify-content: center;
    align-items: center;
    background: rgba(0, 0, 0, 0.4);
    .loading {
        position: relative;
        display: inline-block;
        animation: loadani 2s infinite linear;
    }
}
@keyframes loadani {
    0% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(360deg);
    }
}
</style>
