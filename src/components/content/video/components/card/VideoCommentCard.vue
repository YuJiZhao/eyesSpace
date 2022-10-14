<template>
  <standard-card title="站长感言" :icon="icon">
    <Wait :show="show" :fail="isFail" height="100px" @callback="initData">
      <div class="commentCard">{{text}}</div>
    </Wait>
  </standard-card>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import Resource from "@/config/resource";
import StandardCard from "@/components/general/card/components/StandardCard.vue";
import { Wait } from "@/components/general/popup";
import videoProcess from "@/components/content/video/businessTs/videoProcess";
import { videoContext } from "@/components/content/video/businessTs/videoContext";

export default defineComponent({
  components: { StandardCard, Wait },
  setup() {
    let text = ref('');
    function initData() {
      text.value = videoContext.data.ownerComment!;
    }

    return {
      icon: Resource.comment,
      show: videoProcess.cardInitLoad,
      isFail: videoProcess.cardInitFail,
      text,
      initData
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";
.commentCard {
  min-height: 50px;
  max-height: 120px;
  color: $normal;
  overflow-y: auto;
}

@media screen and (max-width: 800px) {
  .commentCard {
    max-height: none !important;
    overflow: visible !important;
  }
}

</style>