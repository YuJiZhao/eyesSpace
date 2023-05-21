<template>
  <div class="versionCardList">
    <owner-card class="card" />
    <version-data-card class="card" />
    <info-card class="card" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import VersionDataCard from "@/components/content/version/components/cards/VersionDataCard.vue";
import { InfoCard, OwnerCard } from "@/components/general/card";

export default defineComponent({
  components: { InfoCard, OwnerCard, VersionDataCard },
  setup() {
    return {
    };
  },
});
</script>

<style lang="scss" scoped>
.versionCardList {
  .card {
    margin-bottom: 20px;
  }
}
</style>