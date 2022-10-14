<template>
  <div class="headMeta">
    <div class="title">{{title}}</div>
    <div class="meta">
      <span>类别: {{category}}</span>
      <span> | </span>
      <span>标签: <span v-for="label in labels" :key="label">{{label + " "}}</span></span>
      <span> | </span>
      <span>时长: {{time}}</span>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, watch, inject } from "vue";
import { blogDetailContext } from "@/components/content/blogDetail/businessTs/blogDetailContext";
import blogDetailProcess from "@/components/content/blogDetail/businessTs/blogDetailProcess";
import { HelpInterface } from "@/d.ts/plugin";

export default defineComponent({
  components: {  },
  setup() {
    const $utils = inject<HelpInterface>("$utils")!;

    let title = ref("");
    let category = ref("");
    let labels = ref<Array<string>>([]);
    let time = ref("");

    watch(
      () => blogDetailProcess.cardInitLoad.value,
      (value) => {
        if(!value) {
          title.value = blogDetailContext.data.title!;
          category.value = blogDetailContext.data.category!;
          labels.value = blogDetailContext.data.labels!;
          time.value = $utils.estimateReadTime(blogDetailContext.data.words!);
        } 
      })

    return {
      title,
      category,
      labels,
      time
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.headMeta {
  .title {
    min-height: 40px;
    line-height: 40px;
    font-size: 25px;
    color: $title;
  }
  .meta {
    font-size: 13px;
    height: 20px;
    line-height: 20px;
    color: $normal;
  }
}
</style>