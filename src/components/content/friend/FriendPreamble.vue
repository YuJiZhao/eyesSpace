<template>
  <div class="friendPreamble">
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
import { defineComponent, onBeforeMount, ref, inject } from "vue";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { codeConfig } from "@/config/program";
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

export default defineComponent({
  components: { MdEditor },
  setup() {
    const $api = inject<ApiObject>("$api")!;
    const $process = inject<ProcessInterface>("$process")!;

    let content = ref("");

    function getFriendPreamble() {
        $api.getFriendPreamble().then(({code, data}) => {
            if (code == codeConfig.success) {
                content.value = data.content;
            } else {
                $process.tipShow.error("获取数据失败");
            }
        });
    }
    
    onBeforeMount(() => {
      getFriendPreamble();
    })

    return {
      content,
      id: "friend"
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