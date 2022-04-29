<template>
  <transition name="alertAnimate">
    <div class="alert" v-if="status">
      <div class="title">
        <div class="close" @click="close">×</div>
        <div class="content">{{ msg.title }}</div>
      </div>
      <div class="value">{{ msg.content }}</div>
    </div>
  </transition>
</template>

<script lang="ts">
/**
 * 提示组件
 */
import { defineComponent, ref, reactive, watch, inject } from "vue";
import { PopupType } from "@/d.ts/modules";
import "animate.css";

export default defineComponent({
  components: {},
  setup() {
    const $popup = inject<PopupType>("$popup")!;

    let status = ref($popup.alertStatus.value);
    let msg = reactive({
      title: $popup.alertMsg.title,
      content: $popup.alertMsg.content,
    });

    watch(
      () => $popup.alertStatus.value,
      value => {
        status.value = value;
        msg.title = $popup.alertMsg.title;
        msg.content = $popup.alertMsg.content;
      }
    );

    function close() {
      $popup.alertHide();
    }

    return {
      status,
      msg,
      close
    };
  },
});
</script>

<style lang="scss" scoped>
.alertAnimate-enter-active {
  animation: bounceInRight 1s;
}
.alertAnimate-leave-active {
  animation: bounceOutRight 1s;
}
.alert {
  width: 250px;
  height: 90px;
  padding: 10px;
  box-sizing: border-box;
  border-radius: 10px;
  box-shadow: rgb(213, 213, 250) 0 0 5px;
  background: #fff;
  position: fixed;
  z-index: 99;
  top: 50px;
  right: 50px;
  .title {
    display: flex;
    height: 20px;
    margin-top: 5px;
    .close {
      width: 20px;
      height: 20px;
      border-radius: 50%;
      background: rgb(246, 108, 107);
      color: #fff;
      text-align: center;
      line-height: 20px;
      cursor: pointer;
    }
    .content {
      line-height: 20px;
      margin-left: 10px;
      font-size: 15px;
    }
  }
  .value {
    margin-left: 30px;
    margin-top: 10px;
    font-size: 13px;
  }
}
</style>
