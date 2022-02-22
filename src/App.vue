<template>
  <router-view></router-view>
  <!-- <router-view v-slot="{ Component }" v-if="!waiting">
    <keep-alive>
      <component :is="Component" />
    </keep-alive>
  </router-view>
  <base-wait></base-wait>
  <base-loading :wait="waiting"></base-loading> -->
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
    //路由
    // const $route = useRoute();
    // const $router = useRouter();
    //接口
    // const $service = inject<ApiObject>('$service')!;
    //接口等待层
    // const $wait = inject<Wait>('$wait')!;
    //控制页面展示
    // const waiting = ref(true);

    return {
      // waiting
    };
  }
});
</script>