<template>
  <div class="commentInput" :id="inputId">
    <emoji-input ref="inputRef" @changeValue="changeValue" />
    <div class="publishBox">
        <div class="uploadImg" @click="uploadImg">上传图片</div>
        <div class="publishBtn" @click="publish">发表评论</div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, inject, onMounted } from "vue";
import { UserInterface, ProcessInterface } from "@/d.ts/plugin";
import EmojiInput from "@/components/general/input/EmojiInput.vue";
import { codeConfig, urlConfig } from "@/config/program";
import { publishComment } from "@/server/composition/comment";
import { CommentApiType } from "@/constant";
import { siteContext } from "@/config/site";
import { commentContext } from "@/components/general/comment/config";
import { goComment, publishCommentProcess } from "@/components/general/comment/businessTs/commentProcess";
import { PublishCommentInterface } from "@/d.ts/server/api";

export default defineComponent({
  components: { EmojiInput },
  setup() {
    const $user = inject<UserInterface>("$user")!;
    const $process = inject<ProcessInterface>("$process")!;

    let objectId = inject<number>("objectId")!;
    let apiType = inject<CommentApiType>("apiType")!;

    let originalComment = "";
    let comment = "";
    let inputRef = ref();
    let replyId: null | number = null;

    function uploadImg() {
        window.open(urlConfig.picbedUrl);
    }

    function changeValue(originalText: string, text: string) {
        originalComment = originalText;
        comment = text;
    }

    function publishCheck() {
        if(!$user.status) {
            $process.alertShow({
                title: "发表失败",
                content: "仅登录用户可评论，请先登录注册"
            });
            return false;
        }
        if(comment.trim() == "") {
            $process.tipShow.warn("评论不能为空");
            return false;
        }
        if(comment.length > siteContext.commentMaxLen) {
            $process.tipShow.warn(`评论长度不能超过${siteContext.commentMaxLen}字`);
            return false;
        }
        return true;
    }

    function analysisComment() {
        let exp = /^\[#[0-9]+@.{2,}\]：/;
        let replyFlag = comment.match(exp);
        if(replyFlag != null) {
            if(comment.replace(exp, "").trim() == "") {
                $process.tipShow.warn("评论不能为空");
                return false;
            }
        } else {
            return true;
        }

        let separator = replyFlag[0].indexOf("@");
        let inputReplyId = Number(replyFlag[0].substring(2, separator));
        let endSeparator = replyFlag[0].lastIndexOf("]：");
        let inputReplyName = replyFlag[0].substring(separator + 1, endSeparator);
        
        if(inputReplyId == goComment.objectId.value && inputReplyName == goComment.replyName.value) {
            replyId = inputReplyId;
            comment = comment.replace(exp, "");
        }
        return true;
    }

    async function publish() {
        replyId = null;
        if(!publishCheck()) return;
        if(!analysisComment()) return;
        let req: PublishCommentInterface = {
            objectId,
            originalComment,
            comment
        };
        if(replyId != null) {
            req.replyId = replyId;
            if(goComment.landlord.value) {
                req.landlord = goComment.landlord.value;
            }
        }
        
        await publishComment(apiType, req).then(({code, msg}) => {
            if(code == codeConfig.success) {
                $process.tipShow.success("发表成功");
                comment = "";
                inputRef.value.clearText();
                replyId = null;
                goComment.landlord.value = undefined;
                publishCommentProcess.doPublishComment();
            } else {
                $process.tipShow.error(msg);
            }
        })
    }

    return {
        inputId: commentContext.inputId,
        inputRef,
        comment,
        uploadImg,
        publish,
        changeValue
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.commentInput {
    .publishBox {
        display: flex;
        justify-content: flex-end;
        margin-top: 20px;
        margin-right: 5px;
        .uploadImg, .publishBtn {
            width: 80px;
            height: 30px;
            color: $normal;
            line-height: 30px;
            text-align: center;
            margin-right: 5px;
            border-radius: 5px;
            box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
            -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
            -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
            cursor: pointer;
        }
        .publishBtn {
            margin-left: 10px;
        }
    }
}
</style>