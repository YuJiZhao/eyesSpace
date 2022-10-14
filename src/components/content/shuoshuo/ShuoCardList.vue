<template>
  <div class="shuoCard">
    <owner-card class="card" />
    <shuo-data-card class="card" />
    <info-card class="card" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ShuoDataCard from "@/components/content/shuoshuo/components/cards/ShuoDataCard.vue";
import { InfoCard, OwnerCard } from "@/components/general/card";

export default defineComponent({
  components: { InfoCard, OwnerCard, ShuoDataCard },
  setup() {
    return {
    };
  },
});
</script>

<style lang="scss" scoped>
.shuoCard {
  .card {
    margin-bottom: 20px;
  }
}

</style>