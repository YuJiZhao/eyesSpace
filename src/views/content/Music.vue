<template>
  <div class="music">
    <Wait :show="show" :fail="isFail" height="400px">
      <music-player />
    </Wait>
    <func-btn />
    <base-dialog v-if="isShowAnnounce" title="音乐公告" @close="close">
      <div class="announce" v-html="announce"></div>
    </base-dialog>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, onActivated, onMounted, ref, watch } from 'vue';
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { MusicPlayer, FuncBtn } from "@/components/content/music";
import useProcessControl from "@/hooks/useProcessControl";
import { musicContext } from "@/components/content/music/businessTs/musicContext";
import BaseDialog from "@/components/general/popup/dialogComponent/BaseDialog.vue";
import { musicContext as musicContextConfig } from "@/components/content/music/config";
import { codeConfig } from "@/config/program";
import { Wait } from "@/components/general/popup";
import musicProcess from "@/components/content/music/businessTs/musicProcess";
import { writerMeta } from "@/router/help";
import { metaInfo } from "@/config/site";

export default defineComponent({
  name: "Music",
  components: { MusicPlayer, FuncBtn, BaseDialog, Wait },
  beforeRouteEnter: () => {
    writerMeta(metaInfo.music);
  },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let show = ref(true);
    let isFail = ref(false);
    let isShowAnnounce = ref(false);

    function close() {
      isShowAnnounce.value = false;
    }

    function controlAnnounce() {
      let isOpen = localStorage.getItem(musicContextConfig.storageItemKey);
      if(!isOpen) {
        localStorage.setItem(musicContextConfig.storageItemKey, musicContextConfig.storageItemValue);
        isShowAnnounce.value = true;
      }
    }

    async function initData() {
      show.value = true;
      isFail.value = false;
      await $api.getMusicInfo().then(({code, msg, data}) => {
        if(code == codeConfig.success) {
          musicContext.init(data);
          musicProcess.initNotice();
          show.value = false;
          return;
        } else if(code == codeConfig.authentication_error) {
          $process.alertShow({
            title: "视频获取失败",
            content: "视频功能仅对注册用户开放，请先注册或登录"
          });
        } else {
          $process.tipShow.error(msg);
        }
        isFail.value = true;
      })
    }

    watch(
      () => musicProcess.musicEndSentry.value,
      () => initData()
    )

    onMounted(() => {
      controlAnnounce();
      initData();
    })

    onActivated(() => {
      useProcessControl(true, false, false);
    })

    return {
      show,
      isFail,
      isShowAnnounce,
      announce: musicContextConfig.musicAnnounce,
      close
    };
  },
});
</script>

<style lang="scss" scoped>
.music {
  width: 100%;
}
</style>