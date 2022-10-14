<template>
    <blog-item
      class="item blogItem"
      v-if="data.type == HomeType.blog"
      :id="data.homeList.id"
      :title="data.homeList.title"
      :category="data.homeList.category"
      :words="data.homeList.words"
      :summary="data.homeList.summary"
      :date="data.homeList.createTime"
      :view="data.homeList.views"
      :like="data.homeList.likes"
      :collection="data.homeList.collections"
    />
    <shuo-item
      class="item shuoshuoItem"
      v-if="data.type == HomeType.shuoshuo"
      :id="data.homeList.id"
      :content="data.homeList.content"
      :picList="data.homeList.picList"
      :views="data.homeList.views"
      :comments="data.homeList.comments"
      :createTime="data.homeList.createTime"
      @click.capture.stop="jumpDetail(data.homeList.id)"
    />
</template>

<script lang="ts">
import { defineComponent } from "vue";
import BlogItem from "@/components/content/blog/components/BlogItem.vue";
import ShuoItem from "@/components/content/shuoshuo/components/ShuoItem.vue";
import { HomeType } from "@/constant";
import { useRouter } from "vue-router";

export default defineComponent({
  components: { BlogItem, ShuoItem },
  props: ["data"],
  setup(props) {
    let router = useRouter();

    function jumpDetail(id: string) {
      window.open(router.resolve({
        path: "/shuoshuo/details",
        query: { id }
      }).href, "_blank");
    }

    return {
      HomeType,
      data: props.data,
      jumpDetail,
    };
  },
});
</script>

<style lang="scss" scoped>
.shuoshuoItem {
  cursor: pointer;
}
</style>