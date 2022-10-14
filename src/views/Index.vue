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
    <Top />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, inject } from "vue";
import { ProcessInterface } from "@/d.ts/plugin";
import { Header, SideBar, SideCard, Footer, Top } from "@/components/index";
import { siteConfig } from "@/config/program";

export default defineComponent({
  components: { Header, SideBar, SideCard, Footer, Top },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
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