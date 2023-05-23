<template>
  <div class="anime">
    <Wait :show="show" :fail="isFail" height="400px">
      <anime-list :key="animeSentry" :animeListData="animeListData" />
    </Wait>
    <Pagination
      :key="animeSentry"
      :total="total"
      :size="pageSize"
      :initPage="page"
      @pageChange="pageChange"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, onActivated, ref, onBeforeMount } from 'vue';
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import useProcessControl from "@/hooks/useProcessControl";
import { CardDirection, CardType, CardList } from "@/constant";
import { writerMeta } from "@/router/help";
import { metaInfo } from "@/config/site";
import { codeConfig } from "@/config/program";
import { goBoth, GoBothType } from "@/hooks/useGoBoth";
import { Wait } from "@/components/general/popup";
import Pagination from "@/components/general/Pagination/pagination.vue";
import { AnimeList } from "@/components/content/anime";

export default defineComponent({
  name: "Anime",
  components: { Pagination, Wait, AnimeList },
  beforeRouteEnter: () => {
    writerMeta(metaInfo.anime);
  },
  setup() {
    const $api = inject<ApiObject>("$api")!;
    const $process = inject<ProcessInterface>("$process")!;

    let show = ref(true);
    let isFail = ref(false);
    let page = ref(1);
    let pageSize = ref(6);
    let total = ref(0);
    let animeSentry = ref(0);
    let animeListData = ref([]);

    async function getAnimeList() {
      $api.getAnimeList({page: page.value}).then(({code, data}) => {
        if (code == codeConfig.success) {
          animeListData.value = data.data;
          total.value = data.total;
          show.value = false;
          animeSentry.value++;
          goBoth(GoBothType.TopSpeed);
        } else {
          $process.tipShow.error("获取数据失败");
          isFail.value = true;
        }
      })
    }

    function pageChange(target: number) {
      page.value = target;
      getAnimeList();
    }

    onBeforeMount(() => {
      getAnimeList();
    })

    onActivated(() => {
      useProcessControl(true, {
        direction: CardDirection.row,
        cardType: CardType.CardList,
        cardList: CardList.AnimeCardList
      });
    })

    return {
      show,
      isFail,
      total,
      page,
      pageSize,
      animeSentry,
      animeListData,
      pageChange
    };
  },
});
</script>

<style lang="scss" scoped>
.anime {
  width: 100%;
}
</style>