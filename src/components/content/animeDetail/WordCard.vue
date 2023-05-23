<template>
  <div class="wordCard">
    <div class="title">站长说</div>
    <anime-md :content="word" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AnimeMd from "./component/AnimeMd.vue";

export default defineComponent({
  components: { AnimeMd },
  props: ["word"],
  setup(props) {
    return {
        word: props.word,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.wordCard {
  width: calc(100% - 5px);
  min-height: 100px;
  margin: 0 auto;
  margin-top: 2px;
  margin-bottom: 20px;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 0 4px rgba($color: $black, $alpha: 0.7);
  -webkit-box-shadow: 0 0 4px rgba($color: $black, $alpha: 0.7);
  -moz-box-shadow: 0 0 4px rgba($color: $black, $alpha: 0.7);
  color: $normal;
  .title {
    height: 30px;
    line-height: 30px;
    text-align: center;
    font-size: 20px;
  }
}
</style>