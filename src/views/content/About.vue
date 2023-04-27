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
  </div>
</template>

<script lang="ts">
import { defineComponent, onActivated, ref, inject } from "vue";
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import useProcessControl from "@/hooks/useProcessControl";
import { writerMeta } from "@/router/help";
import { metaInfo } from "@/config/site";
import { ApiObject, ProcessInterface } from "@/d.ts/plugin";
import { codeConfig } from "@/config/program";

export default defineComponent({
  name: "About",
  components: { MdEditor },
  beforeRouteEnter: () => {
    writerMeta(metaInfo.about);
  },
  setup() {
    const $api = inject<ApiObject>("$api")!;
    const $process = inject<ProcessInterface>("$process")!;

    let content = ref("");

    async function getAboutContent() {
      $api.getAboutContent().then(({code, msg, data}) => {
        if (code == codeConfig.success) {
          content.value = data.content;
        } else {
          $process.tipShow.error(`内容获取失败！${msg}`);
        }
      });
    }
    
    onActivated(() => {
      useProcessControl();
    });

    // created生命周期执行
    getAboutContent();

    return {
      content,
      editorId: "about",
    };
  },
});
</script>

<style lang="scss" scoped>
:deep(.md) {
  background: none;
}

.about {
  .aboutComment {
    margin-top: 20px;
  }
}
</style>