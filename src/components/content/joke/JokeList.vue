<template>
  <div class="jokeList">
    <div class="jokeItem" v-for="item in jokeListData" :key="item" @click.stop="handlePreview">
      <p>{{item.category}}</p>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import JokeItem from "./components/JokeItem.vue";
import { v3ImgPreviewFn } from "v3-img-preview";

export default defineComponent({
  components: { JokeItem },
  props: ["jokeListData"],
  setup(props) {

    function handlePreview() {
      v3ImgPreviewFn({
        images: <string[]>props.jokeListData.urlList,
        index: 0
      });
    }

    return {
      jokeListData: props.jokeListData,
      handlePreview
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.jokeList {
  width: 100%;
  min-width: 335px;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  .jokeItem {
    width: 130px;
    height: 130px;
    background-image: radial-gradient(#333, #888);
    margin: 0 10px 20px 10px;
    cursor: pointer;
    p {
      color: #fff;
      text-align: center;
      line-height: 130px;
    }
  }
}
</style>