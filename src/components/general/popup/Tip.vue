<template>
  <div class="tip">
    <transition-group name="tipAnimate">
      <div class="tipItem" v-for="item in msgList" :key="item">
        <img :src="tipType[item.type]" />
        <div class="word">{{ item.msg }}</div>
      </div>
    </transition-group>
  </div>
</template>

<script lang="ts">
import { defineComponent, watch, inject, ref, Ref, UnwrapNestedRefs } from "vue";
import { ProcessInterface } from "@/d.ts/modules/process";
import { tipType } from "@/config/site";

export default defineComponent({
  components: {},
  setup() {
    const $process = inject<ProcessInterface>("$process")!;

    let msgList: Ref<Array<UnwrapNestedRefs<{ msg: string; type: number }>>> = ref([]);

    watch(
      () => $process.tipSentry.value,
      () => {
        msgList.value.push({
          msg: $process.tipList.msg,
          type: $process.tipList.type,
        });
        setTimeout(() => {
          msgList.value.splice(0, 1);
        }, 3000);
      }
    );

    return {
      msgList,
      tipType,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.tipAnimate-enter-active {
  animation: flipInX 0.8s;
}
.tipAnimate-leave-active {
  animation: flipOutX 0.8s;
}

.tip {
  position: fixed;
  z-index: 1000;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  .tipItem {
    min-width: 150px;
    max-width: 300px;
    min-height: 30px;
    background: $white;
    border-radius: 5px;
    box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    padding: 5px 10px;
    margin-bottom: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: $normal;
    img {
      width: 20px;
      height: 20px;
      display: block;
      margin-right: 3px;
    }
  }
}
</style>
