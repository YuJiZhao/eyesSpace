<template>
  <div class="sideBar">
    <owner-card class="ownerCard" />
    <div class="navBox">
      <div class="navItem" v-for="item in headerConfig" :key="item.path" @click="pageJump(item.path)">
        <img :src="item.icon" />
        <div class="word">{{ item.word }}</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, watch } from "vue";
import { WindowInterface } from "@/d.ts/plugin";
import { OwnerCard } from "@/components/general/card";
import { useRouter } from "vue-router";
import { siteConfig } from "@/config/program";
import { headerConfig } from "@/config/site";

export default defineComponent({
  emits: ["closeSideBar"],
  components: { OwnerCard },
  setup(prop, ctx) {
    const $router = useRouter();
    const $window = inject<WindowInterface>("$window")!;

    function pageJump(path: string) {
      $router.push(path);
      ctx.emit("closeSideBar");
    }

    watch(
      () => $window.width.value,
      (value) => {
        if (value > siteConfig.mpThreshold) {
          ctx.emit("closeSideBar");
        }
      }
    );

    return {
      headerConfig,
      pageJump
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.sideBar {
  position: fixed;
  z-index: 1000;
  top: 0;
  right: 0;
  width: 300px;
  height: 100vh;
  background: $white;
  padding: 0 20px;
  .ownerCard {
    margin: 20px auto;
  }
  .navBox {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    .navItem {
      box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
      -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
      -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
      display: flex;
      justify-content: center;
      align-items: center;
      width: 80px;
      height: 50px;
      border-radius: 10px;
      margin-bottom: 10px;
      cursor: pointer;
      img {
        display: block;
        width: 25px;
        height: 25px;
        margin-right: 5px;
      }
      .word {
        font-size: 15px;
        text-align: center;
      }
    }
  }
}
</style>