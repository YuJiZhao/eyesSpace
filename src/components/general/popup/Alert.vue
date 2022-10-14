<template>
  <transition name="alertAnimate">
    <div class="alert" v-show="status">
      <div class="title">
        <div class="close" @click="close">×</div>
        <div class="content">{{ msg.title }}</div>
      </div>
      <div class="value">{{ msg.content }}</div>
    </div>
  </transition>
</template>

<script lang="ts">
import { defineComponent, ref, reactive, watch, inject } from "vue";
import { ProcessInterface } from "@/d.ts/modules/process";

export default defineComponent({
  components: {},
  setup() {
    const $process = inject<ProcessInterface>("$process")!;

    let status = ref($process.alertStatus.value);
    let msg = reactive({
      title: "",
      content: "",
    });

    watch(
      () => $process.alertStatus.value,
      (value) => {
        status.value = value;
        if ((value = true)) {
          msg.title = $process.alertMsg.title;
          msg.content = $process.alertMsg.content;
        }
      }
    );

    function close() {
      $process.alertHide();
    }

    return {
      status,
      msg,
      close,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.alertAnimate-enter-active {
  animation: bounceInRight 0.8s;
}
.alertAnimate-leave-active {
  animation: bounceOutRight 0.8s;
}

.alert {
  width: 250px;
  min-height: 90px;
  padding: 10px;
  padding-bottom: 15px;
  box-sizing: border-box;
  border-radius: 10px;
  box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  background: #fff;
  position: fixed;
  z-index: 999;
  top: 60px;
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
      color: $title;
      font-size: 15px;
    }
  }
  .value {
    margin-left: 30px;
    margin-top: 10px;
    color: $normal;
    font-size: 13px;
  }
}

@media screen and (max-width: 800px) {
  .alert {
    right: 10px;
  }
}
</style>
