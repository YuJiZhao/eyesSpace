<template>
  <standard-card title="动漫数据" :icon="icon">
    <Wait :show="show" :fail="isFail" height="100px">
      <div class="card animeDataCard">
        <ul>
          <li v-for="item in animeDataCardConfig" :key="item.name">
            <div>{{ item.title }} :</div>
            <div>{{ animeData[item.name] }}</div>
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
import { animeDataCardConfig } from "@/components/content/anime/config";
import { Wait } from "@/components/general/popup";

export default defineComponent({
  components: { StandardCard, Wait },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let show = ref(true);
    let isFail = ref(false);
    let animeData = ref({});

    async function getAnimeListInfo() {
      show.value = true;
      isFail.value = false;
      await $api.getAnimeListInfo().then(({code, data}) => {
        if(code == codeConfig.success) {
          animeData.value = data;
          show.value = false;
        } else {
          $process.tipShow.error("动漫数据获取失败");
          isFail.value = true;
        }
      });
    }

    onMounted(() => {
      getAnimeListInfo();
    })
    
    return {
      icon: resource.data,
      animeDataCardConfig,
      animeData,
      show,
      isFail
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.animeDataCard {
  li {
    display: flex;
    justify-content: space-between;
    margin-bottom: 5px;
    color: $normal;
  }
}
</style>