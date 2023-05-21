<template>
  <div class="versionMd">
      <md-editor 
        v-model="content"
        :editor-id="id"
        preview-theme="vuepress" 
        previewOnly 
        showCodeRowNumber
      />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

export default defineComponent({
  components: { MdEditor },
  props: ["content", "commentId"],
  setup(props) {
    return {
      content: props.content,
      id: "versionId" + props.commentId.replace(/\./g, "-")
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

:deep(.md) {
  background: none;
  li {
    margin-top: 5px;
    margin-bottom: 5px;
  }
}
</style>