<template>
  <div class="versionItem">
    <div class="header">
        {{type + "版本更新 v" + version}}
    </div>
    <div class="content">
      <version-md :content="content" :commentId="commentId" />
    </div>
    <div class="imgList" v-if="picList.length">
      <div class="versionImg" v-for="item in picList" :key="item">
        <Image :imgArray="picList" :url="item" size="97px" />
      </div>
    </div>
    <div class="footer">{{createTime}}</div>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import Image from "@/components/general/image/Image.vue";
import VersionMd from "./VersionMd.vue";
import { HelpInterface } from "@/d.ts/plugin";
import { versionTypeConvert } from "../config";

export default defineComponent({
  components: { Image, VersionMd },
  props: ["data"],
  setup(props) {
    const $utils = inject<HelpInterface>("$utils")!;

    return {
        type: versionTypeConvert[props.data.type],
        version: props.data.version,
        content: props.data.description,
        commentId: props.data.type + "-" + props.data.version,
        picList: props.data.picList,
        createTime: props.data.createTime
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.versionItem {
  width: calc(100% - 5px);
  margin: 0 auto;
  margin-top: 2px;
  margin-bottom: 20px;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  color: $normal;
  .header {
    font-size: 26px;
    margin-top: 10px;
    text-align: center;
  }
  .content {
    margin-bottom: 10px;
  }
  .imgList {
    display: flex;
    justify-content: start;
    flex-wrap: wrap;
    .versionImg {
      margin-right: 5px;
      margin-bottom: 5px;
    }
  }
  .footer {
    text-align: right;
  }
}
</style>