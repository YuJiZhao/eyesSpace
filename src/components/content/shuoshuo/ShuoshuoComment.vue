<template>
  <div class="shuoshuoComment">
    评论管理
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
