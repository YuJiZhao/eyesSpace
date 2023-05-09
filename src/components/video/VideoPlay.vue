<template>
  <div class="videoPlay">
    <div class="videoTitle">{{videoInfo.title}}</div>
    <div class="videoBody">
      <div class="playerCard">
        <div id="player"></div>
      </div>
      <div class="content">
        <n-card title="版权声明" hoverable class="card copyrightCard">
          <p>原视频地址: <a :href="videoInfo.originalUrl">网络链接</a></p>
          <p>原视频发布者: {{videoInfo.originalAuthor}}</p>
        </n-card>
        <n-card title="视频数据" hoverable class="card dataCard">
          <p><span>播放量: {{videoInfo.views}}</span> <span>点赞数: {{videoInfo.likes}}</span> <span>评论数: {{videoInfo.comments}}</span></p>
          <p>发布日期: {{videoInfo.createTime}}</p>
        </n-card>
        <n-card title="视频评论" hoverable class="card commentCard">{{videoInfo.ownerComment}}</n-card>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, reactive, ref } from "vue";
import { useMessage } from "naive-ui";
import $api from "@/server/api";
import Player from "xgplayer/dist/core_player";
import fullscreen from "xgplayer/dist/controls/fullscreen";
import loading from "xgplayer/dist/controls/loading";
import play from "xgplayer/dist/controls/play";
import poster from "xgplayer/dist/controls/poster";
import progress from "xgplayer/dist/controls/progress";
import time from "xgplayer/dist/controls/time";
import volume from "xgplayer/dist/controls/volume";
import flex from "xgplayer/dist/controls/flex";

export default {
  components: {},
  setup() {
    const message = useMessage();
    let videoInfo = ref({});

    async function getVideoInfo() {
      let id = sessionStorage.getItem("videoId") || 3;
      await $api.getVideoInfo({id}).then((res) => {
        if(res.code == 200) {
          videoInfo.value = res.data;
          initPlayer()
        } else {
          message.error("视频信息获取失败！错误信息：" + res.msg);
        }
      });
    }

    function initPlayer() {
      new Player({
        id: "player",
        url: videoInfo.value.videoUrl,
        poster: videoInfo.value.pictureUrl,
        width: 800,
        height: 450,
        controlPlugins: [
          fullscreen,
          loading,
          play,
          poster,
          progress,
          time,
          volume,
          flex,
        ],
      });
    }

    onMounted(() => {
      getVideoInfo();
    });
    return { videoInfo };
  },
};
</script>

<style scoped lang="scss">
.videoPlay {
  .videoTitle {
    font-size: 20px;
    font-weight: bold;
    margin-top: 10px;
    margin-bottom: 20px;
    margin-left: 30px;
  }
  .videoBody {
    display: flex;
    min-height: 500px;
    .playerCard {
      height: 450px;
      padding: 20px;
      margin-left: 10px;
      margin-bottom: -10px;
      border-radius: 10px;
      box-shadow: 0px 0px 10px #abc4c4;
    }
    .content {
      ::v-deep .n-card__content {
        margin-top: -10px;
      }
      .card {
        width: 320px;
        margin-bottom: 20px;
        margin-left: 20px;
      }
      .copyrightCard {
        max-height: 135px;
      }
      .dataCard p:first-child > span {
        display: inline-block;
        margin-right: 20px;
      }
      .commentCard {
        height: 185px;
        overflow-y: scroll;
      }
    }
  }
}
</style>
