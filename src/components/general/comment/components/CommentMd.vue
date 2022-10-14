<template>
  <div class="commentMd">
      <md-editor 
        v-model="content"
        :editor-id="id"
        preview-theme="github" 
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
      id: "commentId" + props.commentId
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

:deep(.md) {
  background: none;
  font-family: 'eyes';
}
</style>