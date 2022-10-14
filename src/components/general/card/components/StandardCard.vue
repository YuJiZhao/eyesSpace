<template>
<base-card>
  <div class="standardCard">
    <div class="header">
      <img :src="icon">
      <div class="title">{{title}}</div>
    </div>
    <slot />
  </div>
</base-card>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import BaseCard from "./BaseCard.vue";

export default defineComponent({
  props: ["icon", "title"],
  components: { BaseCard },
  setup() {
    return {
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.standardCard {
  .header {
    margin-bottom: 20px;
    display: flex;
    img {
      width: 20px;
      height: 20px;
      display: block;
      margin-right: 5px;
    }
    .title {
      height: 20px;
      line-height: 20px;
      font-size: 18px;
      color: $title;
    }
  }
}
</style>