<template>
  <div class="shuoItem">
    <div class="content">
      <shuo-md :content="props.content" :commentId="props.id" />
    </div>
    <div class="imgList" v-if="props.picList.length">
      <div class="shuoImg" v-for="item in props.picList" :key="item">
        <Image :imgArray="props.picList" :url="item" size="97px" />
      </div>
    </div>
    <div class="foot">
      <div class="time">{{props.createTime}}</div>
      <div class="data">
        <div class="views">阅读:{{views}}</div>
        <div class="comment" @click="doComment">评论:{{comments}}</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import Resource from "@/config/resource";
import Image from "@/components/general/image/Image.vue";
import ShuoMd from "./ShuoMd.vue";
import { HelpInterface } from "@/d.ts/plugin";

export default defineComponent({
  components: { Image, ShuoMd },
  props: {
    id: String,
    content: String,
    picList: Array,
    views: Number,
    comments: Number,
    createTime: String
  },
  setup(props) {
    const $utils = inject<HelpInterface>("$utils")!;

    return {
      commentIcon: Resource.comment,
      props,
      views: $utils.simplifyNum(props.views!),
      comments: $utils.simplifyNum(props.comments!)
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.shuoItem {
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
  .content {
    margin-bottom: 10px;
  }
  .imgList {
    display: flex;
    justify-content: start;
    flex-wrap: wrap;
    .shuoImg {
      margin-right: 5px;
      margin-bottom: 5px;
    }
  }
  .foot {
    display: flex;
    justify-content: space-between;
    height: 40px;
    margin-top: 10px;
    padding-top: 5px;
    line-height: 30px;
    font-size: 15px;
    border-top: 1px dashed rgba($color: $black, $alpha: 0.5);
    .data {
      width: 140px;
      display: flex;
      justify-content: flex-end;
      .comment {
        margin-left: 10px;
      }
    }
  }
}
</style>