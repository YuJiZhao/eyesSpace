<template>
  <div class="header">
    <div class="space" @click="pageJump('/')">{{ spaceName }}</div>
    <div class="left">
      <HeaderItem v-if="mpSwitch" />
      <div class="menuBar" :style="{backgroundImage: 'url(' + menuBar + ')' }" @click="openSideBar" v-else></div>
      <div class="avatar" @click="avatarClick" :style="{ backgroundImage: 'url(' + avatar + ')' }"></div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, onMounted, ref, watch } from "vue";
import { ContextInterface, WindowInterface, UserInterface, ProcessInterface } from "@/d.ts/plugin";
import { useRouter } from "vue-router";
import { siteConfig } from "@/config/program";
import { headerConfig, userCenterContext, siteContext } from "@/config/site";
import resource from "@/config/resource";
import jQuery from "jquery";
import useMouseWheel from "@/hooks/useMouseWheel";
import HeaderItem from "./components/HeaderItem.vue";

export default defineComponent({
  components: { HeaderItem },
  emits: ['openSideBar'],
  setup(prop, ctx) {
    const $context = inject<ContextInterface>("$context")!;
    const $user = inject<UserInterface>("$user")!;
    const $window = inject<WindowInterface>("$window")!;
    const $process = inject<ProcessInterface>("$process")!;
    const router = useRouter();

    let spaceName = ref($context.data.spaceName);
    let mpSwitch = ref(true);
    let lock = true; // 显示锁

    function pageJump(path: string) {
      router.push(path);
    }

    function openSideBar() {
      ctx.emit("openSideBar");
    }

    function avatarClick() {
      if($user.status) {
        window.open(`${userCenterContext.info}?clientId=${siteContext.clientId}`);
      } else {
        window.open(`${userCenterContext.auth}?clientId=${siteContext.clientId}&redirectUrl=${process.env.VITE_SITE_URL + userCenterContext.redirectUrl}`);
      }
    }

    watch(
      () => $window.width.value,
      (value) => {
        mpSwitch.value = value > siteConfig.mpThreshold;
      },
      { immediate: true }
    );

    watch(
      () => $window.scrollTop.value,
      (value) => {
        if(value == 0) {
          lock = true;
          jQuery(".header").css("top", "0px");
        }
      }
    );

    // 滚轮与滑动监听
    onMounted(() => {
      useMouseWheel(() => {
        if(lock) return;
        jQuery(".header").css("top", "0px");
        lock = true;
      }, () => {
        if(!lock || $process.headerCheckLock.value) return;
        jQuery(".header").css("top", "-60px");
        lock = false;
      });
    });

    return {
      spaceName,
      avatar: $user.data.avatar,
      headerConfig,
      menuBar: resource.menu,
      mpSwitch,
      pageJump,
      openSideBar,
      avatarClick,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.header {
  min-width: 370px;
  width: 100%;
  max-width: 2500px;
  margin: 0 auto;
  height: 60px;
  padding: 20px 40px 20px 40px;
  background: rgba($white, 0.7);
  color: $normal;
  transition: top 0.5s;
  position: fixed;
  z-index: 998;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  justify-content: space-between;
  .space {
    width: 140px;
    height: 20px;
    color: $title;
    line-height: 20px;
    font-size: 20px;
    cursor: pointer;
  }
  .left {
    display: flex;
    justify-content: space-between;
    .menuBar {
      width: 30px;
      height: 30px;
      background-size: 100% 100%;
      transform: translate(-10px, -5px);
      cursor: pointer;
    }
    .avatar {
      width: 40px;
      height: 40px;
      background-size: 100% 100%;
      border-radius: 50%;
      transform: translateY(-10px);
      box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
      -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
      -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
      cursor: pointer;
    }
  }
}

@media screen and (max-width: 800px) {
  .header {
    padding: 20px !important;
  }
}
</style>