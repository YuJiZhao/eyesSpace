<template>
  <div class="blogCardList">
    <owner-card class="card" />
    <blog-data-card class="card" />
    <blog-category-card class="card" />
    <blog-label-card class="card" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { OwnerCard } from "@/components/general/card";
import { BlogDataCard, BlogCategoryCard, BlogLabelCard } from "@/components/content/blog/components/cards";

export default defineComponent({
  components: { OwnerCard, BlogDataCard, BlogCategoryCard, BlogLabelCard },
  setup() {
    return {
    };
  },
});
</script>

<style lang="scss" scoped>
.blogCardList {
  .card {
    margin-bottom: 20px;
  }
}

</style>