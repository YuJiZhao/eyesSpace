<template>
  <div class="version">
    <Wait :show="show" :fail="isFail" height="400px">
      <version-list :key="versionSentry" :versionListData="versionListData" />
    </Wait>
    <Pagination
      :key="versionSentry"
      :total="total"
      :size="pageSize"
      :initPage="page"
      @pageChange="pageChange"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, onActivated, ref, onBeforeMount } from 'vue';
import { ProcessInterface, ApiObject, ContextInterface } from "@/d.ts/plugin";
import useProcessControl from "@/hooks/useProcessControl";
import { CardDirection, CardType, CardList } from "@/constant";
import { useRouter } from "vue-router";
import { writerMeta } from "@/router/help";
import { metaInfo } from "@/config/site";
import { codeConfig } from "@/config/program";
import { goBoth, GoBothType } from "@/hooks/useGoBoth";
import { Wait } from "@/components/general/popup";
import Pagination from "@/components/general/Pagination/pagination.vue";
import { VersionList } from "@/components/content/version";

export default defineComponent({
  name: "Version",
  components: { Pagination, Wait, VersionList },
  beforeRouteEnter: () => {
    writerMeta(metaInfo.version);
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
    let total = ref(0);
    let versionListData = ref([]);
    let versionSentry = ref(0);

    async function getVersionList() {
      $api.getVersionList({page: page.value}).then(({code, msg, data}) => {
        if (code == codeConfig.success) {
          console.log(data);
          versionListData.value = data.data;
          total.value = data.total;
          show.value = false;
          versionSentry.value++;
          goBoth(GoBothType.TopSpeed);
        } else {
          $process.tipShow.error("获取数据失败");
          isFail.value = true;
        }
      });
    }

    function pageChange(target: number) {
      page.value = target;
      getVersionList();
    }

    onBeforeMount(() => {
      getVersionList();
    })

    onActivated(() => {
      useProcessControl(true, {
        direction: CardDirection.row,
        cardType: CardType.CardList,
        cardList: CardList.VersionCardList
      });
    })

    return {
      show,
      isFail,
      total,
      page,
      pageSize,
      versionListData,
      versionSentry,
      pageChange
    };
  },
});
</script>

<style lang="scss" scoped>
.version {
  width: 100%;
}
</style>