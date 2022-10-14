<template>
  <div class="about">
    <md-editor 
      v-model="content" 
      :editor-id="editorId"
      preview-theme="vuepress" 
      previewOnly 
      showCodeRowNumber
      no-katex
    />
    <Comment class="aboutComment" :objectId="objectId" :apiType="apiType" />
  </div>
</template>

<script lang="ts">
import { defineComponent, onActivated } from "vue";
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import useProcessControl from "@/hooks/useProcessControl";
import about from "@/config/about";
import Comment from "@/components/general/comment/Comment.vue";
import { CommentApiType } from "@/constant";
import { writerMeta } from "@/router/help";
import { metaInfo } from "@/config/site";

export default defineComponent({
  name: "About",
  components: { MdEditor, Comment },
  beforeRouteEnter: () => {
    writerMeta(metaInfo.about);
  },
  setup() {
    onActivated(() => {
      useProcessControl();
    })

    return {
      content: about,
      editorId: "about",
      objectId: 0,
      apiType: CommentApiType.site,
    };
  },
});
</script>

<style lang="scss" scoped>
:deep(.md) {
  background: none;
  font-family: 'eyes';
}

.about {
  .aboutComment {
    margin-top: 20px;
  }
}
</style>