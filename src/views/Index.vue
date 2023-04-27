<template>
  <div class="index">
    <Header class="header" v-if="headerSwitch" @openSideBar="openSideBar" />
    <transition name="sideBarAnimate">
      <side-bar v-if="sideBarSwitch" @closeSideBar="closeSideBar" />
    </transition>
    <div class="content">
      <side-card class="cardGroup" v-if="sideCardSwitch" />
      <router-view v-slot="{ Component }">
        <keep-alive :include="keepAliveRoute">
          <component class="component" :is="Component" />
        </keep-alive>
      </router-view>
    </div>
    <Footer v-if="footerSwitch"/>
    <Tools />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, inject, onMounted } from "vue";
import { ProcessInterface, WindowInterface } from "@/d.ts/plugin";
import { Header, SideBar, SideCard, Footer, Tools } from "@/components/index";
import { siteConfig } from "@/config/program";

export default defineComponent({
  components: { Header, SideBar, SideCard, Footer, Tools },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $window = inject<WindowInterface>("$window")!;
    let sideBarSwitch = ref(false);

    function openSideBar() {
      sideBarSwitch.value = !sideBarSwitch.value;
      if (sideBarSwitch.value != true) return;
      $process.maskShow(closeSideBar);
    }

    function closeSideBar() {
      sideBarSwitch.value = false;
      $process.maskHide();
    }

    // DOM监听
    function DOMObserve() {
      let dom = document.querySelector("html")!;
      let win: any = window;
      let MutationObserver = win.MutationObserver || win.webkitMutationObserver || win.MozMutationObserver;
      let mutationObserver = new MutationObserver((mutations: any) => {
      	let height = document.querySelector("html")!.offsetHeight;
        // header形态监听
        $process.headerCheckSwitch($window.height.value, height);
        // footer形态更新
        $process.footerPositionSwitch($window.height.value, height);
      })
      mutationObserver.observe(dom, {
        childList: true,
      	attributes: true,
      	characterData: true,
      	subtree: false,
      	attributeOldValue: false,
      	characterDataOldValue: false
      })
    }

    onMounted(() => {
      DOMObserve();
    })

    return {
      keepAliveRoute: siteConfig.keepAliveRoute,
      headerSwitch: $process.headerStatus,
      sideBarSwitch,
      sideCardSwitch: $process.sideCardStatus,
      sideCardPosition: $process.sideCardPosition,
      footerSwitch: $process.footerStatus,
      openSideBar,
      closeSideBar
    };
  },
});
</script>

<style lang="scss" scoped>
.sideBarAnimate-enter-active {
  animation: fadeInRight 0.5s;
}
.sideBarAnimate-leave-active {
  animation: fadeOutRight 0.5s;
}

.index {
  .content {
    width: 1200px;
    margin: 0 auto;
    margin-top: 70px;
    display: flex;
    flex-direction: v-bind(sideCardPosition);
    justify-content: space-between;
    .cardGroup {
      width: 300px;
    }
    .component {
      flex: 1;
      min-width: 335px;
      margin: 0 20px;
      overflow: hidden;
    }
  }
}

@media screen and (max-width: 1240px) {
  .content {
    width: 1000px !important;
      .cardGroup {
        width: 290px !important;
      }
  }
}

@media screen and (max-width: 1040px) {
  .content {
    width: 800px !important;
    .cardGroup {
      width: 280px !important;
    }
  }
}

@media screen and (max-width: 800px) {
  .content {
    width: 100% !important;
    flex-direction: column-reverse !important;
    .cardGroup {
      width: 100% !important;
      min-width: 350px;
      margin-top: 20px;
    }
    .component {
      width: calc(100% - 40px) !important;
      margin: 0 auto !important;
    }
  }
}
</style>