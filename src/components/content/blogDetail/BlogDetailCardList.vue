<template>
  <div class="blogDetailCardList">
    <owner-card class="card" />
    <blog-data-card class="card" />
    <blog-catalog class="card" :id="followId" />
    <blog-c-r-card class="card" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { OwnerCard } from "@/components/general/card";
import { BlogDataCard, BlogCRCard, BlogCatalog } from "@/components/content/blogDetail/components/cards";
import { siteConfig } from "@/config/program";

export default defineComponent({
  components: { OwnerCard, BlogDataCard, BlogCRCard, BlogCatalog },
  setup() {
    return {
      followId: siteConfig.followCardId
    };
  },
});
</script>

<style lang="scss" scoped>
.blogDetailCardList {
  .card {
    margin-bottom: 20px;
  }
}

</style>