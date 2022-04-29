<template>
  <transition name="annAnimate">
    <div class="announcement" v-if="status">
      <div class="close" @click="close">x</div>
      <div class="title">{{ msg.title }}</div>
      <div class="value">{{ msg.content }}</div>
    </div>
  </transition>
</template>

<script lang="ts">
/**
 * 公告组件
 * @desc 每个用户初次使用时打开一次，公告更新则刷新
 */
import { defineComponent, ref, reactive, watch, inject } from "vue";
import { PopupType } from "@/d.ts/modules";
import "animate.css";

export default defineComponent({
  components: {},
  setup(props, { emit }) {
    const $popup = inject<PopupType>("$popup")!;

    let status = ref($popup.announcementStatus.value);
    let msg = reactive({
      title: $popup.announcementMsg.title,
      content: $popup.announcementMsg.content,
    });

    watch(
      () => $popup.announcementStatus.value,
      value => {
        status.value = value;
        msg.title = $popup.announcementMsg.title;
        msg.content = $popup.announcementMsg.content;
      }
    );

    function close() {
      $popup.announcementHide();
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
.annAnimate-enter-active {
  animation: backInDown 1s;
}
.annAnimate-leave-active {
  animation: backOutUp 1s;
}
.announcement {
  width: 8rem;
  height: 5rem;
  border-radius: 0.2rem;
  background: rgb(206, 216, 217);
  position: fixed;
  z-index: 99;
  top: 50px;
  left: calc(50% - 4rem);
  font-size: 0.4rem;
  .close {
    width: 0.5rem;
    height: 0.5rem;
    border-radius: 50%;
    background: red;
    position: absolute;
    top: 0.2rem;
    right: 0.2rem;
    color: #fff;
    text-align: center;
    line-height: 0.5rem;
    cursor: pointer;
  }

  .title {
    // position: absolute;
    // top: 1rem;
  }

  .value {
  }
}
</style>
