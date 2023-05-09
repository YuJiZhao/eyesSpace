<template>
  <div class="music">
    <n-tabs
      type="segment"
      ref="tabsInstRef"
      v-model:value="tabValue"
      animated
      class="tabs"
    >
      <n-tab-pane name="musicManagement" tab="音乐管理">
        <MusicCard @playMusic="tabValue = 'musicPlay'"/>
      </n-tab-pane>
      <n-tab-pane name="musicUpload" tab="上传音乐">
        <MusicUpload @playMusic="tabValue = 'musicPlay'"/>
      </n-tab-pane>
      <n-tab-pane name="musicPlay" tab="音乐播放">
        <MusicPlay />
      </n-tab-pane>
    </n-tabs>
  </div>
</template>

<script>
import { MusicCard, MusicUpload, MusicPlay } from "@/components/music";
import { ref } from "@vue/reactivity";

export default {
  components: { MusicCard, MusicUpload, MusicPlay },
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
.music {
  .tabs {
    margin-top: -20px;
  }
}
</style>
