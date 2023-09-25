<template>
  <div class="sideCard">
    <div class="cards" v-if="type == CardType.Cards">
      <component class="card" v-for="item in cardChoices" :key="item" :is="cardComponents[item]"/>
    </div>
    <div class="cardList" v-if="type == CardType.CardList">
      <component :is="cardListComponents[cardListChoice]"/>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import { ProcessInterface } from "@/d.ts/plugin";
import { CardType } from "@/constant";
import { AnnounceCard, InfoCard, OwnerCard } from "@/components/general/card";
import { VideoCardList } from "@/components/content/video";
import { ShuoCardList } from "@/components/content/shuoshuo";
import { BlogCardList } from "@/components/content/blog";
import { BlogDetailCardList } from "@/components/content/blogDetail";
import { AnimeCardList } from "@/components/content/anime";
import { FriendCardList } from "@/components/content/friend";
import { VersionCardList } from "@/components/content/version";
import { JokeCardList } from "@/components/content/joke";

export default defineComponent({
  components: { 
    AnnounceCard, InfoCard, OwnerCard,
    VideoCardList, ShuoCardList, BlogCardList, BlogDetailCardList, AnimeCardList, VersionCardList, JokeCardList
  },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const cardComponents = [AnnounceCard, InfoCard, OwnerCard];
    const cardListComponents = [VideoCardList, ShuoCardList, BlogCardList, BlogDetailCardList, AnimeCardList, FriendCardList, VersionCardList, JokeCardList];

    return {
      CardType,
      cardComponents,
      cardListComponents,
      type: $process.sideCardType,
      cardChoices: $process.sideCardChoice,
      cardListChoice: $process.sideCardList
    };
  },
});
</script>

<style lang="scss" scoped>
.sideCard {
  padding: 20px;
  padding-top: 0;
  .cards > .card {
    margin-bottom: 20px;
  }
}
</style>