<template>
  <standard-card title="站长说" :icon="icon">
    <Wait :show="show" :fail="isFail" height="100px">
        <div class="jokeNoticeCard">
            <div class="notice">{{ notice }}</div>
        </div>
    </Wait>
  </standard-card>
</template>

<script lang="ts">
import { defineComponent, inject, onBeforeMount, ref } from "vue";
import StandardCard from "@/components/general/card/components/StandardCard.vue";
import resource from "@/config/resource";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { codeConfig } from "@/config/program";
import { Wait } from "@/components/general/popup";

export default defineComponent({
  components: { StandardCard, Wait },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let show = ref(true);
    let isFail = ref(false);
    let notice = ref("");

    async function getJokeNotice() {
        show.value = true;
        isFail.value = false;
        $api.getJokeNotice().then(({code, msg, data}) => {
            if (code == codeConfig.success) {
                notice.value = data.notice;
                show.value = false;
            } else {
                $process.tipShow.error("站长说数据获取失败");
                isFail.value = true;
            }
        })
    }

    onBeforeMount(() => {
        getJokeNotice();
    })

    return {
      notice,
      icon: resource.announce,
      show,
      isFail
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.jokeNoticeCard {
  .notice {
    color: $normal;
    line-height: 18px;
  }
}
</style>