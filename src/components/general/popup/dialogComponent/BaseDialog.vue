<template>
  <transition name="baseDialogAnimate">
    <div class="baseDialog">
      <div class="close" v-if="closeSwitch" @click="close">x</div>
      <div class="title" v-if="titleSwitch">{{title}}</div>
      <slot />
    </div>
  </transition>
</template>

<script lang="ts">
import { defineComponent, inject, onMounted } from "vue";
import { ProcessInterface } from "@/d.ts/plugin";

export default defineComponent({
  props: {
    title: String,
    close: Boolean
  },
  emits: ["close"],
  setup(props, ctx) {
    const $process = inject<ProcessInterface>("$process")!;

    function close() {
      $process.dialogHide();
      $process.maskHide();
      ctx.emit("close");
    }

    onMounted(() => {
      $process.maskShow(close);
    })

    return {
      closeSwitch: props.close,
      titleSwitch: props.title != null,
      title: props.title,
      close
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.baseDialogAnimate-enter-active {
  animation: fadeIn 1s;
}
.baseDialogAnimate-leave-active {
  animation: fadeOut 1s;
}

.baseDialog {
  width: 500px;
  padding: 20px;
  background: rgba($white, 0.9);
  border-radius: 10px;
  box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  position: fixed;
  z-index: 1000;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  .close {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    position: absolute;
    top: 15px;
    right: 15px;
    color: rgba($black, 0.6);
    font-size: 20px;
    text-align: center;
    line-height: 20px;
    cursor: pointer;
  }
  .title {
    height: 20px;
    font-size: 18px;
    line-height: 20px;
    margin-bottom: 20px;
  }
}

@media screen and (max-width: 800px) {
  .baseDialog {
    width: 80% !important;
    min-width: 350px;
  }
}
</style>
