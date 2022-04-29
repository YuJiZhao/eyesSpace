<template>
  <transition name="alertAnimate">
    <div class="alert" v-if="status">
      <div class="title">{{ msg.title }}</div>
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
  setup(props, { emit }) {
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

    return {
      status,
      msg,
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
  width: 5rem;
  height: 5rem;
  background: #fff;
  position: fixed;
  z-index: 99;
  top: 50px;
  right: 50px;
}
</style>
