<template>
  <div class="video">
    <n-tabs
      type="segment"
      ref="tabsInstRef"
      v-model:value="tabValue"
      animated
      class="tabs"
    >
      <n-tab-pane name="videoManagement" tab="视频管理">
        <VideoCard @playVideo="tabValue = 'videoPlay'"/>
      </n-tab-pane>
      <n-tab-pane name="videoUpload" tab="上传视频">
        <VideoUpload @playVideo="tabValue = 'videoPlay'"/>
      </n-tab-pane>
      <n-tab-pane name="videoPlay" tab="视频播放">
        <VideoPlay />
      </n-tab-pane>
    </n-tabs>
  </div>
</template>

<script>
import { VideoCard, VideoUpload, VideoPlay } from "@/components/video";
import { ref } from "@vue/reactivity";

export default {
  components: { VideoCard, VideoUpload, VideoPlay },
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
.video {
  .tabs {
    margin-top: -20px;
  }
}
</style>
