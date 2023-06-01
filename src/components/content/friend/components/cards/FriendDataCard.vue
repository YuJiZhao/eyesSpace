<template>
  <standard-card title="友链数据" :icon="icon">
    <Wait :show="show" :fail="isFail" height="100px">
      <div class="card friendDataCard">
        <ul>
          <li v-for="item in friendDataCardConfig" :key="item.name">
            <div>{{ item.title }} :</div>
            <div>{{ friendData[item.name] }}</div>
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
import { friendDataCardConfig } from "@/components/content/friend/config";
import { Wait } from "@/components/general/popup";

export default defineComponent({
  components: { StandardCard, Wait },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let show = ref(true);
    let isFail = ref(false);
    let friendData = ref({
        validChain: 10,
        invalidChain: 1,
        verifyingChain: 3
    });

    async function getFriendListInfo() {
      show.value = true;
      isFail.value = false;
      await $api.getFriendListData().then(({code, data}) => {
        if(code == codeConfig.success) {
          friendData.value = data;
          show.value = false;
        } else {
          $process.tipShow.error("友链数据获取失败");
          isFail.value = true;
        }
      });
    }

    onMounted(() => {
      getFriendListInfo();
    })
    
    return {
      icon: resource.data,
      friendDataCardConfig,
      friendData,
      show,
      isFail
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.friendDataCard {
  li {
    display: flex;
    justify-content: space-between;
    margin-bottom: 5px;
    color: $normal;
  }
}
</style>