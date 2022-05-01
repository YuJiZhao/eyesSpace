<template>
  <div class="reward" v-if="status">
    <div class="mask" @click="close"></div>
    <div class="box">
      <div class="cards">
        <div class="payImg">
          <a :href="payImg.wxpay" target="_blank">
            <img :src="payImg.wxpay" />
          </a>
        </div>
        <div class="payImg">
          <a :href="payImg.alipay" target="_blank">
            <img :src="payImg.alipay" />
          </a>
        </div>
      </div>
      <div class="payName">
        <p>微信</p>
        <p>支付宝</p>
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
import { urlConfig, errorMsg } from "@/config/websiteConfig";
import errorImg from "@/assets/image/common/404.jpg";

export default defineComponent({
  setup() {
    const $popup = inject<PopupType>("$popup")!;
    const $context = inject<CVType>("$context")!;

    let status = ref($popup.rewardStatus.value);
    let payImg = reactive({
      wxpay: "",
      alipay: "",
    });

    watch(
      () => $popup.rewardStatus.value,
      (value) => {
        status.value = value;
        if (value == true) {
          payImg.wxpay = $context.data.wechat_reward != undefined ? (urlConfig.picUrl + $context.data.wechat_reward) : errorImg;
          payImg.alipay = $context.data.alipay_reward != undefined ? (urlConfig.picUrl + $context.data.alipay_reward) : errorImg;
        }
      }
    );

    function close() {
      $popup.rewardHide();
    }

    return {
      status,
      payImg,
      close,
    };
  },
});
</script>

<style lang="scss" scoped>
.mask {
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  z-index: 98;
  top: 0;
  left: 0;
}
.reward {
  .box {
    width: 400px;
    height: 250px;
    box-sizing: border-box;
    border-radius: 10px;
    padding: 10px;
    background: #fff;
    position: fixed;
    z-index: 99;
    top: 170px;
    left: calc(50% - 175px);
    .cards {
      display: flex;
      justify-content: space-around;
      img {
        width: 150px;
      }
    }
    .payName {
      display: flex;
      justify-content: space-around;
    }
    .words {
      text-align: center;
      line-height: 70px;
    }
  }
}
</style>
