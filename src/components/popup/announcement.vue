<template>
  <transition name="annAnimate">
    <div class="announcement" v-if="status">
      <div class="close" @click="close">×</div>
      <div class="content">
        <div class="title">{{ msg.title }}</div>
        <div class="info">
          <div>发布人：{{ info.author }}</div>
          <div>发布时间：{{ info.time }}</div>
        </div>
        <div class="value">{{ msg.content }}</div>
      </div>
    </div>
  </transition>
</template>

<script lang="ts">
/**
 * 公告组件
 * @desc 每个用户初次使用时打开一次，公告更新则刷新
 */
import { defineComponent, ref, reactive, watch, inject } from "vue";
import { PopupType, CVType } from "@/d.ts/modules";
import { errorMsg } from "@/config/websiteConfig";
import "animate.css";

export default defineComponent({
  components: {},
  setup() {
    const $popup = inject<PopupType>("$popup")!;
    const $context = inject<CVType>("$context")!;

    let status = ref($popup.announcementStatus.value);
    let info = reactive({
      author: "",
      time: "",
    });
    let msg = reactive({
      title: $popup.announcementMsg.title,
      content: $popup.announcementMsg.content,
    });

    watch(
      () => $popup.announcementStatus.value,
      (value) => {
        status.value = value;
        if (value == true) {
          // TODO：这种解决异步的方法不完美
          info.author = $context.data.nick || errorMsg.contextError;
          info.time = $context.data.ann_time || errorMsg.contextError;
          msg.title = $popup.announcementMsg.title;
          msg.content = $popup.announcementMsg.content;
        }
      }
    );

    function close() {
      $popup.announcementHide();
    }

    return {
      status,
      msg,
      info,
      close,
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
  width: 500px;
  height: 300px;
  box-sizing: border-box;
  padding: 10px;
  box-shadow: rgb(213, 213, 250) 0 0 5px;
  border-radius: 10px;
  background: #fff;
  position: fixed;
  z-index: 99;
  top: 50px;
  left: calc(50% - 250px);
  font-size: 16px;
  .close {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    background: rgb(246, 108, 107);
    position: absolute;
    top: 10px;
    right: 10px;
    color: #fff;
    text-align: center;
    line-height: 20px;
    cursor: pointer;
  }
  .content {
    margin-top: 15px;
    .title {
      font-size: 25px;
      font-weight: bolder;
      text-align: center;
    }
    .info {
      margin-top: 10px;
      opacity: 0.7;
      font-size: 10px;
    }
    .value {
      margin-top: 10px;
    }
  }
}
</style>
