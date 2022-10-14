<template>
  <standard-card title="博客分类" :icon="icon">
    <Wait :show="show" :fail="isFail" height="100px">
      <div class="card blogCategoryCard">
        <div class="category" :class="'category_' + item.category" v-for="item in categoryData" :key="item.category" @click="getBlogList(item.category)">
          <div class="name">{{item.category}}</div>
          <div class="num">{{item.num}}</div>
        </div>
      </div>
    </Wait>
  </standard-card>
</template>

<script lang="ts">
import { defineComponent, ref, inject, onMounted, nextTick } from "vue";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { codeConfig } from "@/config/program";
import { useRouter } from "vue-router";
import StandardCard from "@/components/general/card/components/StandardCard.vue";
import resource from "@/config/resource";
import { Wait } from "@/components/general/popup";
import { BlogQueryInterface } from "@/components/content/blog/d.ts/blogQuery";

export default defineComponent({
  components: { StandardCard, Wait },
  setup() {
    const router = useRouter();
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let categoryData = ref();
    let show = ref(true);
    let isFail = ref(false);

    async function getBlogCategory() {
      show.value = true;
      isFail.value = false;
      await $api.getBlogCategory().then(({code, msg, data}) => {
        if(code == codeConfig.success) {
          categoryData.value = data;
          show.value = false;
          initActive();
        } else {
          $process.tipShow.error("博客分类获取失败");
          isFail.value = true;
        }
      })
    }

    function getBlogList(category: string) {
      let query: Partial<BlogQueryInterface> = {};
      query.page = 1;
      if(router.currentRoute.value.query.label) query.label = <string>router.currentRoute.value.query.label;
      if(!document.querySelector(".category_" + category)!.classList.contains("active")) {
        document.querySelectorAll(".category").forEach((v) => {
          v.classList.remove("active");
        })
        document.querySelector(".category_" + category)!.classList.add("active");
        query.category = category;
      } else {
        document.querySelector(".category_" + category)!.classList.remove("active");
      }
      router.push({
        path: "/blog",
        query
      });
    }

    function initActive() {
      nextTick(() => {
        if(router.currentRoute.value.query.category) {
          document.querySelector(".category_" + <string>router.currentRoute.value.query.category)!.classList.add("active");
        }
      })
    }

    onMounted(() => {
      getBlogCategory();
    })
    
    return {
      icon: resource.category,
      categoryData,
      show,
      isFail,
      getBlogList
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.blogCategoryCard {
  color: $normal;
  .category {
    display: flex;
    justify-content: space-between;
    height: 25px;
    line-height: 25px;
    cursor: pointer;
    transition: 0.5s;
    &:hover {
      color: $white;
      background: rgb(127, 200, 248);
      padding: 0 10px;
    }
  }
}

.active {
  color: $white;
  background: rgb(127, 248, 135) !important;
  padding: 0 10px;
  &:hover {
    background: rgb(127, 248, 135) !important;
  }
}
</style>