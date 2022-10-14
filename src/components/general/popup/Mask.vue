<template>
  <transition name="maskAnimate">
    <div class="mask" v-show="status" @click="clickMask"></div>
  </transition>
</template>

<script lang="ts">
import { defineComponent, inject, ref, watch } from "vue";
import { ProcessInterface } from "@/d.ts/modules/process";

export default defineComponent({
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    let status = ref(false);
    let clickMask = ref(() => {});

    watch(
      () => $process.maskStatus.value,
      (value) => {
        status.value = value;
        if(value) {
          clickMask.value = $process.maskClickFunc.value;
        }
      }
    );

    return {
      status,
      clickMask
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.maskAnimate-enter-active {
  animation: fadeIn 0.5s;
}
.maskAnimate-leave-active {
  animation: fadeOut 0.5s;
}

.mask {
    position: fixed;
    z-index: 999;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: rgba($black, 0.5);
}
</style>