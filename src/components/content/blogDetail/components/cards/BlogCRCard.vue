<template>
  <standard-card title="版权声明" :icon="icon">
    <div class="card blogCRCard">
      <div class="listItem">
          <div>文章作者</div>
          <div>{{ownerName}}</div>
      </div>
      <div class="listItem">
          <div>文章链接</div>
          <div class="copy" @click="doCopy">点击复制</div>
      </div>
      <div class="word">转载请注明出处！</div>
    </div>
  </standard-card>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import StandardCard from "@/components/general/card/components/StandardCard.vue";
import { HelpInterface, ProcessInterface, ContextInterface } from "@/d.ts/plugin";
import resource from "@/config/resource";
import { Wait } from "@/components/general/popup";

export default defineComponent({
  components: { StandardCard, Wait },
  setup() {
    const $utils = inject<HelpInterface>("$utils")!;
    const $process = inject<ProcessInterface>("$process")!;
    const $context = inject<ContextInterface>("$context")!;

    function doCopy() {
        $utils.doCopy(location.href);
        $process.tipShow.success("复制成功");
    }

    return {
      icon: resource.copyright,
      ownerName: $context.data.ownerName,
      spaceName: $context.data.spaceName,
      doCopy
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.blogCRCard {
  color: $normal;
  .listItem {
    height: 25px;
    line-height: 25px;
    display: flex;
    justify-content: space-between;
    .copy {
      color: rgb(6, 122, 122);
      cursor: pointer;
    }
  }
  .word {
    margin-top: 10px;
  }
}
</style>