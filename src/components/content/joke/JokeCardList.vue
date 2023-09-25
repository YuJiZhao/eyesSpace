<template>
  <div class="jokeCardList">
    <owner-card class="card" />
    <joke-notice-card class="card" />
    <info-card class="card" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import JokeNoticeCard from "./components/card/JokeNoticeCard.vue";
import { InfoCard, OwnerCard } from "@/components/general/card";

export default defineComponent({
  components: { InfoCard, OwnerCard, JokeNoticeCard },
  setup() {
    return {
    };
  },
});
</script>

<style lang="scss" scoped>
.jokeCardList {
  .card {
    margin-bottom: 20px;
  }
}
</style>