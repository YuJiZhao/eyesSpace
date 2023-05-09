<template>
  <div class="shuoshuoComment">

  </div>
</template>

<script>
import { defineComponent, reactive } from "vue";
import { useMessage } from "naive-ui";
import { useRouter } from "vue-router";
import $api from "@/server/api";

export default defineComponent({
  components: {},
  setup() {
    const message = useMessage();
    const router = useRouter();

    return {};
  },
});
</script>

<style scoped lang="scss">
.shuoshuoComment {

}
</style>
