<template>
  <div class="message">
    <Comment class="messageComment" :objectId="objectId" :apiType="apiType" />
  </div>
</template>

<script lang="ts">
import { defineComponent, onActivated } from "vue";
import useProcessControl from "@/hooks/useProcessControl";
import Comment from "@/components/general/comment/Comment.vue";
import { CommentApiType } from "@/constant";
import { writerMeta } from "@/router/help";
import { metaInfo } from "@/config/site";

export default defineComponent({
  name: "Message",
  components: { Comment },
  beforeRouteEnter: () => {
    writerMeta(metaInfo.message);
  },
  setup() {
    onActivated(() => {
      useProcessControl();
    })

    return {
      objectId: 0,
      apiType: CommentApiType.site,
    };
  },
});
</script>

<style lang="scss" scoped>
.messageComment {
    margin: 20px auto;
}
</style>