<template>
  <standard-card title="公告" :icon="icon">
    <div class="announceCard">
      <div class="announce">{{ announce }}</div>
    </div>
  </standard-card>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import StandardCard from "./components/StandardCard.vue";
import resource from "@/config/resource";
import { ContextInterface } from "@/d.ts/plugin";

export default defineComponent({
  components: { StandardCard },
  setup() {
    const $context = inject<ContextInterface>("$context")!;
    return {
      announce: $context.data.announce,
      icon: resource.announce,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.announceCard {
  .announce {
    color: $normal;
  }
}
</style>