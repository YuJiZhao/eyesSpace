<template>
  <standard-card title="网站数据" :icon="icon">
    <div class="card infoCard">
      <ul>
        <li v-for="item in siteData" :key="item.key">
          <div>{{ item.name }} :</div>
          <div>{{ num[item.key] }}</div>
        </li>
      </ul>
    </div>
  </standard-card>
</template>

<script lang="ts">
import { defineComponent, inject, ref } from "vue";
import StandardCard from "./components/StandardCard.vue";
import resource from "@/config/resource";
import { siteData } from "@/config/site";
import { ContextInterface, HelpInterface } from "@/d.ts/plugin";

export default defineComponent({
  components: { StandardCard },
  setup() {
    const $context = inject<ContextInterface>("$context")!;
    const $utils = inject<HelpInterface>("$utils")!;

    let num = ref([
      $context.data.visitNum,
      $context.data.visitorNum,
      $context.data.userNum,
      $utils.getTimeDisff(
        new Date(),
        new Date($context.data.buildTime as string)
      ),
    ]);
    return {
      icon: resource.data,
      siteData,
      num,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.infoCard {
  li {
    display: flex;
    justify-content: space-between;
    margin-bottom: 5px;
    color: $normal;
  }
}
</style>