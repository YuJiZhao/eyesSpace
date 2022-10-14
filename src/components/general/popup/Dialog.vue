<template>
  <div class="dialog" v-if="status">
    <component :is="dialogComponents[choice]"/>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import { ProcessInterface } from "@/d.ts/modules/process";
import { Reward, ContactMe, idCard } from "./dialogComponent";

export default defineComponent({
  components: { Reward, ContactMe, idCard },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const dialogComponents = [Reward, ContactMe, idCard];
    
    return {
      dialogComponents,
      choice: $process.dialogChoice,
      status: $process.dialogStatus
    };
  },
});
</script>