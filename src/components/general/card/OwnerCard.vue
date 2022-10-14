<template>
  <base-card>
    <div class="ownerCard">
      <div class="avatar" :style="{ backgroundImage: 'url(' + context.avatar + ')' }"></div>
      <div class="words">
        <p class="name">{{ context.name }}</p>
        <p class="motto">{{ context.motto }}</p>
      </div>
      <div class="footprint">
        <a 
          class="footItem"
          v-for="item in context.footprint" :key="item.name"
          :style="{ backgroundImage: 'url(' + item.icon + ')' }"
          :href="item.url"
          target="_blank"
        ></a>
      </div>
    </div>
  </base-card>
</template>

<script lang="ts">
import { defineComponent, inject } from "vue";
import BaseCard from "./components/BaseCard.vue";
import { ContextInterface } from "@/d.ts/plugin";

export default defineComponent({
  components: { BaseCard },
  setup() {
    const $context = inject<ContextInterface>("$context")!;

    let context = {
      avatar: $context.data.ownerAvatar,
      name: $context.data.ownerName,
      motto: $context.data.ownerMotto,
      footprint: $context.data.footprint!,
    };

    return {
      context
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.ownerCard {
  padding-top: 10px;
  .avatar {
    width: 110px;
    height: 110px;
    background-size: 100% 100%;
    border-radius: 50%;
    margin: 0px auto;
    margin-bottom: 20px;
  }
  .words {
    margin-bottom: 20px;
    .name {
      font-size: 20px;
      color: $title;
      text-align: center;
      margin-bottom: 10px;
    }
    .motto {
      color: $normal;
      text-align: center;
    }
  }
  .footprint {
    display: flex;
    justify-content: space-around;
    .footItem {
      width: 30px;
      height: 30px;
      background-size: 100% 100%;
      margin-top: 20px;
    }
  }
}
</style>