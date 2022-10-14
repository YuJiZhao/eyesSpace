<template>
  <div class="home">
    <Wait :show="show" :fail="isFail" height="400px">
      <home-list :key="homeSentry" :homeListData="homeListData" />
    </Wait>
    <Pagination
      :total="total"
      :size="pageSize"
      :initPage="page"
      @pageChange="pageChange"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, ref, onBeforeMount, watch, onActivated } from 'vue';
import { ProcessInterface, ApiObject, ContextInterface } from "@/d.ts/plugin";
import useProcessControl from "@/hooks/useProcessControl";
import { CardDirection, Cards } from "@/constant";
import { codeConfig } from "@/config/program";
import { useRouter } from "vue-router";
import Pagination from "@/components/general/Pagination/pagination.vue";
import useGoTop from "@/hooks/useGoTop";
import { writerMeta } from "@/router/help";
import { metaInfo } from "@/config/site";
import HomeList from "@/components/content/home/HomeList.vue";
import { Wait } from "@/components/general/popup";

export default defineComponent({
  name: "Home",
  components: { HomeList, Pagination, Wait },
  beforeRouteEnter: () => {
    writerMeta(metaInfo.home);
  },
  setup() {
    const router = useRouter();
    const $api = inject<ApiObject>("$api")!;
    const $context = inject<ContextInterface>("$context")!;
    const $process = inject<ProcessInterface>("$process")!;

    let show = ref(true);
    let isFail = ref(false);
    let page = ref(1);
    let pageSize = ref(6);
    let homeListData = ref([]);
    let homeSentry = ref(0);

    async function getHomeList() {
      await $api.getHomeList({
        page: page.value,
        pageSize: pageSize.value
      }).then(({code, msg, data}) => {
        if(code == codeConfig.success) {
          homeListData.value = data;
          show.value = false;
          homeSentry.value ++;
          useGoTop();
        } else {
          $process.tipShow.error("获取数据失败");
          isFail.value = true;
        }
      })
    }
    
    async function pageChange(target: number) {
      router.push({
        path: "/",
        query: {
          page: target
        }
      });
    }

    watch(
      () => router.currentRoute.value.query,
      () => {
        if(router.currentRoute.value.path != "/") return;
        page.value = router.currentRoute.value.query.page ? Number(router.currentRoute.value.query.page) : 1;
        getHomeList();
      }
    )

    onActivated(() => {
      useProcessControl(true, {
        direction: CardDirection.row,
        cards: [
          Cards.OwnerCard,
          Cards.AnnounceCard,
          Cards.InfoCard
        ]
      });
    })

    onBeforeMount(() => {
      page.value = router.currentRoute.value.query.page ? Number(router.currentRoute.value.query.page) : 1;
      getHomeList();
    })

    return {
      homeListData,
      total: $context.data.blogNum! + $context.data.shuoNum!,
      show,
      isFail,
      page,
      pageSize,
      homeSentry,
      pageChange
    };
  },
});
</script>

<style lang="scss" scoped>
.home {
  width: 100%;
}
</style>