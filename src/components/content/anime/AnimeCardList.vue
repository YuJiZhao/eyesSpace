<template>
  <div class="animeCard">
    <owner-card class="card" />
    <anime-notice-card class="card" />
    <anime-data-card class="card" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { OwnerCard } from "@/components/general/card";
import AnimeNoticeCard from "./components/cards/AnimeNoticeCard.vue";
import AnimeDataCard from "./components/cards/AnimeDataCard.vue";

export default defineComponent({
  components: { OwnerCard, AnimeNoticeCard, AnimeDataCard },
  setup() {
    return {
    };
  },
});
</script>

<style lang="scss" scoped>
.animeCard {
  .card {
    margin-bottom: 20px;
  }
}

</style>