<template>
  <div class="mdEditor">
    <Wait :show="show" :fail="isFail" height="400px" @callback="initData">
      <md-editor 
        v-model="content" 
        :editor-id="editorId"
        preview-theme="vuepress" 
        previewOnly 
        showCodeRowNumber
        no-katex
      />
    </Wait>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { blogDetailContext } from "@/components/content/blogDetail/businessTs/blogDetailContext";
import { blogDetailConfig } from "@/components/content/blogDetail/config";
import blogDetailProcess from "@/components/content/blogDetail/businessTs/blogDetailProcess";
import { Wait } from "@/components/general/popup";

export default defineComponent({
  components: { MdEditor, Wait },
  props: ["content"],
  setup() {
    let content = ref("");

    function initData() {
      content.value = blogDetailContext.data.content!;
    }

    return {
      content,
      show: blogDetailProcess.cardInitLoad,
      isFail: blogDetailProcess.cardInitFail,
      editorId: blogDetailConfig.editorId,
      initData,
    };
  },
});
</script>

<style lang="scss" scoped>
:deep(.md) {
  background: none;
}
</style>