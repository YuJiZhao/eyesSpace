<template>
  <transition-group name="toolAnimate">
    <div class="tool" 
      v-for="item in funcItem" :key="item.index" 
      @click="item.clickFunc"
      :style="{bottom: item.index * 50 + 30 + 'px'}"
      v-show="show && showLock"
    >
      <img :src="item.icon" />
    </div>
  </transition-group>
  <div class="tool toolBox" @click="showLock = !showLock" v-if="show">
    <img :src="toolBoxIcon" />
  </div>
</template>

<script lang="ts">
import { defineComponent, watch, inject, ref } from "vue";
import { WindowInterface, ProcessInterface } from "@/d.ts/plugin";
import resource from "@/config/resource";
import { siteConfig } from "@/config/program";
import { goBoth, GoBothType } from "@/hooks/useGoBoth";
import { CardDirection } from "@/constant";

export default defineComponent({
  setup() {
    const $window = inject<WindowInterface>("$window")!;
    const $process = inject<ProcessInterface>("$process")!;

    let show = ref(false);
    let showLock = ref(false);

    let funcItem = [
      {
        index: 4,
        icon: resource.top,
        clickFunc: () => goBoth(
          [GoBothType.TopSpeed, GoBothType.TopTime][$process.rollType.value],
          $process.rollTime.value
        )
      },
      {
        index: 3,
        icon: resource.showBar,
        clickFunc: () => {
          if(!localStorage.getItem(siteConfig.sideBarShowStorage)) {
            localStorage.setItem(siteConfig.sideBarShowStorage, $process.sideCardStatus.value ? "show" : "hide")
          };
          if(localStorage.getItem(siteConfig.sideBarShowStorage) == "hide") {
            $process.tipShow.warn("当前页面不支持关闭侧栏");
            return;
          }
          $process.sideCardStatus.value = !$process.sideCardStatus.value;
        }
      },
      {
        index: 2,
        icon: resource.switchBar,
        clickFunc: () => {
          if(!$process.sideCardStatus.value) {
            $process.tipShow.warn("当前页面不支持切换侧栏");
            return;
          }
          $process.sideCardPosition.value = $process.sideCardPosition.value == CardDirection.row ? CardDirection.reverse : CardDirection.row;
        }
      },
      {
        index: 1,
        icon: resource.bottom,
        clickFunc: () => goBoth(
          [GoBothType.BottomSpeed, GoBothType.BottomTime][$process.rollType.value],
          $process.rollTime.value
        )
      }
    ];

    watch(
      () => $window.width.value,
      (value) => {
        show.value = value > siteConfig.mpThreshold;
      },
      { immediate: true }
    );

    return {
      toolBoxIcon: resource.toolBox,
      show,
      showLock,
      funcItem
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.toolAnimate-enter-active {
  animation: fadeIn 0.2s;
}
.toolAnimate-leave-active {
  animation: fadeOut 0.2s;
}

.tool {
  width: 35px;
  height: 35px;
  border-radius: 50%;
  box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  position: fixed;
  right: 20px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  img {
    width: 25px;
    display: block;
  }
}

.toolBox {
  bottom: 30px;
}
</style>