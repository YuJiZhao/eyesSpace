<template>
  <standard-card title="版本数据" :icon="icon">
    <Wait :show="show" :fail="isFail" height="100px">
      <div class="card versionDataCard">
        <ul>
          <li v-for="item in versionDataCardConfig" :key="item.name">
            <div>{{ item.title }} :</div>
            <div>{{ versionData[item.name] }}</div>
          </li>
        </ul>
      </div>
    </Wait>
  </standard-card>
</template>

<script lang="ts">
import { defineComponent, ref, inject, onMounted, reactive } from "vue";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { codeConfig } from "@/config/program";
import StandardCard from "@/components/general/card/components/StandardCard.vue";
import resource from "@/config/resource";
import { versionDataCardConfig } from "@/components/content/version/config";
import { Wait } from "@/components/general/popup";

export default defineComponent({
  components: { StandardCard, Wait },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let show = ref(true);
    let isFail = ref(false);
    let versionData = reactive({
      siteVersion: "3.2.1",
      frontendVersion: "2.1.3",
      backendVersion: "2.1.2",
      versionNum: 5
    });

    async function getVersionInfo() {
      show.value = true;
      isFail.value = false;

      await $api.getVersionInfo().then(({code, data}) => {
        if(code == codeConfig.success) {
          versionData.siteVersion = data.siteVersion;
          versionData.frontendVersion = data.frontendVersion;
          versionData.backendVersion = data.backendVersion;
          versionData.versionNum = data.versionNum;
          show.value = false;
        } else {
          $process.tipShow.error("版本数据获取失败");
          isFail.value = true;
        }
      });

      show.value = false;
    }

    onMounted(() => {
      getVersionInfo();
    })
    
    return {
      icon: resource.data,
      versionDataCardConfig,
      versionData,
      show,
      isFail
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.versionDataCard {
  li {
    display: flex;
    justify-content: space-between;
    margin-bottom: 5px;
    color: $normal;
  }
}
</style>