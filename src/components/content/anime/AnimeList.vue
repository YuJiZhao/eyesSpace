<template>
  <div class="animeList">
    <div class="animeItem" v-for="(item, index) in animeListData" :key="item">
      <anime-item :data="item" :index="index" @click="jumpDetail(item.id)"/>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AnimeItem from "./components/AnimeItem.vue";
import { useRouter } from "vue-router";

export default defineComponent({
  components: { AnimeItem },
  props: ["animeListData"],
  setup(props) {
    let router = useRouter();

    function jumpDetail(id: number) {
      window.open(router.resolve(`/anime/details/${id}`).href, "_blank");
    }
    
    return {
      animeListData: props.animeListData,
      jumpDetail
    };
  },
});
</script>

<style lang="scss" scoped>
.animeList {
  width: 100%;
  min-width: 335px;
  margin: 0 auto;
  .animeItem {
    cursor: pointer;
  }
}
</style>