<template>
  <div class="blogList">
    <blog-item
      v-for="item in blogListData" :key="item.id"
      :id="item.id"
      :title="item.title"
      :category="item.category"
      :words="item.words"
      :summary="item.summary"
      :date="item.createTime"
      :view="item.views"
      :like="item.likes"
      :collection="item.collections"
    />
    <div class="noDataTip" v-if="!blogListData.length">暂无数据</div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted } from "vue";
import BlogItem from "@/components/content/blog/components/BlogItem.vue";
import { blogContext } from "@/components/content/blog/businessTs/blogContext";

export default defineComponent({
  components: { BlogItem },
  setup() {

    return {
      blogListData: blogContext.data,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.blogList {
  width: 100%;
  min-width: 335px;
  margin: 0 auto;
  .noDataTip {
    height: 400px;
    color: $normal;
    line-height: 400px;
    text-align: center;
  }
}
</style>