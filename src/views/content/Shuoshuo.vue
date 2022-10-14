<template>
  <div class="shuoshuo">
    <Wait :show="show" :fail="isFail" height="400px">
      <shuo-list :key="refreshSentry" class="shuoList" />
    </Wait>
    <Wait class="loadBtnWait" :show="loadShow" size="30px">
      <div class="loadBtn" @click="loadData">
        <img :src="loadIcon">
        <div>加载更多</div>
      </div>
    </Wait>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, onActivated, onMounted, ref } from 'vue';
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import useProcessControl from "@/hooks/useProcessControl";
import { CardDirection, CardType, CardList } from "@/constant";
import { ShuoList } from "@/components/content/shuoshuo";
import { codeConfig } from "@/config/program";
import { shuoContext } from "@/components/content/shuoshuo/businessTs/shuoContext";
import { Wait } from "@/components/general/popup";
import resource from "@/config/resource";
import { writerMeta } from "@/router/help";
import { metaInfo } from "@/config/site";

export default defineComponent({
  name: "Shuoshuo",
  components: { ShuoList, Wait },
  beforeRouteEnter: () => {
    writerMeta(metaInfo.shuoshuo);
  },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let page = ref(1);
    let pageSize = ref(6);

    let show = ref(true);
    let isFail = ref(false);
    let loadShow = ref(false);
    let endLock = false;
    let refreshSentry = ref(0);

    async function getShuoshuoList() {
      return await $api.getShuoshuoList({
        page: page.value, 
        pageSize: pageSize.value
      }).then(({code, msg, data}) => {
        if(code == codeConfig.success) {
          if(!data.length) {
            $process.tipShow.info("已经是最后一篇说说啦");
            endLock = true;
          }
          shuoContext.init(data);
          return true;
        } else {
          $process.tipShow.error(msg);
          return false;
        }
      })
    }

    async function loadData() {
      loadShow.value = true;
      page.value ++;
      await getShuoshuoList().then((flag) => {
        if(!flag) {
          $process.tipShow.error("获取说说失败");
        } else if(!endLock) {
          $process.tipShow.success("获取说说成功");
          refreshSentry.value = refreshSentry.value + 1;
          page.value ++;
        }
        page.value --;
        loadShow.value = false;
      });
    }

    async function initData() {
      if(shuoContext.data.length) {
        show.value = false;
        return;
      }
      show.value = true;
      isFail.value = false;
      await getShuoshuoList().then((flag) => {
        show.value = isFail.value = !flag;
      });
    }

    onMounted(() => {
      initData();
    })

    onActivated(() => {
      useProcessControl(true, {
        direction: CardDirection.row,
        cardType: CardType.CardList,
        cardList: CardList.ShuoCardList
      }, true);
    })

    return {
      show,
      isFail,
      loadShow,
      loadIcon: resource.load,
      refreshSentry,
      loadData
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.shuoshuo {
  width: 100%;
  .loadBtnWait {
    width: 100px;
    height: 40px;
    border-radius: 5px;
    box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    margin: 10px auto;
    .loadBtn {
      color: $normal;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      img {
        display: block;
        width: 20px;
        height: 20px;
      }
    }
  }
}
</style>