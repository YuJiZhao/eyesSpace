<template>
</template>

<script lang="ts">
import { defineComponent, inject, onMounted } from "vue";
import { useRoute } from "vue-router";
import { siteConfig } from "@/config/program";
import { HelpInterface } from "@/d.ts/plugin";
import useProcessControl from "@/hooks/useProcessControl";

export default defineComponent({
  setup() {
    const route = useRoute();
    const $utils = inject<HelpInterface>("$utils")!;

    let sToken = route.query[siteConfig.tokenHeader.sToken] as string;
    let lToken = route.query[siteConfig.tokenHeader.lToken] as string;
    if (sToken != undefined && lToken != undefined) {
        $utils.setCookie(siteConfig.tokenHeader.sToken, sToken, siteConfig.tokenExpireTime);
        $utils.setCookie(siteConfig.tokenHeader.lToken, lToken, siteConfig.tokenExpireTime);
    }
    location.replace(process.env.VITE_SITE_URL!);

    onMounted(() => {
      useProcessControl(false, false, false);
    })
    
    return {};
  },
});
</script>