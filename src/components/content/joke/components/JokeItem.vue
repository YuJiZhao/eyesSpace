<template>
  <div class="jokeItem">

  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted } from "vue";
import Image from "@/components/general/image/Image.vue";

export default defineComponent({
  components: { Image },
  props: ["data"],
  setup(props) {

    onMounted(() => {
      console.log(props.data);
    })

    return {
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.jokeItem {
  width: 300px;
  min-width: 200px;
  max-width: 400px;
  background: black;
}
</style>