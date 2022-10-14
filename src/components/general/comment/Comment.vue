<template>
  <div class="comment">
    <comment-input />
    <div class="commentArea">
      <div class="title">评论区</div>
      <Wait :show="show" :fail="isFail" height="200px">
        <div class="commentList">
          <comment-item class="item" v-for="item in commentList" :key="item.id" :commentData="item" />
        </div>
      </Wait>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, provide, ref, inject, onMounted, watch } from "vue";
import CommentInput from "./components/CommentInput.vue";
import CommentItem from "./components/CommentItem.vue";
import { Wait } from "@/components/general/popup";
import { CommentItemInterface } from "@/components/general/comment/d.ts/api";
import { ProcessInterface } from "@/d.ts/plugin";
import { codeConfig } from "@/config/program";
import { getCommentList, delComment } from "@/server/composition/comment";
import { delCommentProcess, publishCommentProcess } from "@/components/general/comment/businessTs/commentProcess";

export default defineComponent({
  components: { CommentInput, CommentItem, Wait },
  props: ["objectId", "apiType", "showEmoji"],
  setup(props) {
    provide("objectId", props.objectId);
    provide("apiType", props.apiType);
    provide("showEmoji", props.showEmoji == null ? true : false);

    const $process = inject<ProcessInterface>("$process")!;

    let show = ref(true);
    let isFail = ref(false);
    let commentList = ref<Array<CommentItemInterface>>([]);

    async function getComment() {
      show.value = true;
      isFail.value = false;
      await getCommentList(props.apiType, {id: encodeURIComponent(props.objectId)}).then(({code, msg, data}) => {
        if(code == codeConfig.success) {
          commentList.value = data;
          show.value = false;
        } else {
          $process.tipShow.error(msg);
          isFail.value = true;
        }
      });
    }

    async function delSelectedComment(id: number) {
      await delComment(props.apiType, [id]).then(({code, msg}) => {
        if(code == codeConfig.success) {
          getComment();
          $process.tipShow.success("删除评论成功");
        } else {
          $process.tipShow.error(msg);
        }
      });
    }

    watch(
      () => delCommentProcess.delSentry.value,
      () => {
        delSelectedComment(delCommentProcess.delId.value)
      }
    )
    watch(
      () => publishCommentProcess.publishSentry.value,
      () => {
        getComment();
      }
    )

    onMounted(() => {
      getComment();
    })

    return {
      show,
      isFail,
      commentList
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.comment {
  #commentInput {
    margin-bottom: 20px;
  }
  .commentArea {
    .title {
      height: 30px;
      line-height: 30px;
      color: $normal;
      border-bottom: 1px solid rgba($color: #525151, $alpha: 0.3);
    }
    .commentList {
      margin: 20px 0;
      .item {
        margin-top: 30px;
      }
    }
  }
}
</style>