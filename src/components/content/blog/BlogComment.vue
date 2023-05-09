<template>
  <div class="BlogComment">
    博客评论
  </div>
</template>

<script>
import { defineComponent, reactive } from "vue";
import { useMessage } from "naive-ui";
import $api from "@/server/api";

export default defineComponent({
  components: {},
  setup() {
    const message = useMessage();

    return {};
  },
});
</script>

<style scoped lang="scss">
.BlogComment {

}
</style>
