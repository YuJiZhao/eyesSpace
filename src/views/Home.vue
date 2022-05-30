<template>
  <div class="home">
    <button @click="jump1">jumpMusic</button>
    <button @click="jump2">jumpBlog</button>
    <button @click="open1">click</button>
    <button @click="open2">click</button>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, inject } from "vue";
import { useRouter } from "vue-router";
import { PopupType, CVType } from "@/d.ts/modules";
import { alertMsg, announcementMsg } from "@/config/websiteConfig";

export default defineComponent({
  components: {},
  setup() {
    const $router = useRouter();
    const $popup = inject<PopupType>("$popup")!;
    const $context = inject<CVType>("$context")!;

    function jump1() {
      $router.push("/music");
    }

    function jump2() {
      $router.push("/blog");
    }

    function open1() {
      $popup.announcementShow(announcementMsg.defaultAnnouncement);
    }

    function open2() {
      $popup.alertShow(alertMsg.copyAlert);
    }

    return {
      jump1,
      jump2,
      open1,
      open2,
    };
  },
});
</script>

<style lang="scss" scoped>
.home {
}
</style>
