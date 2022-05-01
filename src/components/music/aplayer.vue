<template>
  <div ref="playerRef" id="playerRef"></div>
</template>

<script lang="ts">
import APlayer from "APlayer";
import "APlayer/dist/APlayer.min.css";
import {
  defineComponent,
  onBeforeUnmount,
  onMounted,
  ref,
} from "vue";
import { aplayerConfig } from "@/config/appConfig"
import $ from "jquery";

export default defineComponent({
  components: {},
  setup() {
    let playerRef = ref();
    let instance: any;

    class Audio {
      artist: String;
      name: String;
      url: String;
      cover: String;
      lrc: String;

      constructor(
        artist: String,
        name: String,
        url: String,
        cover: String,
        lrc: String
      ) {
        this.artist = artist;
        this.name = name;
        this.url = url;
        this.cover = cover;
        this.lrc = lrc;
      }
    }

    // 初始化
    onMounted(() => {
      $.get(
        "https://api.i-meto.com/meting/api?server=netease&type=playlist&id=6936632013",
        function (data, status) {
          console.log("data: ", data);
          let audioList = data.map(
            (value: any) =>
              new Audio(
                value.author,
                value.title,
                value.url,
                value.pic,
                value.lrc
              )
          );

          instance = new APlayer({
            container: $("#playerRef"),
            audio: data,
            fixed: aplayerConfig.fixed,
            mini: aplayerConfig.mini,
            autoplay: aplayerConfig.autoplay,
            loop: aplayerConfig.loop,
            order: aplayerConfig.order,
            preload: aplayerConfig.preload,
            volume: aplayerConfig.volume,
            mutex: aplayerConfig.mutex,
            lrcType: aplayerConfig.lrcType
          });
        }
      );
    });

    // 销毁
    onBeforeUnmount(() => {
      instance.destroy();
    });

    return {};
  },
});
</script>

<style lang="scss" scoped>
</style>
