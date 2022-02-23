<template>
  <router-view v-slot="{ Component }" v-if="!waiting">
    <keep-alive>
      <component :is="Component" />
    </keep-alive>
  </router-view>
  <base-wait></base-wait>
  <base-loading :wait="waiting"></base-loading>
</template>

<script lang="ts">
import { defineComponent, inject, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import BaseLoading from './components/loading/base-loading.vue';
import BaseWait from './components/loading/base-wait.vue';
export default defineComponent({
  name: 'App',
  components: { BaseLoading, BaseWait },
  setup() {
    const $route = useRoute();
    const $router = useRouter();
    //接口
    const $service = inject<ApiObject>('$service')!;
    //接口等待层
    const $wait = inject<Wait>('$wait')!;
    //控制页面展示
    const waiting = ref(false);

    //csrf
    // async function getSafe() {
    //   await $service.safe();
    // }

    //路由加载完成，确保能够获取url参数
    // $router.isReady().then(async () => {
      // await getSafe();
      // Promise.all([context(), sso()]).then(async values => {
      //   const [contextData, ssoData] = values;
      //   initUserInfo(ssoData);
      //   initConfig(contextData);
      //   waiting.value = false;
      // });
    // });

    //初始化用户信息
    // async function initUserInfo({ code, u, c }: resp_type) {
    //   if (code == '200') {
    //     $wait.hide();
    //     $user.init();
    //   }
    // }

    //初始化文案配置
    // async function initConfig({ code, pa_a }: resp_type) {
    //   if (code == '200') {
    //     const p = JSON.parse(pa_a) || {};
    //     $context.init(p);
    //   }
    // }

    return {
      waiting
    }
  }
});
</script>