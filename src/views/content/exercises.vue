<template>
  <div class="exercises">
    exercises
  </div>
</template>

<script>
import { reactive } from "vue";
import { useMessage } from "naive-ui";
import $api from "@/server/api";

export default {
  components: {},
  setup() {
    const message = useMessage();
    
    return {
    };
  },
};
</script>

<style scoped lang="scss">
.exercises {
}
</style>
