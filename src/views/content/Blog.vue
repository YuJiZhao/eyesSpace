<template>
  <div class="blog">
    <Wait :show="show" :fail="isFail" height="400px">
      <blog-list :key="blogSentry" />
    </Wait>
    <Pagination 
      :key="blogSentry"
      :total="total" 
      :size="pageSize"
      :initPage="page"
      @pageChange="pageChange"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, onActivated, onBeforeMount, ref, watch } from 'vue';
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import useProcessControl from "@/hooks/useProcessControl";
import { useRouter } from "vue-router";
import { CardDirection, CardType, CardList } from "@/constant";
import { codeConfig } from "@/config/program";
import { Wait } from "@/components/general/popup";
import { BlogList } from "@/components/content/blog";
import { blogContext } from '@/components/content/blog/businessTs/blogContext';
import Pagination from "@/components/general/Pagination/pagination.vue";
import { goBoth, GoBothType } from "@/hooks/useGoBoth";
import { writerMeta } from "@/router/help";
import { metaInfo } from "@/config/site";
import { BlogQueryInterface } from "@/components/content/blog/d.ts/blogQuery";

export default defineComponent({
  name: "Blog",
  components: { BlogList, Wait, Pagination },
  beforeRouteEnter: () => {
    writerMeta(metaInfo.blog);
  },
  setup() {
    const router = useRouter();
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;
    
    let page = ref(1);
    let total = ref(1);
    let pageSize = ref(8);
    let category = ref<string>();
    let label = ref<string>();

    let show = ref(true);
    let isFail = ref(false);
    let blogSentry = ref(0);

    async function getBlogList() {
      return await $api.getBlogList({
        page: page.value,
        pageSize: pageSize.value,
        category: category.value,
        label: label.value
      }).then(({code, data}) => {
        if(code == codeConfig.success) {
          blogContext.init(data.data);
          total.value = data.total;
          blogSentry.value ++;
          goBoth(GoBothType.TopSpeed);
          return true;
        } else {
          $process.tipShow.error("博客列表获取失败");
          return false;
        }
      })
    }

    async function initData() {
      page.value = Number(<string>router.currentRoute.value.query.page) || 1;
      category.value = <string>router.currentRoute.value.query.category;
      label.value = <string>router.currentRoute.value.query.label;
      
      await getBlogList().then((flag) => {
        show.value = isFail.value = !flag;
      });
    }

    async function pageChange(target: number) {
      category.value = <string>router.currentRoute.value.query.category;
      label.value = <string>router.currentRoute.value.query.label;
      let query: Partial<BlogQueryInterface> = { page: target };
      if(category.value) {
        query.category = category.value;
      }
      if(label.value) {
        query.label = label.value;
      }
      router.push({
        path: "/blog",
        query
      });
    }

    watch(
      () => router.currentRoute.value.query,
      () => {
        if(router.currentRoute.value.path != "/blog") return;
        page.value = Number(router.currentRoute.value.query.page) || 1;
        category.value = <string>router.currentRoute.value.query.category;
        label.value = <string>router.currentRoute.value.query.label;
        getBlogList();
      }
    )

    onActivated(() => {
      useProcessControl(true, {
        direction: CardDirection.row,
        cardType: CardType.CardList,
        cardList: CardList.BlogCardList
      });
    })

    onBeforeMount(() => {
      initData();
    })

    return {
      show,
      isFail,
      total,
      page,
      pageSize,
      blogSentry,
      pageChange
    };
  },
});
</script>

<style lang="scss" scoped>
.blog {
  width: 100%;
}
</style>