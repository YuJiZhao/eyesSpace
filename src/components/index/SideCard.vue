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
import { defineComponent, inject, nextTick, onMounted, ref, watch } from "vue";
import { ProcessInterface, WindowInterface } from "@/d.ts/plugin";
import { CardType } from "@/constant";
import { AnnounceCard, InfoCard, LabelCard, NewPubCard, OwnerCard, RandomPubCard } from "@/components/general/card";
import VideoCardList from "@/components/content/video/VideoCardList.vue";
import ShuoCardList from "@/components/content/shuoshuo/ShuoCardList.vue";
import BlogCardList from "@/components/content/blog/BlogCardList.vue";
import BlogDetailCardList from "@/components/content/blogDetail/BlogDetailCardList.vue";
import { siteConfig } from "@/config/program";

export default defineComponent({
  components: { 
    AnnounceCard, InfoCard, LabelCard, NewPubCard, OwnerCard, RandomPubCard,
    VideoCardList, ShuoCardList, BlogCardList, BlogDetailCardList
  },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $window = inject<WindowInterface>("$window")!;
    const cardComponents = [AnnounceCard, InfoCard, LabelCard, NewPubCard, OwnerCard, RandomPubCard];
    const cardListComponents = [VideoCardList, ShuoCardList, BlogCardList, BlogDetailCardList];

    let sticky = ref("static");
    let stickyHeight = ref("0px");

    watch(
      () => $process.sideCardFollow.value,
      (value) => {
        if(!value || $window.width.value < siteConfig.mpThreshold) return;
        nextTick(() => {
          if(document.querySelector(`#${siteConfig.followCardId}`)) {
            sticky.value = "sticky";
            stickyHeight.value = -((document.querySelector(`#${siteConfig.followCardId}`) as HTMLElement).offsetTop - 55) + "px";
          }
        })
      }
    )

    return {
      CardType,
      cardComponents,
      cardListComponents,
      type: $process.sideCardType,
      cardChoices: $process.sideCardChoice,
      cardListChoice: $process.sideCardList,
      sticky,
      stickyHeight
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
  .cardList {
    position: v-bind(sticky);
	  top: v-bind(stickyHeight);
  }
}
</style>