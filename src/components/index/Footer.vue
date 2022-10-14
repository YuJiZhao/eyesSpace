<template>
  <div class="footer" :class="{ static: positionSwitch, fixed: !positionSwitch }">
    <div>{{footerConfig.copyright}}</div>
    <div><span class="theme" @click="jumpPage(warehouseUrl)">主题 {{footerConfig.theme}} | {{footerConfig.techStack}}</span></div>
    <div><span class="zwfw" @click="jumpPage(zwfwUrl)">{{footerConfig.zwfwCode}}</span></div>
  </div>
</template>

<script lang="ts">
import { defineComponent, watch, inject } from 'vue';
import { WindowInterface, ProcessInterface } from "@/d.ts/plugin";
import { footerConfig } from "@/config/site";
import { urlConfig } from "@/config/program";

export default defineComponent({
  setup() {
    const $window = inject<WindowInterface>("$window")!;
    const $process = inject<ProcessInterface>("$process")!;

    function jumpPage(path: string) {
      window.open(path);
    }

    watch(
      () => document.querySelector("html")!.offsetHeight,
      (value) => $process.footerPositionSwitch(value)
    )

    watch(
      () => $window.height.value,
      (value) => $process.footerPositionSwitch(value),
      { immediate: true }
    )

    return {
      footerConfig,
      positionSwitch: $process.footerPosition,
      warehouseUrl: urlConfig.warehouseUrl,
      zwfwUrl: urlConfig.zwfwUrl,
      jumpPage
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.footer {
  width: 100%;
  height: 100px;
  background: rgba($color: $white, $alpha: 0.7);
  color: $normal;
  display: flex;
  flex-direction: column;
  justify-content: center;
  div {
    height: 25px;
    line-height: 25px;
  }
  .theme, .zwfw {
    cursor: pointer;
  }
}

.fixed {
  position: fixed;
  bottom: 0;
  text-align: center;
}

.static {
  align-items: center;
}
</style>