<template>
  <div class="funcBar">
      <div v-for="item in videoFuncBar" :key="item.name" @click="funcObject[item.clickFunc]">
        <Wait class="btn" :class="item.name" :show="show" :fail="isFail" size="20px" @callback="initData" failSize="30px">
          <!-- // TODO: 记得优化代码 -->
          <img :src="(typeof item.icon == 'string') ? item.icon : item.icon[isLike ? 1 : 0]" />
          <div class="word">{{(typeof item.word == 'string') ? item.word : item.word[isChangeClearWord ? 1 : 0]}}</div>
        </Wait>
      </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, inject, watch, nextTick, onActivated, onDeactivated, WatchStopHandle } from "vue";
import { ProcessInterface, ApiObject, WindowInterface } from "@/d.ts/plugin";
import videoProcess from "@/components/content/video/businessTs/videoProcess";
import { videoContext } from "@/components/content/video/businessTs/videoContext";
import { videoContext as videoContextConfig } from "@/components/content/video/config";
import { videoFuncBar } from "@/components/content/video/config";
import useProcessControl from "@/hooks/useProcessControl";
import { codeConfig } from "@/config/program";
import { Wait } from "@/components/general/popup";
import { videoSideCards } from "@/components/content/video/config";
import { siteConfig } from "@/config/program";

export default defineComponent({
  components: { Wait },
  emits: ["openAnnounce"],
  setup(props, ctx) {
    const $process = inject<ProcessInterface>("$process")!;
    const $window = inject<WindowInterface>("$window")!;
    const $api = inject<ApiObject>("$api")!;

    let isLike = ref(false);
    let isShowSideBar = true;
    let isChangeClearWord = ref(false);
    let watchSwatch: WatchStopHandle;
    let changeSwatch: WatchStopHandle;

    let funcObject = {
      doLike: () => {
        $api.doVideoUserLike({id: encodeURIComponent(videoContext.data.id!)}).then(({code, msg}) => {
          if(code == codeConfig.success) {
            isLike.value = !isLike.value;
            videoProcess.likesNumSentry.value = !videoProcess.likesNumSentry.value;
            if(isLike.value) {
              sessionStorage.setItem(videoContextConfig.videoStep, String(1));
              $process.tipShow.success("点赞成功");
            } else {
              sessionStorage.setItem(videoContextConfig.videoStep, String(-1));
              $process.tipShow.success("取消点赞成功");
            }
            sessionStorage.setItem(
              videoContextConfig.videoLikeNum, 
              sessionStorage.getItem(videoContextConfig.videoLikeNum) 
                ? String(Number(sessionStorage.getItem(videoContextConfig.videoLikeNum)) + 1) 
                : "1"
            );
          } else {
            $process.tipShow.error(msg);
          }
        });
      },
      doComment: () => {
        $process.alertShow({
          title: "操作终止",
          content: "评论功能暂未上线，尽请期待"
        });
      },
      changeVideo: () => {
        if(!videoProcess.videoVariable.value) return;
        sessionStorage.removeItem(videoContextConfig.videoLikeNum);
        videoProcess.doChangeVideo();
        videoProcess.videoVariable.value = false;
      },
      doFullScreen: () => {
        videoProcess.doFullScreen();
      },
      doClear: () => {
        if(document.querySelector(".clearBtn")!.classList.contains("btn_disabled")) return;
        isShowSideBar = !isShowSideBar;
        isChangeClearWord.value = !isChangeClearWord.value;
        useProcessControl(true, isShowSideBar ? videoSideCards : false, false);
      },
      openAnnounce: () => {
        ctx.emit("openAnnounce");
      }
    }

    function initData() {
      isLike.value = videoContext.data.isLike!;
    }

    function btnControl(selector: string, flag: boolean) {
      nextTick(() => {
        if(flag) {
          document.querySelector(selector)!.classList.add("btn_disabled");
        } else {
          document.querySelector(selector)!.classList.remove("btn_disabled")
        }
      })
    }

    onActivated(() => {
      watchSwatch = watch(
        () => $window.width.value,
        (value) => { btnControl(".clearBtn", value < siteConfig.mpThreshold); },
        { immediate: true }
      );

      changeSwatch = watch(
        () => videoProcess.videoVariable.value,
        (value) => { btnControl(".changeBtn", !value); },
        { immediate: true }
      );
    })

    onDeactivated(() => {
      watchSwatch();
      changeSwatch();
    })

    return {
      show: videoProcess.cardInitLoad,
      isFail: videoProcess.cardInitFail,
      funcObject,
      videoFuncBar,
      isLike,
      isChangeClearWord,
      initData
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.funcBar {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  margin-top: 20px;
  .btn {
    box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100px;
    height: 40px;
    border-radius: 5px;
    margin-bottom: 10px;
    cursor: pointer;
    img {
      display: block;
      width: 25px;
      height: 25px;
      margin-right: 5px;
    }
    .word {
      font-size: 15px;
      text-align: center;
    }
  }
}

.btn_disabled {
  cursor: default !important;
  opacity: 0.6;
}
</style>