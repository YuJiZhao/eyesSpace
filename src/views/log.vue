<template>
  <div class="log">
    log
  </div>
</template>

<script>
import { reactive } from "vue";
import { useMessage } from "naive-ui";
import { useRouter } from "vue-router";
import $api from "@/server/api";

export default {
  components: {},
  setup() {
    const message = useMessage();
    const router = useRouter();

    return {
    };
  },
};
</script>

<style scoped lang="scss">
.log {
}
</style>
