<template>
  <standard-card title="视频数据" :icon="icon">
    <Wait :show="show" :fail="isFail" height="100px" @callback="initData">
      <div class="infoCard">
        <ul>
          <li v-for="item in info" :key="item.index">
            <div>{{item.title}}</div>
            <div>{{item.value}}</div>
          </li>
        </ul>
      </div>
    </Wait>
  </standard-card>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, watch } from "vue";
import Resource from "@/config/resource";
import StandardCard from "@/components/general/card/components/StandardCard.vue";
import { Wait } from "@/components/general/popup";
import videoProcess from "@/components/content/video/businessTs/videoProcess";
import { videoContext } from "@/components/content/video/businessTs/videoContext";
import { videoContext as videoContextConfig } from "@/components/content/video/config";

export default defineComponent({
  components: { StandardCard, Wait },
  setup() {
    let info = ref<Array<infoType>>([]);
    const indexList = ["views", "likes", "comments", "createTime"];
    const titleList = ["播放量", "点赞量", "评论数", "发布时间"];
    
    // 构建数据结构
    type infoType = {
      index: string;
      title: string;
      value: string | number;
    }
    function buildInfoStructure(index: Array<string>, title: Array<string>, value: Array<string | number>): Array<infoType> {
      let info: Array<infoType> = [];
      index.forEach((v, i) => {
        info.push({
          index: index[i],
          title: title[i],
          value: value[i]
        });
      })
      return info;
    }

    // 初始化数据
    function initData() {
      info.value = buildInfoStructure(indexList, titleList, [
        videoContext.data.views!,
        videoContext.data.likes!,
        videoContext.data.comments!,
        videoContext.data.createTime!
      ]);
    }

    watch(
      () => videoProcess.likesNumSentry.value,
      () => {
        info.value[1].value = <number>info.value[1].value + Number(sessionStorage.getItem(videoContextConfig.videoStep));
      }
    )

    onMounted(() => {
      if(
        info.value.length && 
        sessionStorage.getItem(videoContextConfig.videoLikeNum) && 
        Number(sessionStorage.getItem(videoContextConfig.videoLikeNum)) % 2 == 1) 
      { info.value[1].value = <number>info.value[1].value + (Number(sessionStorage.getItem(videoContextConfig.videoStep)) || 0) } 
    })

    return {
      info,
      icon: Resource.data,
      show: videoProcess.cardInitLoad,
      isFail: videoProcess.cardInitFail,
      initData
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.infoCard {
  ul > li {
    height: 25px;
    line-height: 25px;
    color: $normal;
    display: flex;
    justify-content: space-between;
  }
}
</style>