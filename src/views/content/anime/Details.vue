<template>
  <div class="details">
    <anime-item class="animeItem" :data="animeData" :key="animeSentry" />
    <word-card :word="animeData.word" :key="animeSentry" />
    <Comment :objectId="animeId" :apiType="apiType" />
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, inject, ref, reactive } from "vue";
import { useRouter } from "vue-router";
import useProcessControl from "@/hooks/useProcessControl";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { CardDirection, CardList, CardType } from "@/constant";
import { codeConfig } from "@/config/program";
import Comment from "@/components/general/comment/Comment.vue";
import { CommentApiType } from "@/constant";
import { buildMeta, writerMeta } from "@/router/help";
import { AnimeItem } from "@/components/content/anime";
import { WordCard } from "@/components/content/animeDetail";

export default defineComponent({
  components: { Comment, AnimeItem, WordCard },
  setup() {
    const router = useRouter();
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let animeId = ref(router.currentRoute.value.params.id);
    let animeData = ref({
      title: "",
      introduce: "",
      word: ""
    });
    let animeSentry = ref(0);

    function setMeta() {
      writerMeta(buildMeta(
        "动漫 | " + animeData.value.title,
        animeData.value.introduce,
        animeData.value.word
      ));
    }

    async function getAnimeInfo() {
      $api.getAnimeInfo([animeId.value]).then(({code, msg, data}) => {
        if (code == codeConfig.success) {
          animeData.value = data;
          setMeta();
          animeSentry.value++;
        } else {
          $process.tipShow.error(msg);
        }
      })
    }

    onMounted(() => {
      getAnimeInfo();
      useProcessControl(true, {
        direction: CardDirection.row,
        cardType: CardType.CardList,
        cardList: CardList.AnimeCardList
      });
    });

    return {
      animeId,
      animeData,
      animeSentry,
      apiType: CommentApiType.anime,
    };
  },
});
</script>

<style lang="scss" scoped>
.details {
  width: 100%;
}
</style>