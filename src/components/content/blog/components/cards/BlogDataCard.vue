<template>
  <standard-card title="博客数据" :icon="icon">
    <Wait :show="show" :fail="isFail" height="100px">
      <div class="card blogDataCard">
        <ul>
            <li v-for="item in blogDataCardConfig" :key="item.name">
                <div>{{item.title}}</div>
                <div>{{blogData[item.name]}}</div>
            </li>
        </ul>
      </div>
    </Wait>
  </standard-card>
</template>

<script lang="ts">
import { defineComponent, ref, inject, onMounted } from "vue";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { codeConfig } from "@/config/program";
import StandardCard from "@/components/general/card/components/StandardCard.vue";
import resource from "@/config/resource";
import { blogDataCardConfig } from "@/components/content/blog/config";
import { Wait } from "@/components/general/popup";

export default defineComponent({
  components: { StandardCard, Wait },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let show = ref(true);
    let isFail = ref(false);
    let blogData = ref<any>({});

    async function getBlogListInfo() {
      show.value = true;
      isFail.value = false;
      await $api.getBlogListInfo().then(({code, data}) => {
        if(code == codeConfig.success) {
          blogData.value = data;
          if(data.deleteNum) {
            blogData.value.totalNum -= data.deleteNum;
          }
          show.value = false;
        } else {
          $process.tipShow.error("博客数据获取失败");
          isFail.value = true;
        }
      });
    }

    onMounted(() => {
      getBlogListInfo();
    })
    
    return {
      icon: resource.data,
      blogDataCardConfig,
      blogData,
      show,
      isFail
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.blogDataCard {
  li {
    display: flex;
    justify-content: space-between;
    margin-bottom: 5px;
    color: $normal;
  }
}
</style>