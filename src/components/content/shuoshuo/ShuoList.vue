<template>
  <div class="shuoList">
    <shuo-item
      class="shuoItem"
      v-for="item in shuoDataList" :key="item.id"
      :id="item.id"
      :content="item.content"
      :picList="item.picList"
      :views="item.views"
      :comments="item.comments"
      :createTime="item.createTime"
      @click.capture.stop="jumpDetail(item.id)"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ShuoItem from "@/components/content/shuoshuo/components/ShuoItem.vue"; 
import { shuoContext } from "@/components/content/shuoshuo/businessTs/shuoContext";
import { useRouter } from "vue-router";

export default defineComponent({
  components: { ShuoItem },
  setup() {
    let router = useRouter();

    function jumpDetail(id: string) {
      window.open(router.resolve({
        path: "/shuoshuo/details",
        query: { id }
      }).href, "_blank");
    }

    return {
      jumpDetail,
      shuoDataList: shuoContext.data
    };
  },
});
</script>

<style lang="scss" scoped>
.shuoList {
  width: 100%;
  min-width: 335px;
  margin: 0 auto;
  .shuoItem {
    cursor: pointer;
  }
}
</style>