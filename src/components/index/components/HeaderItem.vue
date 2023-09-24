<template>
  <div class="headerItem">
    <div class="barItem" v-for="item in headerConfig" :key="item.word">
      <div class="option" v-if="!item.children" @click="pageJump(item.path)">
        <img :src="item.icon" />
        <div class="word">{{ item.word }}</div>
      </div>
      <div class="dropDown" v-else>
        <div class="link">
          <img :src="item.icon" />
          <div class="word">{{ item.word }}</div>
        </div>
        <div class="list">
          <div class="child" v-for="child in item.children" :key="child.word" @click="pageJump(child.path)">
            <img :src="child.icon" />
            <div class="word">{{ child.word }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { useRouter } from "vue-router";
import { headerConfig } from "@/config/site";

export default defineComponent({
  setup() {
    const router = useRouter();
    
    function pageJump(path: string) {
      router.push(path);
    }

    return {
      headerConfig,
      pageJump
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.headerItem {
  display: flex;
  .barItem {
    width: 60px;
    margin-right: 20px;
    .option, .link, .child {
      display: flex;
      justify-content: center;
      align-items: center;
      img {
        width: 16px;
        height: 16px;
        display: block;
      }
      .word {
        line-height: 20px;
        margin-left: 5px;
      }
    }
    .option {
      cursor: pointer;
    }
    .dropDown {
      position: relative;
      cursor: default;
      .list {
        width: 0;
        height: 0;
        overflow: hidden;
        opacity: 0;
        transition: opacity 0.5s;
      }
      &:hover .list {
        width: 60px;
        height: auto;
        opacity: 1;
      }
      .child {
        margin-top: 20px;
        backdrop-filter: blur(1px);
        cursor: pointer;
      }
    }
  }
}
</style>