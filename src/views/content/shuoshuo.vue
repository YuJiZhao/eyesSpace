<template>
  <div class="shuoshuo">
    <n-tabs
      type="segment"
      ref="tabsInstRef"
      v-model:value="tabValue"
      animated
      class="tabs"
    >
      <n-tab-pane name="shuoshuoManagement" tab="说说管理">
        <ShuoshuoManagement />
      </n-tab-pane>
      <n-tab-pane name="shuoshuoPublish" tab="发布说说">
        <ShuoshuoPublish />
      </n-tab-pane>
      <n-tab-pane name="shuoshuoComment" tab="评论管理">
        <ShuoshuoComment />
      </n-tab-pane>
    </n-tabs>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import {
  ShuoshuoComment,
  ShuoshuoManagement,
  ShuoshuoPublish,
} from "@/components/content/shuoshuo";

export default {
  components: { ShuoshuoComment, ShuoshuoManagement, ShuoshuoPublish },
  setup() {
    let tabsInstRef = ref(null);
    let tabValue = ref();

    return {
      tabsInstRef,
      tabValue,
    };
  },
};
</script>

<style scoped lang="scss">
.shuoshuo {
  .tabs {
    margin-top: -20px;
  }
}
</style>
