<template>
  <div class="details">
    <shuo-item
      class="shuoshuoItem"
      :key="shuoSentry"
      :id="shuoData.id"
      :content="shuoData.content"
      :picList="shuoData.picList"
      :views="shuoData.views"
      :comments="shuoData.comments"
      :createTime="shuoData.createTime"
    />
    <Comment :objectId="shuoshuoId" :apiType="apiType" />
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, inject, ref, reactive } from "vue";
import { useRouter } from "vue-router";
import useProcessControl from "@/hooks/useProcessControl";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { CardDirection, CardList, CardType } from "@/constant";
import { codeConfig } from "@/config/program";
import { goBoth, GoBothType } from "@/hooks/useGoBoth";
import Comment from "@/components/general/comment/Comment.vue";
import { CommentApiType } from "@/constant";
import ShuoItem from "@/components/content/shuoshuo/components/ShuoItem.vue"; 
import { buildMeta, writerMeta } from "@/router/help";
import { siteContext } from "@/config/site";

export default defineComponent({
  components: { Comment, ShuoItem },
  setup() {
    const router = useRouter();
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let shuoshuoId = ref(router.currentRoute.value.query.id);
    let shuoData = reactive({
        id: "",
        content: "",
        picList: [],
        views: 0,
        comments: 0,
        createTime: ""
    });
    let shuoSentry = ref(0);

    function setMeta() {
      writerMeta(buildMeta(
        siteContext.siteName + " | 说说",
        shuoData.content,
        shuoData.content
      ));
    }

    async function getShuoshuoInfo() {
        $api.getShuoshuoSingleInfo({id: encodeURIComponent(<string>shuoshuoId.value)}).then(({code, msg, data}) => {
            if(code == codeConfig.success) {
              shuoData.id = data.id;
              shuoData.content = data.content;
              shuoData.picList = data.picList;
              shuoData.views = data.views;
              shuoData.comments = data.comments;
              shuoData.createTime = data.createTime;
              setMeta();
              shuoSentry.value++;
            } else {
              $process.tipShow.error(msg);
            }
        })
    }

    onMounted(() => {
      goBoth(GoBothType.TopSpeed);
      getShuoshuoInfo();
      useProcessControl(true, {
        direction: CardDirection.row,
        cardType: CardType.CardList,
        cardList: CardList.ShuoCardList
      }, true);
    });

    return {
      shuoData,
      shuoshuoId,
      shuoSentry,
      apiType: CommentApiType.shuoshuo,
    };
  },
});
</script>

<style lang="scss" scoped>
.details {
  width: 100%;
}
</style>