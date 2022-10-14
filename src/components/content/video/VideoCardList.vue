<template>
  <div class="videoCard">
    <video-copy-right-card class="card" />
    <video-info-card class="card" />
    <video-comment-card class="card" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { VideoCopyRightCard, VideoInfoCard, VideoCommentCard } from "./components/card";

export default defineComponent({
  components: { VideoCopyRightCard, VideoInfoCard, VideoCommentCard },
  setup() {
    return {
    };
  },
});
</script>

<style lang="scss" scoped>
.videoCard {
  .card {
    margin-bottom: 20px;
  }
}

</style>