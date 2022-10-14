<template>
  <div class="video">
    <div class="playBox">
      <div class="title">{{title}}</div>
      <Wait :show="show" :fail="isFail" color="#000" width="100%" height="400px" failColor="#000">
        <video-player />
      </Wait>
      <func-bar @openAnnounce="openAnnounce" />
    </div>
    <base-dialog v-if="isShowAnnounce" title="视频公告" @close="close">
      <div class="announce" v-html="announce"></div>
    </base-dialog>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, onActivated, onMounted, ref, watch } from 'vue';
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import useProcessControl from "@/hooks/useProcessControl";
import { VideoPlayer, FuncBar } from "@/components/content/video";
import { codeConfig } from "@/config/program";
import { videoContext } from "@/components/content/video/businessTs/videoContext";
import videoProcess from "@/components/content/video/businessTs/videoProcess";
import BaseDialog from "@/components/general/popup/dialogComponent/BaseDialog.vue";
import { videoContext as videoContextConfig } from "@/components/content/video/config";
import { Wait } from "@/components/general/popup";
import { videoSideCards } from "@/components/content/video/config";
import { writerMeta } from "@/router/help";
import { metaInfo } from "@/config/site";

export default defineComponent({
  name: "Video",
  components: { VideoPlayer, BaseDialog, Wait, FuncBar },
  beforeRouteEnter: () => {
    writerMeta(metaInfo.video);
  },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let title = ref("");
    let show = ref(true);
    let isFail = ref(false);
    let isShowAnnounce = ref(false);

    async function initData() {
      videoProcess.cardInitLoad.value = true;
      show.value = true;
      isFail.value = false;
      await $api.getVideoInfo().then(({code, msg, data}) => {
        if(code == codeConfig.success) {
          title.value = data.title;
          videoContext.init(data);
          videoProcess.cardInitLoad.value = false;
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
        videoProcess.cardInitFail.value = true;
      })
    }

    function controlAnnounce() {
      let isOpen = localStorage.getItem(videoContextConfig.storageItemKey);
      if(!isOpen) {
        localStorage.setItem(videoContextConfig.storageItemKey, videoContextConfig.storageItemValue);
        isShowAnnounce.value = true;
      }
    }

    function close() {
      isShowAnnounce.value = false;
    }

    function openAnnounce() {
      isShowAnnounce.value = true;
    }

    watch(
      () => videoProcess.videoEndSentry.value,
      () => initData()
    )

    onMounted(() => {
      controlAnnounce();
      initData();
    })

    onActivated(() => {
      useProcessControl(true, videoSideCards, false);
    })

    return {
      title,
      show,
      isFail,
      isShowAnnounce,
      announce: videoContextConfig.videoAnnounce,
      close,
      openAnnounce
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.video {
  width: 100%;
  padding: 0 !important;
  .playBox {
    width: 100%;
    max-width: 800px;
    margin: 0 auto;
    .title {
      font-size: 20px;
      height: 50px;
      line-height: 50px;
      color: $title;
    }
  }
  .announce {
    width: 320px;
    margin: 0 auto;
  }
}
</style>