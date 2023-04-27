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
import { defineComponent, inject, onMounted, ref } from "vue";
import StandardCard from "./components/StandardCard.vue";
import resource from "@/config/resource";
import { siteData } from "@/config/site";
import { ContextInterface, HelpInterface, ApiObject, ProcessInterface } from "@/d.ts/plugin";
import { codeConfig } from "@/config/program";

export default defineComponent({
  components: { StandardCard },
  setup() {
    const $api = inject<ApiObject>("$api")!;
    const $context = inject<ContextInterface>("$context")!;
    const $utils = inject<HelpInterface>("$utils")!;
    const $process = inject<ProcessInterface>("$process")!;

    let num = ref([
      $utils.getTimeDisff(
        new Date(),
        new Date($context.data.buildTime as string)
      ),
    ]);

    async function getSiteData() {
      $api.getSiteData().then(({code, msg, data}) => {
        if (code == codeConfig.success) {
          num.value.push(data.visitNum);
          num.value.push(data.visitorNum);
        } else {
          $process.tipShow.error("网站数据获取失败！" + msg);
        }
      })
    }

    onMounted(() => {
      getSiteData();
    })

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