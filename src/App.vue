<template>
  <router-view v-slot="{ Component }" v-if="!waiting">
    <keep-alive>
      <component :is="Component" />
    </keep-alive>
  </router-view>
  <Wait></Wait>
  <Loading :wait="waiting"></Loading>
  <Mask :mask="isMask"></Mask>
</template>

<script lang="ts">
import { defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import Loading from './components/loading/base-loading.vue';
import Wait from './components/loading/base-wait.vue';
import Mask from './components/currency/mask.vue';

export default defineComponent({
  name: 'App',
  components: { Loading, Wait, Mask },
  setup() {
    const $route = useRoute();
    const $router = useRouter();
    const $service = inject<ApiObject>('$service')!;
    const $context = inject<CV>('$context')!;
    const $user = inject<U>('$user')!;
    const $wait = inject<Wait>('$wait')!;
    const waiting = ref(false);
    const isMask = ref(false);

    // csrf
    async function getSafe() {
      await $service.safe();
    }

    // 获取文案信息
    async function context() {
      return await $service.copywriting({});
    }

    // 获取用户信息
    async function sso() {
      // return await $service.au_in({ cn, pn, p, t, b_type: 'diy' });
    }

    //路由加载完成初始化博客基本内容
    $router.isReady().then(async () => {
      // await getSafe();
      // Promise.all([context(), sso()]).then(async values => {
      //   const [contextData, ssoData] = values;
      //   initConfig(contextData);
        // initUserInfo(ssoData);
        // waiting.value = false;
      // });
    });

    // 初始化文案信息
    async function initConfig({ code, pa_a }: resp_type) {
      if (code == '200') {
        const p = JSON.parse(pa_a) || {};
        $context.init(p);
      }
    }

    // 初始化用户信息
    async function initUserInfo({ code, u, c }: resp_type) {
      if (code == '200') {
        $user.init(Object.assign(c, u));
      }
    }

    return {
      waiting,
      isMask
    }
  }
});
</script>

<style lang="scss">

</style>