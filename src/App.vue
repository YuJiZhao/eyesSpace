<template>
  <!-- 主页面 -->
  <Home class="home" v-if="!loadStatus"/>
  
  <!-- 弹出层 -->
  <Load class="load" v-if="loadStatus"></Load>
  <Announcement />
</template>

<script lang="ts">
import { defineComponent, inject, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import Home from "@/views/Home.vue";
import { Load, Alert, Announcement } from "@/components/popup";
import { CVType, PopupType, UserType } from "@/d.ts/modules";

export default defineComponent({
  components: {
    Home,
    Load, Alert, Announcement
  },
  setup() {
    const $route = useRoute();
    const $router = useRouter();
    const $service = inject<ApiObject>('$service')!;
    const $context = inject<CVType>('$context')!;
    const $user = inject<UserType>('$user')!;
    const $popup = inject<PopupType>('$popup')!;

    let loadStatus = ref($popup.loadStatus.value);

    watch(() => $popup.loadStatus.value, (value) => {
      loadStatus.value = value;
    });

    // csrf
    async function getSafe() {
      await $service.safe();
    }

    // 获取文案信息
    async function context() {
      // return await $service.copywriting({});
    }

    // 获取用户信息
    async function sso() {
      // return await $service.au_in({ cn, pn, p, t, b_type: 'diy' });
    }

    //路由加载完成初始化博客基本内容
    $router.isReady().then(async () => {
      // await getSafe();
      // Promise.all([context(), sso()]).then(async values => {
        // const [contextData, ssoData] = values;
        // initConfig(contextData);
        // initUserInfo(ssoData);
        // $popup.loadStatus.val = true
      // });
    });

    // 初始化文案信息
    async function initConfig({ code, pa_a }: RespType) {
      // if (code == '200') {
      //   const p = JSON.parse(pa_a) || {};
      //   $context.init(p);
      // }
    }

    // 初始化用户信息
    // async function initUserInfo({ code, u, c }: resp_type) {
    //   if (code == '200') {
    //     $user.init(Object.assign(c, u));
    //   }
    // }

    return {
      loadStatus
    }
  }
});
</script>

<style lang="scss">
.load {
  width: 100vw;
  height: 100vh;
}
</style>