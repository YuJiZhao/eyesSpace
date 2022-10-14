<template>
  <div class="iconBtn">
    <img class="icon" :src="icon">
    <div class="word">{{word}}</div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";

export default defineComponent({
  components: {},
  props: ["icon", "word", "width", "height"],
  setup(props) {

    return {
        icon: props.icon,
        word: props.word,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.iconBtn {
    width: 95px;
    height: 35px;
    border-radius: 5px;
    box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    .icon {
        width: 20px;
        height: 20px;
        display: block;
        margin-right: 3px;
    }
}
</style>