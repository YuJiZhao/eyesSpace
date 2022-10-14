<template>
  <div class="funcBtn">
    <div class="functionBtn" @click="open">
      <img :src="funcBtnIcon">
    </div>
    <base-dialog v-if="isShowDialog" @close="close">
      <div class="funcPop">
        <div class="data">
          <div class="title">数据</div>
          <div class="content">
            <div>播放数: {{musicData.views}}</div>
            <div>点赞数: {{musicData.likes}}</div>
            <div>评论数: {{musicData.comments}}</div>
          </div>
        </div>
        <div class="funcBar">
          <div class="title">功能</div>
          <div class="funcList">
            <icon-btn :key="likeBtnSentry" class="btn" word="点赞" :icon="musicData.isLike ? likeActiveIcon : likeIcon" @click="doUserLike"/>
            <icon-btn class="btn" word="评论" :icon="commentIcon" @click="doComment" />
            <icon-btn :key="rangeBtnSentry" class="btn" :word="rangeWord[rangeWordSwitch]" :icon="rangeIcon" @click="changeRange" />
          </div>
        </div>
        <div class="copyRight">如有侵权，请联系站长删除</div>
      </div>
    </base-dialog>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, ref, watch, inject, onMounted } from "vue";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import BaseDialog from "@/components/general/popup/dialogComponent/BaseDialog.vue";
import resource from "@/config/resource";
import IconBtn from "@/components/general/button/IconBtn.vue";
import musicProcess from "@/components/content/music/businessTs/musicProcess";
import { musicContext } from "@/components/content/music/businessTs/musicContext";
import { codeConfig } from "@/config/program";
import { musicContext as musicContextConfig } from "@/components/content/music/config";

export default defineComponent({
  components: { BaseDialog, IconBtn },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let isShowDialog = ref(false);
    let musicData = reactive({
      views: 0,
      likes: 0,
      comments: 0,
      isLike: false
    });
    let rangeWord = ["页内播放", "全站播放"];
    let rangeWordSwitch = ref(0);
    let likeBtnSentry = ref(0);
    let rangeBtnSentry = ref(0);

    function open() {
      isShowDialog.value = true;
    }

    function close() {
      isShowDialog.value = false;
    }

    function doComment() {
      $process.alertShow({
        title: "服务终止",
        content: "评论功能暂未上线，敬请期待"
      });
    }

    async function doUserLike() {
      await $api.doMusicUserLike({id: musicContext.data.id!}).then(({code, msg}) => {
        if(code == codeConfig.success) {
          $process.tipShow.success(musicData.likes ? "取消点赞成功" : "点赞成功");
          likeBtnSentry.value += 1;
          musicData.likes += musicData.isLike ? -1 : 1;
          musicData.isLike = !musicData.isLike;
        } else {
          $process.tipShow.error(musicData.likes ? "取消点赞操作失败" : "点赞操作失败");
        }
      })
    }

    function changeRange() {
      if(localStorage.getItem(musicContextConfig.storageRangeKey)) {
        localStorage.removeItem(musicContextConfig.storageRangeKey);
        rangeWordSwitch.value = 0;
        rangeBtnSentry.value++;
      } else {
        localStorage.setItem(musicContextConfig.storageRangeKey, musicContextConfig.storageRangeValue);
        rangeWordSwitch.value = 1;
        rangeBtnSentry.value++;
      }
      musicProcess.rangeNotice();
    }

    watch(
      () => musicProcess.initSentry.value,
      () => {
        musicData.views = musicContext.data.views!;
        musicData.likes = musicContext.data.likes!;
        musicData.comments = musicContext.data.comments!;
        musicData.isLike = musicContext.data.isLike!;
      }
    )

    onMounted(() => {
      rangeWordSwitch.value = localStorage.getItem(musicContextConfig.storageRangeKey) ? 1 : 0;
    })

    return {
      funcBtnIcon: resource.function,
      likeIcon: resource.like,
      likeActiveIcon: resource.likeActive,
      commentIcon: resource.comment,
      rangeIcon: resource.range,
      isShowDialog,
      musicData,
      rangeWord,
      rangeWordSwitch,
      likeBtnSentry,
      rangeBtnSentry,
      open,
      close,
      doComment,
      doUserLike,
      changeRange
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.funcBtn {
  .functionBtn {
    width: 35px;
    height: 35px;
    box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    border-radius: 50%;
    cursor: pointer;
    position: fixed;
    right: 10px;
    top: 50%;
    img {
      width: 25px;
      height: 25px;
      display: block;
      margin: 0 auto;
      transform: translateY(5px);
    }
  }
  .funcPop {
    color: $normal;
    .title {
      color: $title;
      margin: 10px 0;
    }
    .funcList {
      display: flex;
      justify-content: center;
      .btn {
        margin-right: 10px;
      }
    }
    .data {
      .content {
        display: flex;
        justify-content: center;
        div {
          width: 95px;
          text-align: center;
          margin-right: 10px;
        }
      }
    }
    .copyRight {
      height: 30px;
      line-height: 30px;
      margin-top: 30px;
    }
  }
}

@media screen and (min-width: 800px) {
  .funcBtn {
    .functionBtn {
      width: 50px;
      height: 50px;
      box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
      -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
      -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
      border-radius: 50%;
      cursor: pointer;
      position: fixed;
      right: 30px;
      top: 50%;
      transform: translateY(-50%);
      img {
        width: 40px;
        height: 40px;
        display: block;
        margin: 0 auto;
        transform: translateY(5px);
      }
    }
  }
}
</style>