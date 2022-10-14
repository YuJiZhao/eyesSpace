<template>
  <div class="shuoItem">
    <div class="content">{{props.content}}</div>
    <div class="imgList" v-if="props.picList.length">
      <div class="shuoImg" v-for="item in props.picList" :key="item">
        <Image :url="item" size="97px" />
      </div>
    </div>
    <div class="foot">
      <div class="time">{{props.createTime}}</div>
      <div class="data">
        <div class="views">阅读: {{props.views}}</div>
        <div class="comment" @click="doComment">评论: {{props.comments}}</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import { ProcessInterface } from "@/d.ts/plugin";
import Resource from "@/config/resource";
import Image from "@/components/general/image/Image.vue";

export default defineComponent({
  components: { Image },
  props: {
    id: String,
    content: String,
    picList: Array,
    views: Number,
    comments: Number,
    createTime: String
  },
  setup(props) {
    const $process = inject<ProcessInterface>("$process")!;

    function doComment() {
      $process.alertShow({
        title: "服务终止",
        content: "评论功能暂未上线，敬请期待"
      });
    }

    return {
      commentIcon: Resource.comment,
      props,
      doComment
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