<template>
  <div class="friendCard">
    <owner-card class="card" />
    <friend-data-card class="card" />
    <info-card class="card" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { OwnerCard, InfoCard } from "@/components/general/card";
import FriendDataCard from "./components/cards/FriendDataCard.vue";

export default defineComponent({
  components: { OwnerCard, InfoCard, FriendDataCard },
  setup() {
    return {
    };
  },
});
</script>

<style lang="scss" scoped>
.friendCard {
  .card {
    margin-bottom: 20px;
  }
}

</style>