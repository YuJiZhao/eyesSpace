<template>
  <standard-card title="版权声明" :icon="icon">
    <Wait :show="show" :fail="isFail" height="100px" @callback="initData">
      <div class="copyRightCard">
        <ul>
          <li class="dataLi">
            <div>原视频地址：</div>
            <div class="link" @click="goVideoAddr">网络链接</div>
          </li>
          <li class="dataLi">
            <div class="authorKey">原作者：</div>
            <div class="author">{{author}}</div>
          </li>
          <li class="dataLi">
            <div>站长邮箱：</div>
            <div class="copy" @click="copyEmail">点击复制</div>
          </li>
          <li class="word">如有侵权，请联系站长删除</li>
        </ul>
      </div>
    </Wait>
  </standard-card>
</template>

<script lang="ts">
import { defineComponent, inject, ref } from "vue";
import Resource from "@/config/resource";
import StandardCard from "@/components/general/card/components/StandardCard.vue";
import { Wait } from "@/components/general/popup";
import videoProcess from "@/components/content/video/businessTs/videoProcess";
import { videoContext } from "@/components/content/video/businessTs/videoContext";
import { siteContext } from "@/config/site"; 
import { HelpInterface, ProcessInterface } from "@/d.ts/plugin";

export default defineComponent({
  components: { StandardCard, Wait },
  setup() {
    const $utils = inject<HelpInterface>("$utils")!;
    const $process = inject<ProcessInterface>("$process")!;

    let author = ref("");
    let url = ref("");

    function initData() {
      url.value = videoContext.data.originalUrl!;
      author.value = videoContext.data.originalAuthor!;
    }

    function goVideoAddr() {
      window.open(url.value);
    }

    function copyEmail() {
      $utils.doCopy(siteContext.ownerEmail);
      $process.tipShow.success("复制成功");
    }

    return {
      icon: Resource.copyright,
      show: videoProcess.cardInitLoad,
      isFail: videoProcess.cardInitFail,
      author,
      initData,
      goVideoAddr,
      copyEmail
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.copyRightCard {
  ul > li {
    height: 25px;
    line-height: 25px;
    color: $normal;
  }
  .dataLi {
    display: flex;
    justify-content: space-between;
    .link, .copy {
      color: rgb(37, 90, 90);
      cursor: pointer;
    }
    .authorKey {
      min-width: 80px;
    }
    .author {
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
  .word {
    margin-top: 10px;
  }
}
</style>