<template>
  <div class="commentItemChild">
    <div class="replyItem" v-for="item in replyData" :key="item.id" :id="idPrefix + item.id">
      <div class="header">
        <div class="leftBox">
          <div class="avatar" :style="{backgroundImage: 'url(' + item.avatar + ')'}"></div>
          <div class="infoBox">
            <div class="name">
              {{item.name}} 
              <span class="replyObject">回复 <a :href="'#' + idPrefix + item.replyId">@{{item.replyName}}</a></span>
            </div>
            <div class="date">{{item.createTime}}</div>
          </div>
        </div>
        <div class="rightBox">
          <div class="delete" v-if="item.owner" @click="doDelComment(item.id)">x</div>
        </div>
      </div>
      <div class="content">
        <comment-md :content="item.comment" :commentId="item.id" />
      </div>
      <div class="footer">
        <a class="comments" :href="'#' + inputId" @click="goComment(item.id, item.name, landlord)">
          <img class="commentIcon" :src="commentIcon">
          <div class="commentNum">{{item.comments}}</div>
        </a>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { commentContext } from "@/components/general/comment/config";
import CommentMd from "./CommentMd.vue";
import resource from "@/config/resource";
import { goComment } from "@/components/general/comment/businessTs/commentProcess";
import { delCommentProcess } from "@/components/general/comment/businessTs/commentProcess";

export default defineComponent({
  components: { CommentMd },
  props: ["replyData", "landlord"],
  setup(props) {
    return {
      replyData: props.replyData,
      landlord: props.landlord,
      idPrefix: commentContext.idPrefix,
      inputId: commentContext.inputId,
      commentIcon: resource.comment,
      goComment: goComment.clickFunc,
      doDelComment: delCommentProcess.doDelComment
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.commentItemChild {
  .replyItem {
    padding-top: 20px;
    margin-top: 15px;
    border-top: 1px dashed rgba($color: $black, $alpha: 0.5);
    .header {
      height: 30px;
      display: flex;
      justify-content: space-between;
      margin-bottom: 10px;
      .leftBox {
        display: flex;
        justify-content: start;
        .avatar {
          width: 30px;
          height: 30px;
          background-size: 100% 100%;
          border-radius: 50%;
          margin-right: 10px;
        }
        .infoBox {
          .name {
            height: 15px;
            line-height: 15px;
            overflow: hidden;
            .replyObject > a {
              color: $normal;
              text-decoration: none;
            }
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
          line-height: 20px;
          margin-left: 5px;
        }
      }
    }
  }
}
</style>