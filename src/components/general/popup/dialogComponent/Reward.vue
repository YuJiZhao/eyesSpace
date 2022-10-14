<template>
  <base-dialog>
    <div class="reward">
      <div class="payType">
        <img :src="alipayImg" @click="jumpPage(alipayImg)">
        <div class="name">支付宝</div>
      </div>
      <div class="payType">
        <img :src="wepayImg" @click="jumpPage(wepayImg)">
        <div class="name">微信</div>
      </div>
    </div>
  </base-dialog>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import BaseDialog from "./BaseDialog.vue";
import resource from "@/config/resource";

export default defineComponent({
  components: { BaseDialog },
  setup() {
    function jumpPage(path: string) {
      window.open(path);
    }

    return {
      alipayImg: resource.alipay,
      wepayImg: resource.wepay,
      jumpPage
    };
  },
});
</script>

<style lang="scss" scoped>
.reward {
  display: flex;
  justify-content: space-between;
  .payType {
    width: 50%;
    img {
      display: block;
      width: 90%;
      margin: 0 auto;
      cursor: pointer;
    }
    .name {
      text-align: center;
    }
  }
}
</style>
