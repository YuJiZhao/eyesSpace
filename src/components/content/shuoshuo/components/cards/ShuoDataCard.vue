<template>
  <standard-card title="说说数据" :icon="icon">
    <Wait :show="show" :fail="isFail" height="100px">
      <div class="card shuoDataCard">
        <ul>
          <li v-for="item in shuoDataCardConfig" :key="item.name">
            <div>{{ item.title }} :</div>
            <div>{{ shuoData[item.name] }}</div>
          </li>
        </ul>
      </div>
    </Wait>
  </standard-card>
</template>

<script lang="ts">
import { defineComponent, ref, inject, onMounted } from "vue";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { codeConfig } from "@/config/program";
import StandardCard from "@/components/general/card/components/StandardCard.vue";
import resource from "@/config/resource";
import { shuoDataCardConfig } from "@/components/content/shuoshuo/config";
import { Wait } from "@/components/general/popup";

export default defineComponent({
  components: { StandardCard, Wait },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let show = ref(true);
    let isFail = ref(false);
    let shuoData = ref();

    async function getShuoshuoListInfo() {
      show.value = true;
      isFail.value = false;
      await $api.getShuoshuoListInfo().then(({code, data}) => {
        if(code == codeConfig.success) {
          shuoData.value = data;
          if(data.deleteNum) {
            shuoData.value.totalNum -= data.deleteNum;
          }
          show.value = false;
        } else {
          $process.tipShow.error("说说数据获取失败");
          isFail.value = true;
        }
      });
    }

    onMounted(() => {
      getShuoshuoListInfo();
    })
    
    return {
      icon: resource.data,
      shuoDataCardConfig,
      shuoData,
      show,
      isFail
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.shuoDataCard {
  li {
    display: flex;
    justify-content: space-between;
    margin-bottom: 5px;
    color: $normal;
  }
}
</style>