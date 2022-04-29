<template>
  <div class="reward" v-if="status">
    <div class="box">
      <div class="cards">
        <div class="wxpay">
          <a :href="payImg.wxpay">
            <img :src="payImg.wxpay" />
          </a>
        </div>
        <div class="alipay">
          <a :href="payImg.alipay">
            <img :src="payImg.alipay" />
          </a>
        </div>
      </div>
      <div class="words">感谢您的打赏!</div>
    </div>
  </div>
</template>

<script lang="ts">
/**
 * 打赏
 */
import { defineComponent, inject, reactive, ref, watch } from "vue";
import { PopupType, CVType } from "@/d.ts/modules";
import { config } from "@/config/index";

export default defineComponent({
  setup() {
    const $popup = inject<PopupType>("$popup")!;
    const $context = inject<CVType>("$context")!;

    let status = ref($popup.rewardStatus.value);
    let payImg = reactive({
      wxpay: config.picUrl + $context.data.wechat_reward,
      alipay: config.picUrl + $context.data.alipay_reward,
    });

    watch(
      () => $popup.rewardStatus.value,
      (value) => {
        status.value = value;
      }
    );

    return {
      status,
      payImg,
    };
  },
});
</script>

<style lang="scss" scoped>
.reward {
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  z-index: 99;
  top: 0;
  left: 0;
  .box {
    
  }
}
</style>
