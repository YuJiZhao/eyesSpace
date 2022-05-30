<template>
  <router-view v-slot="{ Component }">
    <keep-alive>
      <component :is="Component" />
    </keep-alive>
  </router-view>

  <!-- 弹出层 -->
  <Load />
  <Announcement />
  <Alert />
  <Reward />
</template>

<script lang="ts">
import { defineComponent, inject, reactive } from "vue";
import { useRoute, useRouter } from "vue-router";
import Home from "@/views/Home.vue";
import { Load, Announcement, Alert, Reward } from "@/components/popup";
import { CVType, UserType } from "@/d.ts/modules";
import { PopupType } from "@/d.ts/modules";
import { errorMsg } from "@/config/websiteConfig"

export default defineComponent({
  components: {
    Home,
    Load,
    Announcement,
    Alert,
    Reward,
  },
  setup() {
    const $route = useRoute();
    const $router = useRouter();
    const $service = inject<ApiObject>("$service")!;
    const $context = inject<CVType>("$context")!;
    const $user = inject<UserType>("$user")!;
    const $popup = inject<PopupType>("$popup")!;

    // csrf
    async function getSafe() {
      await $service.safe();
    }

    // 获取文案信息
    async function context(): Promise<RespType> {
      return await $service.getContext();
    }

    // 获取用户信息
    // async function sso(): Promise<RespType> {
    async function sso() {
      // return await $service.;
    }

    //路由加载完成初始化博客基本内容
    $router.isReady().then(async () => {
      // await getSafe();
      // Promise.all([context(), sso()]).then(async values => {
      //   const [contextData, ssoData] = values;
      //   initConfig(contextData);
        // initUserInfo(ssoData);
      // });
    });

    // 初始化文案信息
    async function initConfig({ code, message, data }: RespType) {
      if (code == 200) {
        $context.init(data);
      } else {
        $popup.alertShow(reactive({
          title: errorMsg.apiError,
          content: message || errorMsg.apiErrorDetail
        }));
      }
    }

    // 初始化用户信息
    async function initUserInfo({ code, u, c }: RespType) {
      //   if (code == '200') {
      //     $user.init(Object.assign(c, u));
      //   }
    }

    return {};
  },
});
</script>

<style lang="scss">
/**
 * 全局样式
 */

// 字体
@font-face {
  font-family: "eyes";
  src: url("https://cdn.jsdelivr.net/gh/YuJiZhao/picbed@material/font/eyesblog.woff2");
  font-display: swap;
}

body {
  font-family: eyes !important;
}

// 鼠标
body,
span,
time,
i {
  cursor: url("https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/other/normal.png"),
    auto;
}
a:hover,
button:hover,
#footer-wrap a:hover,
#pagination .page-number:hover,
#nav .site-page:hover,
a > i,
a > span {
  cursor: url("https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/other/click.png"),
    auto;
}

// 谷歌导航条样式设置
::-webkit-scrollbar {
  width: 10px;
}
::-webkit-scrollbar-thumb {
  background: rgb(76, 180, 240);
}
</style>