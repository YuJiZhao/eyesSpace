<template>
  <standard-card title="博客标签" :icon="icon">
    <Wait :show="show" :fail="isFail" height="100px">
      <div class="card blogLabelCard">
        <div class="label" :class="'label_' + item.label" v-for="item in labelData" :key="item.label" @click="getBlogList(item.label)">
          <div class="name">{{item.label}}</div>
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

    let labelData = ref();
    let show = ref(true);
    let isFail = ref(false);

    async function getBlogLabel() {
      show.value = true;
      isFail.value = false;
      await $api.getBlogLabel().then(({code, msg, data}) => {
        if(code == codeConfig.success) {
          labelData.value = data;
          show.value = false;
          initActive();
        } else {
          $process.tipShow.error("获取博客标签失败");
          isFail.value = true;
        }
      })
    }

    function getBlogList(label: string) {
      let query: Partial<BlogQueryInterface> = {};
      query.page = 1;
      if(router.currentRoute.value.query.category) query.category = <string>router.currentRoute.value.query.category;
      if(!document.querySelector(".label_" + label)!.classList.contains("active")) {
        document.querySelectorAll(".label").forEach((v) => {
          v.classList.remove("active");
        })
        document.querySelector(".label_" + label)!.classList.add("active");
        query.label = label;
      } else {
        document.querySelector(".label_" + label)!.classList.remove("active");
      }
      router.push({
        path: "/blog",
        query
      });
    }

    function initActive() {
      nextTick(() => {
        if(router.currentRoute.value.query.label) {
          document.querySelector(".label_" + <string>router.currentRoute.value.query.label)!.classList.add("active");
        }
      })
    }

    onMounted(() => {
      getBlogLabel();
    })
    
    return {
      icon: resource.label,
      labelData,
      show,
      isFail,
      getBlogList
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.blogLabelCard {
  display: flex;
  flex-wrap: wrap;
  color: $normal;
  .label {
    height: 30px;
    padding: 5px;
    border-radius: 3px;
    box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    display: flex;
    margin-right: 10px;
    margin-bottom: 10px;
    cursor: pointer;
    transition: 0.5s;
    &:hover {
      color: $white;
      background: rgb(127, 200, 248);
      box-shadow: 0 0 0 #000;
      -webkit-box-shadow: 0 0 0 #000;
      -moz-box-shadow: 0 0 0 #000;
    }
    .name {
      margin-right: 5px;
    }
  }
}

.active {
  color: $white;
  background: rgb(127, 248, 135);
  box-shadow: 0 0 0 #000 !important;
  -webkit-box-shadow: 0 0 0 #000 !important;
  -moz-box-shadow: 0 0 0 #000 !important;
  &:hover {
    background: rgb(127, 248, 135) !important;
  }
}
</style>