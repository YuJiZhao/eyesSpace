<template>
  <transition name="topAnimate">
    <div class="top" @click="goTop" v-if="show && showLock">
      <img :src="topIcon" />
    </div>
  </transition>
</template>

<script lang="ts">
import { defineComponent, watch, inject, ref } from "vue";
import { WindowInterface } from "@/d.ts/plugin";
import resource from "@/config/resource";
import { siteConfig } from "@/config/program";
import useGoTop from "@/hooks/useGoTop";

export default defineComponent({
  setup() {
    const $window = inject<WindowInterface>("$window")!;

    let show = ref(false);
    let showLock = ref(false);

    watch(
      () => $window.width.value,
      (value) => {
        showLock.value = value > siteConfig.mpThreshold;
      },
      { immediate: true }
    );

    watch(
      () => $window.scrollTop.value,
      (value) => {
        if(value > 400) show.value = true;
        if(value < 300) show.value = false;
      }
    )

    return {
      topIcon: resource.top,
      show,
      showLock,
      goTop: useGoTop,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.topAnimate-enter-active {
  animation: fadeIn 0.5s;
}
.topAnimate-leave-active {
  animation: fadeOut 0.5s;
}

.top {
  position: fixed;
  bottom: 20px;
  right: 20px;
  cursor: pointer;
  img {
    width: 30px;
  }
}
</style>