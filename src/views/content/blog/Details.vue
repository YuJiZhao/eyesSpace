<template>
  <div class="details">
    <head-meta />
    <md-editor />
    <func-bar />
    <reward-btn />
    <Comment :objectId="blogId" :apiType="apiType" />
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, inject, ref } from "vue";
import { useRouter } from "vue-router";
import useProcessControl from "@/hooks/useProcessControl";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { CardDirection, CardList, CardType } from "@/constant";
import { codeConfig } from "@/config/program";
import { MdEditor, HeadMeta, FuncBar, RewardBtn } from "@/components/content/blogDetail";
import useGoTop from "@/hooks/useGoTop";
import { blogDetailContext } from "@/components/content/blogDetail/businessTs/blogDetailContext";
import blogDetailProcess from "@/components/content/blogDetail/businessTs/blogDetailProcess";
import Comment from "@/components/general/comment/Comment.vue";
import { CommentApiType } from "@/constant";
import { buildMeta, writerMeta } from "@/router/help";
import { BlogDetailContextDataInterface } from "@/components/content/blogDetail/d.ts/blogDetailContext";

export default defineComponent({
  components: { MdEditor, HeadMeta, FuncBar, RewardBtn, Comment },
  setup() {
    const router = useRouter();
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let blogId = ref(router.currentRoute.value.params.id);

    function setMeta(data: BlogDetailContextDataInterface) {
      writerMeta(buildMeta(
        data.title,
        data.title + "，" + data.category + "，" + data.labels,
        data.title + "，" + data.category + "，" + data.labels + data.summary
      ));
    }

    async function getBlogInfo() {
      blogDetailProcess.cardInitLoad.value = true;
      blogDetailProcess.cardInitFail.value = false;
      await $api.getBlogInfo([blogId.value]).then(({code, msg, data}) => {
        if(code == codeConfig.success) {
          blogDetailContext.init(data);
          blogDetailProcess.cardInitLoad.value = false;
          setMeta(data);
        } else {
          $process.tipShow.error(msg);
          blogDetailProcess.cardInitFail.value = true;
        }
      })
    }

    onMounted(() => {
      useGoTop();
      getBlogInfo();
      useProcessControl(true, {
        direction: CardDirection.row,
        cardType: CardType.CardList,
        cardList: CardList.BlogDetailCardList,
        follow: true
      });
    });

    return {
      blogId,
      apiType: CommentApiType.blog,
    };
  },
});
</script>

<style lang="scss" scoped>
.details {
  width: 100%;
}
</style>