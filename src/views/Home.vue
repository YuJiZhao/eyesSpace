<template>
  <div class="home">
    <button @click="jump">jump</button>
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

    function jump() {
      $router.push("/music");
    }

    function open1() {
      $popup.announcementShow(announcementMsg.defaultAnnouncement);
    }

    function open2() {
      $popup.alertShow(alertMsg.copyAlert);
    }

    return {
      jump,
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
