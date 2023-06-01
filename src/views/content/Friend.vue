<template>
  <div class="friend">
    <Wait :show="show" :fail="isFail" height="400px">
      <friend-chain-list :key="friendSentry" :data="friendListData"/>
    </Wait>
    <Pagination
      class="pagination"
      :key="friendSentry"
      :total="total"
      :size="pageSize"
      :initPage="page"
      @pageChange="pageChange"
    />
    <friend-preamble />
    <friend-apply />
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
import { FriendPreamble, FriendApply, FriendChainList } from "@/components/content/friend";

export default defineComponent({
  name: "Friend",
  components: { Pagination, Wait, FriendPreamble, FriendApply, FriendChainList },
  beforeRouteEnter: () => {
    writerMeta(metaInfo.friend);
  },
  setup() {
    const $api = inject<ApiObject>("$api")!;
    const $process = inject<ProcessInterface>("$process")!;

    let show = ref(true);
    let isFail = ref(false);
    let page = ref(1);
    let pageSize = ref(8);
    let total = ref(0);
    let friendListData = ref([]);
    let friendSentry = ref(0);

    async function getFriendList() {
      show.value = false;
      $api.getFriendList({page: page.value}).then(({code, msg, data}) => {
        if (code == codeConfig.success) {
          friendListData.value = data.data;
          total.value = data.total;
          show.value = false;
          friendSentry.value++;
          goBoth(GoBothType.TopSpeed);
        } else {
          $process.tipShow.error("获取数据失败");
          isFail.value = true;
        }
      });
    }

    function pageChange(target: number) {
      page.value = target;
      getFriendList();
    }

    onBeforeMount(() => {
      getFriendList();
    })

    onActivated(() => {
      useProcessControl(true, {
        direction: CardDirection.row,
        cardType: CardType.CardList,
        cardList: CardList.FriendCardList
      });
    })

    return {
      show,
      isFail,
      total,
      page,
      pageSize,
      friendListData,
      friendSentry,
      pageChange
    };
  },
});
</script>

<style lang="scss" scoped>
.friend {
  width: 100%;
  .pagination {
    margin-top: 20px;
  }
}
</style>