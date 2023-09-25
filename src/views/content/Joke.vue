<template>
  <div class="joke">
    <Wait :show="show" :fail="isFail" height="400px">
      <joke-list :key="jokeSentry" :jokeListData="jokeListData" />
    </Wait>
    <Pagination
      :key="jokeSentry"
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
import { JokeList } from "@/components/content/joke";

export default defineComponent({
  name: "Joke",
  components: { Pagination, Wait, JokeList },
  beforeRouteEnter: () => {
    writerMeta(metaInfo.joke);
  },
  setup() {
    const $api = inject<ApiObject>("$api")!;
    const $process = inject<ProcessInterface>("$process")!;

    let show = ref(false);
    let isFail = ref(false);
    let page = ref(1);
    let pageSize = ref(20);
    let total = ref(0);
    let jokeListData = ref([]);
    let jokeSentry = ref(0);

    async function getJokeList() {
      $api.getJokeList({page: page.value}).then(({code, msg, data}) => {
        if (code == codeConfig.success) {
          jokeListData.value = data.data;
          total.value = data.total;
          show.value = false;
          jokeSentry.value++;
          goBoth(GoBothType.TopSpeed);
        } else {
          $process.tipShow.error("获取数据失败");
          isFail.value = true;
        }
      });
    }

    function pageChange(target: number) {
      page.value = target;
      getJokeList();
    }

    onBeforeMount(() => {
      getJokeList();
    })

    onActivated(() => {
      useProcessControl(true, {
        direction: CardDirection.row,
        cardType: CardType.CardList,
        cardList: CardList.JokeCardList
      });
    })

    return {
      show,
      isFail,
      total,
      page,
      pageSize,
      jokeListData,
      jokeSentry,
      pageChange
    };
  },
});
</script>

<style lang="scss" scoped>
.joke {
  width: 100%;
}
</style>