<template>
  <div class="aplayerBox">
    <div class="mainPage" ref="playerRef"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, inject, onMounted, onDeactivated, onActivated, watch } from "vue";
import "@/libs/aplayer/APlayer.min.js";
import "@/libs/aplayer/APlayer.min.css";
import { musicContext } from "@/components/content/music/businessTs/musicContext";
import { musicContext as musicContextConfig, aplayerConfig } from "@/components/content/music/config";
import musicProcess from "@/components/content/music/businessTs/musicProcess";
import usePageHidden from "@/hooks/usePageHidden";

export default defineComponent({
  components: {},
  setup() {
    const playerRef = ref();
    let player: any;
    let lock = ref(true);

    function initData() {
      // @ts-ignore
      player = new APlayer({
        container: playerRef.value,
        ...aplayerConfig,
        audio: [
          {
            artist: musicContext.data.author,
            name: musicContext.data.title,
            url: musicContext.data.url,
            cover: musicContext.data.pic,
            lrc: musicContextConfig.lrcApiUrl + encodeURIComponent(musicContext.data.id!),
          }
        ],
      });

      player.on("ended", () => {
        musicProcess.doChangeMusic();
      })

      usePageHidden.addEvent(
        () => {
          if(sessionStorage.getItem("musicPaused") == "play") {
            player.play();
          }
          sessionStorage.removeItem("musicPaused");
        }, 
        () => {
          if(sessionStorage.getItem("musicPaused") == null) {
            sessionStorage.setItem("musicPaused", player.audio.paused ? "suspend" : "play");
          }
          player.pause();
        }
      );
    }

    watch(
      () => musicProcess.musicRangeSentry.value,
      () => {
        lock.value = localStorage.getItem("musicRange") ? false : true;
      },
      { immediate: true }
    )

    onMounted(() => {
      initData();
    });

    onActivated(() => {
      if(lock.value) {
        player.play();
      }
    })

    onDeactivated(() => {
      if(lock.value) {
        player.pause();
      }
    })

    return {
      playerRef,
    };
  },
});
</script>

<style lang="scss" scoped>
.aplayerBox {
  .mainPage {
    width: 1000px;
    background: none;
    position: fixed;
    left: 50%;
    bottom: 10%;
    transform: translateX(-50%);
    overflow: visible;
    font-family: eyes !important;
  }
  :deep(.aplayer-lrc) {
    width: 80%;
    height: 450px;
    position: absolute;
    top: -450px;
    left: 50%;
    transform: translateX(-50%);
    .aplayer-lrc-contents {
      margin-top: 250px;
      p {
        font-size: 16px;
        color: rgb(64, 161, 193);
        margin-bottom: 16px !important;
      }
    }
  }
}

@media screen and (max-width: 1100px) {
  .mainPage {
    width: 800px !important;
  }
}

@media screen and (max-width: 800px) {
  .mainPage {
    width: 100% !important;
    min-width: 350px;
    left: 0 !important;
    bottom: -2px !important;
    transform: translateX(0) !important;
    margin: 0 !important;
  }
}
</style>