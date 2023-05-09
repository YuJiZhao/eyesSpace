<template>
  <div class="blog">
    <n-tabs
      type="segment"
      ref="tabsInstRef"
      v-model:value="tabValue"
      animated
      class="tabs"
    >
      <n-tab-pane name="blogManagement" tab="博客管理">
        <BlogManagement />
      </n-tab-pane>
      <n-tab-pane name="blogComment" tab="评论管理">
        <BlogComment />
      </n-tab-pane>
      <n-tab-pane name="blogPublish" tab="发布博客">
        <BlogPublish />
      </n-tab-pane>
      <n-tab-pane name="blogPage" tab="博客页面">
        <BlogPage />
      </n-tab-pane>
    </n-tabs>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import {
  BlogComment,
  BlogManagement,
  BlogPage,
  BlogPublish,
} from "@/components/content/blog";

export default {
  components: { BlogComment, BlogManagement, BlogPage, BlogPublish },
  setup() {
    let tabsInstRef = ref(null);
    let tabValue = ref();

    return {
      tabsInstRef,
      tabValue,
    };
  },
};
</script>

<style scoped lang="scss">
.blog {
  .tabs {
    margin-top: -20px;
  }
}
</style>
