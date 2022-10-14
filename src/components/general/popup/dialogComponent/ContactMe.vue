<template>
  <base-dialog :close="true" :title="title">
    <div class="contactMe">
      <div class="itemList" v-for="item in contactMeConfig" :key="item.title">
        <div class="title">{{item.title}}:</div>
        <div class="box">
          <div class="content">{{ item.content }}</div>
          <div class="clickBtn">
            <img :src="item.btnIcon" />
            <div @click="clickFunc[item.clickFunc]">{{item.btnWord}}</div>
          </div>
        </div>
      </div>
    </div>
  </base-dialog>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import { ProcessInterface, HelpInterface } from "@/d.ts/plugin";
import BaseDialog from "./BaseDialog.vue";
import { siteContext, contactMeConfig } from "@/config/site";

export default defineComponent({
  components: { BaseDialog },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $utils = inject<HelpInterface>("$utils")!;

    let clickFunc = {
      copy: () => {
        $utils.doCopy(siteContext.ownerEmail);
        $process.tipShow.success("复制成功");
      },
      goBilibli: () => { window.open("https://www.bilibili.com/video/" + siteContext.siteVideoBV) },
    };

    return {
      title: "联系站长",
      contactMeConfig,
      clickFunc,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.contactMe {
  .title {
    margin-left: 20px;
    margin-bottom: 20px;
  }
  .box {
    display: flex;
    justify-content: center;
    margin: 0 auto;
    margin-bottom: 20px;
    .content {
      width: 200px;
      height: 30px;
      text-align: center;
      line-height: 25px;
      border: 2px dashed rgb(50, 50, 50);
      border-radius: 5px;
      margin-right: 10px;
    }
    .clickBtn {
      width: 100px;
      height: 30px;
      line-height: 30px;
      border: 2px solid rgb(50, 50, 50);
      border-radius: 5px;
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;
      img {
        display: block;
        width: 18px;
        height: 18px;
        margin-right: 5px;
      }
    }
  }
}
</style>
