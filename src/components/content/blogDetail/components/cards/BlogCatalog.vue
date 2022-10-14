<template>
    <standard-card title="博客目录" :icon="icon">
        <div class="blogCatalog">
          <Wait :show="show" :fail="isFail" height="100px">
            <md-atalog :editor-id="editorId" :scroll-element="scrollElement" />
          </Wait>
        </div>
    </standard-card>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import StandardCard from "@/components/general/card/components/StandardCard.vue";
import blogDetailProcess from "@/components/content/blogDetail/businessTs/blogDetailProcess";
import { Wait } from "@/components/general/popup";
import { blogDetailConfig } from "@/components/content/blogDetail/config";
import resource from "@/config/resource";
const MdAtalog = MdEditor.MdCatalog;
const scrollElement = document.documentElement;

export default defineComponent({
  components: { StandardCard, MdEditor, Wait, MdAtalog },
  props: ["content"],
  setup() {
    return {
      show: blogDetailProcess.cardInitLoad,
      isFail: blogDetailProcess.cardInitFail,
      editorId: blogDetailConfig.editorId,
      scrollElement,
      icon: resource.catalogue
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

:deep(.md-catalog-link span) {
    color: $normal;
}
</style>