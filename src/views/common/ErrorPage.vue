<template>
  <div class="errorPage">
    <div class="errorBox">
      <img :src="errorPage" />
      <div class="errorTitle"></div>
      <div class="advice">
        <div v-for="item in errorData.items" :key="item.word" @click="funcObject[item.clickFunc]">
          <img :src="item.icon" />
          <div>{{ item.word }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, onMounted, reactive, UnwrapNestedRefs, nextTick } from "vue";
import { useRouter } from "vue-router";
import { ProcessInterface, ContextInterface } from "@/d.ts/plugin";
import resource from "@/config/resource";
import useProcessControl from "@/hooks/useProcessControl";
import { metaInfo, errorConfig, errorPath } from "@/config/site";
import { ErrorConfigPropertyInterface } from "@/d.ts/config/site";
import { Dialogs } from "@/constant";
import { siteConfig } from "@/config/program";
import { writerMeta } from "@/router/help";

export default defineComponent({
  name: "ErrorPage",
  beforeRouteEnter: () => {
    writerMeta(metaInfo.home);
  },
  setup() {
    const router = useRouter();
    const $process = inject<ProcessInterface>("$process")!;
    const $context = inject<ContextInterface>("$context")!;

    let errorData: UnwrapNestedRefs<ErrorConfigPropertyInterface> = reactive({
      title: "",
      items: [],
      process: [],
    });

    let funcObject = {
      goBack: () => { window.history.go(-1); },
      goIndex: () => { router.push("/"); },
      refreshPage: () => { location.reload(); },
      contactMe: () => { $process.dialogShow(Dialogs.ContactMe); },
    };

    function initPage() {
      let param = router.currentRoute.value.params.type as string;
      Object.assign(
        errorData,
        Object.getOwnPropertyNames(errorConfig).includes(param)
          ? (errorConfig as any)[param]
          : errorConfig.errorRoute
      );
      nextTick(() => {
        document.querySelector(".errorPage .errorTitle")!.innerHTML = errorData.title;
      });
      useProcessControl(
        errorData.process[0] == null ? true : errorData.process[0],
        false,
        errorData.process[2] == null ? true : errorData.process[2]
      );
    }

    function checkContext() {
      if(router.currentRoute.value.fullPath == errorPath.context && Object.getOwnPropertyNames($context.data).length) {
        router.push(localStorage.getItem(siteConfig.enterURL) || "/");
      }
    }

    onMounted(() => {
      checkContext();
      initPage();
    });

    return {
      errorPage: resource.errorPage,
      errorData,
      funcObject,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.errorPage {
  .errorBox {
    width: 300px;
    margin-top: 50px;
    margin: 0 auto;
    img {
      width: 250px;
      display: block;
      margin: 0 auto;
    }
    .errorTitle {
      font-size: 20px;
      text-align: center;
      margin-bottom: 20px;
    }
    .advice {
      display: flex;
      justify-content: space-around;
      margin-bottom: 10px;
      & > div {
        width: 120px;
        height: 40px;
        padding: 10px;
        border-radius: 5px;
        box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
        -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
        -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
        cursor: pointer;
        display: flex;
        justify-content: center;
        align-items: center;
        img {
          width: 20px;
          left: 20px;
          display: block;
          margin-left: 0;
          margin-right: 5px;
        }
      }
    }
  }
}
</style>