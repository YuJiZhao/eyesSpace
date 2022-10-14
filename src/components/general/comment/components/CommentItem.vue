<template>
  <div class="commentItem" :id="idPrefix + commentData.id">
    <div class="header">
      <div class="leftBox">
        <div class="avatar"></div>
        <div class="infoBox">
          <div class="name">{{commentData.name}}</div>
          <div class="date">{{commentData.createTime}}</div>
        </div>
      </div>
      <div class="rightBox">
        <div class="delete" v-if="commentData.owner" @click="doDelComment(commentData.id)">x</div>
      </div>
    </div>
    <div class="content">
      <CommentMd :content="commentData.comment" :commentId="commentData.id" />
    </div>
    <div class="footer">
      <a class="comments" :href="'#' + inputId" @click="goComment(commentData.id, commentData.name, commentData.id)">
        <img class="commentIcon" :src="commentIcon">
        <div class="commentNum">{{commentData.comments}}</div>
      </a>
    </div>
    <div class="children" v-if="commentData.children.length">
      <comment-item-child :replyData="commentData.children" :landlord="commentData.id"/>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import CommentMd from "./CommentMd.vue";
import resource from "@/config/resource";
import CommentItemChild from "./CommentItemChild.vue";
import { commentContext } from "@/components/general/comment/config";
import { goComment } from "@/components/general/comment/businessTs/commentProcess";
import { delCommentProcess } from "@/components/general/comment/businessTs/commentProcess";

export default defineComponent({
  components: { CommentMd, CommentItemChild },
  props: ["commentData"],
  setup(props) {
    return {
      commentData: props.commentData,
      avatar: "url('" + props.commentData.avatar + "')",
      commentIcon: resource.comment,
      idPrefix: commentContext.idPrefix,
      inputId: commentContext.inputId,
      goComment: goComment.clickFunc,
      doDelComment: delCommentProcess.doDelComment
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.commentItem {
  width: calc(100% - 5px);
  margin: 0 auto;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  color: $normal;
  .header {
    height: 40px;
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
    .leftBox {
      display: flex;
      justify-content: start;
      .avatar {
        width: 40px;
        height: 40px;
        background-image: v-bind(avatar);
        background-size: 100% 100%;
        border-radius: 50%;
        margin-right: 10px;
      }
      .infoBox {
        .name {
          height: 25px;
          line-height: 25px;
        }
        .date {
          height: 15px;
          line-height: 15px;
          font-size: 12px;
        }
      }
    }
    .rightBox {
      .delete {
        width: 20px;
        height: 20px;
        text-align: center;
        line-height: 20px;
        cursor: pointer;
        transition: 0.3s;
        &:hover {
          color: rgb(246, 108, 107);
        }
      }
    }
  }
  .footer {
    height: 25px;
    display: flex;
    justify-content: flex-end;
    .comments {
      display: flex;
      align-items: center;
      text-decoration: none;
      color: $normal;
      .commentIcon {
        display: block;
        width: 20px;
        height: 20px;
      }
      .commentNum {
        line-height: 25px;
        margin-left: 5px;
      }
    }
  }
  .children {
    margin: 10px 0;
  }
}
</style>