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
      @click.capture.stop="jumpShuo(data.homeList.id)"
    />
    <version-item
      class="item versionItem"
      v-if="data.type == HomeType.version"
      :data="data.homeList"
    />
    <anime-item
      class="item animeItem"
      v-if="data.type == HomeType.anime"
      :data="data.homeList"
      @click="jumpAnime(data.homeList.id)"
    />
</template>

<script lang="ts">
import { defineComponent } from "vue";
import BlogItem from "@/components/content/blog/components/BlogItem.vue";
import ShuoItem from "@/components/content/shuoshuo/components/ShuoItem.vue";
import VersionItem from "@/components/content/version/components/VersionItem.vue";
import { HomeType } from "@/constant";
import { useRouter } from "vue-router";
import { AnimeItem } from "@/components/content/anime";

export default defineComponent({
  components: { BlogItem, ShuoItem, VersionItem, AnimeItem },
  props: ["data"],
  setup(props) {
    let router = useRouter();

    function jumpShuo(id: string) {
      window.open(router.resolve({
        path: "/shuoshuo/details",
        query: { id }
      }).href, "_blank");
    }

    function jumpAnime(id: number) {
      window.open(router.resolve(`/anime/details/${id}`).href, "_blank");
    }

    return {
      HomeType,
      data: props.data,
      jumpShuo,
      jumpAnime
    };
  },
});
</script>

<style lang="scss" scoped>
.shuoshuoItem, .animeItem {
  cursor: pointer;
}
</style>