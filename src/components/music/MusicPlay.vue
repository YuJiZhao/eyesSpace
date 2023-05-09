<template>
  <div class="musicPlay">
    <div class="mainPage" ref="playerRef"></div>
  </div>
</template>

<script>
import { onMounted, ref } from "vue";
import { useMessage } from "naive-ui";
import "@/static/css/APlayer.min.css";
import APlayer from "@/static/js/APlayer.min.js";
import $api from "@/server/api";

export default {
  components: {},
  setup() {
    const message = useMessage();
    const playerRef = ref();

    onMounted(() => {
      let id = sessionStorage.getItem("musicId") || 4;
      $api.getMusicInfo({ id }).then((res) => {
        console.log(res);
        if (res.code != 200) {
          message.error("获取音乐信息失败！错误信息：" + res.msg);
          return;
        }
        let audioList = [
          {
            artist: res.data.author,
            name: res.data.title,
            url: res.data.url,
            cover: res.data.pic,
            lrc: "/api/music/music/getMusicLrc?id=" + id,
          },
        ];
        new APlayer({
          container: playerRef.value,
          fixed: false,
          mini: false,
          autoplay: false,
          theme: "rgba(64,161,193)",
          loop: "all",
          order: "list",
          preload: "auto",
          volume: 0.7,
          lrcType: 3,
          listFolded: true,
          audio: audioList,
        });
      });
    });

    return { playerRef };
  },
};
</script>

<style scoped lang="scss">
.musicPlay {
}
</style>
