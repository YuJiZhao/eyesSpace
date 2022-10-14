<template>
  <standard-card title="博文数据" :icon="icon">
    <Wait :show="show" :fail="isFail" height="100px" @callback="initData">
      <div class="card blogDetailDataCard">
        <ul>
          <li v-for="item in DataCardConfig" :key="item.name">
            <div class="title">{{item.title}}</div>
            <div class="name">{{detailData[item.name]}}</div>
          </li>
        </ul>
      </div>
    </Wait>
  </standard-card>
</template>

<script lang="ts">
import { defineComponent, watch, reactive } from "vue";
import StandardCard from "@/components/general/card/components/StandardCard.vue";
import resource from "@/config/resource";
import { Wait } from "@/components/general/popup";
import { blogDetailContext } from "@/components/content/blogDetail/businessTs/blogDetailContext";
import blogDetailProcess from "@/components/content/blogDetail/businessTs/blogDetailProcess";
import { DataCardConfig } from "@/components/content/blogDetail/config";

export default defineComponent({
  components: { StandardCard, Wait },
  setup() {
    let detailData = reactive({
      views: 0,
      likes: 0,
      collections: 0,
      comments: 0,
      words: 0,
      createTime: ""
    });

    function initData() {
      detailData.views = blogDetailContext.data.views!;
      detailData.likes = blogDetailContext.data.likes!;
      detailData.collections = blogDetailContext.data.collections!;
      detailData.comments = blogDetailContext.data.comments!;
      detailData.words = blogDetailContext.data.words!;
      detailData.createTime = blogDetailContext.data.createTime!;
    }

    watch(
      () => blogDetailProcess.likesNumSentry.value,
      () => {
        detailData.likes += blogDetailProcess.likesNumStep.value
      }
    )

    watch(
      () => blogDetailProcess.collectNumSentry.value,
      () => {
        detailData.collections += blogDetailProcess.collectNumStep.value
      }
    )

    return {
      icon: resource.data,
      show: blogDetailProcess.cardInitLoad,
      isFail: blogDetailProcess.cardInitFail,
      DataCardConfig,
      detailData,
      initData
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.blogDetailDataCard {
  li {
    display: flex;
    justify-content: space-between;
    margin-bottom: 5px;
    color: $normal;
  }
}
</style>