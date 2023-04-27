<template>
  <div class="blogItem" @click="jumpDetail(props.id)">
    <div class="title">{{props.title}}</div>
    <div class="meta">
        <span>类别: {{props.category}}</span>
        <span> | </span>
        <span>字数: {{props.words}}</span>
        <span> | </span>
        <span>阅读时长: {{time}}</span>
    </div>
    <div class="summary">{{props.summary}}</div>
    <div class="footer">
        <div class="time">{{props.date}}</div>
        <div class="data">
            <div class="view">阅读:{{view}}</div>
        </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import { HelpInterface } from "@/d.ts/plugin";
import { useRouter } from "vue-router";

export default defineComponent({
  components: { },
  props: ["id", "title", "category", "words", "summary", "date", "view"],
  setup(props) {
    let router = useRouter();
    const $utils = inject<HelpInterface>("$utils")!;

    function jumpDetail(id: number) {
      window.open(router.resolve(`/blog/details/${id}`).href, "_blank");
    }

    return {
      props,
      time: $utils.estimateReadTime(props.words),
      view: $utils.simplifyNum(props.view),
      jumpDetail
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.blogItem {
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
  cursor: pointer;
  .title {
    min-height: 35px;
    font-size: 20px;
    line-height: 35px;
  }
  .meta {
    font-size: 13px;
    height: 20px;
    line-height: 20px;
    margin-bottom: 7px;
  }
  .summary {
    min-height: 40px;
    margin-bottom: 5px;
    font-size: 15px;
  }
  .footer {
    height: 20px;
    line-height: 20px;
    display: flex;
    justify-content: space-between;
    .data {
      width: 200px;
      display: flex;
      justify-content: flex-end;
    }
  }
}
</style>