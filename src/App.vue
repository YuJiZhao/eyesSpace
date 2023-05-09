<template>
  <n-config-provider :theme-overrides="appThemeOverrides">
    <n-message-provider>
      <n-loading-bar-provider>
        <router-view></router-view>
      </n-loading-bar-provider>
    </n-message-provider>
  </n-config-provider>
</template>

<script>
import { defineComponent } from "vue";
import { useRouter } from "vue-router";
import { NConfigProvider } from "naive-ui";
import appThemeOverrides from "./themes/app.json";

export default defineComponent({
  components: {
    NConfigProvider,
  },
  setup() {
    const router = useRouter();
    return {
      appThemeOverrides,
    };
  },
});
</script>

<style lang="scss">
div {
  box-sizing: border-box;
}

.n-tabs-tab--active {
  background-color: #fff !important;
}
</style>
