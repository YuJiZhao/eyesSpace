<template>
  <base-card>
    <div class="newPubCard">newPubCard</div>
  </base-card>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import BaseCard from "./components/BaseCard.vue";

export default defineComponent({
  components: { BaseCard },
  setup() {
    return {};
  },
});
</script>

<style lang="scss" scoped>
.newPubCard {
}
</style>