<template>
  <div class="music">
    <Aplayer />
  </div>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import { PopupType, CVType } from "@/d.ts/modules";
import Aplayer from "@/components/music/aplayer.vue";
import $ from "jquery";

export default defineComponent({
  components: { Aplayer },
  setup() {
    const $popup = inject<PopupType>("$popup")!;
    const $context = inject<CVType>("$context")!;

    return {};
  },
});
</script>


<style lang="scss" scoped>
.music {
}
</style>
