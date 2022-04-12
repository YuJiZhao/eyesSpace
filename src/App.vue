<template>
  <!-- 主页面 -->
  <Home class="home" v-if="!loadStatus"/>

  <!-- 弹出层 -->
  <Load class="load" v-if="loadStatus"></Load>
  <Announcement />
  <Alert />
  <Reward />
</template>

<script lang="ts">
import { defineComponent, inject, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import Home from "@/views/Home.vue";
import { Load, Announcement, Alert, Reward } from "@/components/popup";
import { CVType, PopupType, UserType } from "@/d.ts/modules";

export default defineComponent({
  components: {
    Home,
    Load, Announcement, Alert, Reward
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
      return await $service.copywriting();
    }

    // 获取用户信息
    async function sso() {
      // return await $service.au_in({ cn, pn, p, t, b_type: 'diy' });
    }

    //路由加载完成初始化博客基本内容
    // $router.isReady().then(async () => {
      // await getSafe();
      // Promise.all([context(), sso()]).then(async values => {
        // const [contextData, ssoData] = values;
        // initConfig(contextData);
        // initUserInfo(ssoData);
        // $popup.loadStatus.val = true
      // });
    // });

    // 初始化文案信息
    async function initConfig({ code, message, data }: RespType) {
      if (code == "200") {
        // const copywriting = JSON.parse(data) || {};
        // console.log(copywriting);
        // $context.init(p);
      } else {
        // TODO: 否则弹出警示框
      }
    }

    // 初始化用户信息
    async function initUserInfo({ code, u, c }: RespType) {
    //   if (code == '200') {
    //     $user.init(Object.assign(c, u));
    //   }
    }

    return {
      loadStatus
    }
  }
});
</script>

<style lang="scss">
// 全局样式

// 响应式布局
@media screen and (min-width: 750px) {
    html {
        font-size: 37.5px !important;
    }
}

// 字体
@font-face {
    font-family: 'eyes';
    src: url('https://cdn.jsdelivr.net/gh/YuJiZhao/picbed@material/font/eyesblog.woff2');
    font-display: swap;
}
body {
    font-family: eyes !important;
}

// 鼠标
body, span, time, i {
    cursor: url('https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/other/normal.png'), auto;
}
a:hover, button:hover, #footer-wrap a:hover, #pagination .page-number:hover, #nav .site-page:hover, a > i, a > span{
    cursor: url('https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/other/click.png'), auto;
}

// 谷歌导航条样式设置
::-webkit-scrollbar {
    width: 10px;
}
::-webkit-scrollbar-thumb {
    background: rgb(76, 180, 240);
}
</style>