<template>
  <div class="homeList">
    <div class="homeItem" v-for="item in homeListData" :key="item">
      <home-item :data="item" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import HomeItem from "@/components/content/home/components/HomeItem.vue";

export default defineComponent({
  components: { HomeItem },
  props: ["homeListData"],
  setup(props) {
    return {
      homeListData: props.homeListData,
    };
  },
});
</script>