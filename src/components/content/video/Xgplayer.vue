<template>
    <div class="xgplayerBox">
      <div id="player"></div>
    </div>
</template>

<script lang="ts">
import { defineComponent, inject, onMounted, reactive, watch } from "vue";
import { ProcessInterface } from "@/d.ts/plugin";
import videoProcess from "@/components/content/video/businessTs/videoProcess";
import { videoContext } from "@/components/content/video/businessTs/videoContext";
import Player from "xgplayer/dist/core_player";
import fullscreen from "xgplayer/dist/controls/fullscreen";
import loading from "xgplayer/dist/controls/loading";
import play from "xgplayer/dist/controls/play";
import poster from "xgplayer/dist/controls/poster";
import usePageHidden from "@/hooks/usePageHidden";

export default defineComponent({
  components: { },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;

    let videoInfo = reactive({
      videoUrl: "",
      pictureUrl: ""
    });
    let player: any;

    function initPlayer() {
      player = new Player({
        id: "player",
        url: videoInfo.videoUrl,
        poster: videoInfo.pictureUrl,
        fluid: true,
        controls: false,
        controlPlugins: [ fullscreen, loading, play, poster ],
        errorTips: "视频加载失败"
      });

      player.on('ended', () => {
        videoProcess.videoVariable.value = true;
      })

      usePageHidden.addEvent(
        () => {
          if(sessionStorage.getItem("videoPaused") == "play") {
            player.play();
          }
          sessionStorage.removeItem("videoPaused");
        },
        () => {
          if(sessionStorage.getItem("videoPaused") == null) {
            sessionStorage.setItem("videoPaused", player.paused ? "suspend" : "play");
          }
          player.pause();
        }
      );
    }

    function initData() {
      videoInfo.videoUrl = videoContext.data.videoUrl!;
      videoInfo.pictureUrl = videoContext.data.pictureUrl!;
      initPlayer();
    }

    watch(
      () => videoProcess.fullscreenSentry.value,
      () => { player.getFullscreen(player.root); }
    )

    onMounted(() => {
      initData();
    })

    return {
      show: videoProcess.cardInitLoad,
      isFail: videoProcess.cardInitFail,
      videoInfo,
      initData
    };
  },
});
</script>

<style lang="scss" scoped>
.xgplayerBox {
  width: 100%;
  #player {
    width: 100%;
  }
}
</style>