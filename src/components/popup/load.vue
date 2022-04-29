<template>
  <div class="load" v-if="status">
    <div class="circ">
      <div class="loading">Loading . . .</div>
      <div class="hands"></div>
      <div class="body"></div>
      <div class="head">
        <div class="eye"></div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
/**
 * 页面跳转加载动画
 */
import { defineComponent, inject, ref, watch } from "vue";
import { PopupType } from "@/d.ts/modules";
// BUG：不知道为什么，load无法控制
export default defineComponent({
  setup() {
    const $popup = inject<PopupType>("$popup")!;

    let status = ref($popup.loadStatus.value);

    watch(
      () => $popup.loadStatus.value,
      value => {
        status.value = value;
      }
    );

    return {
      status,
    };
  },
});
</script>

<style lang="scss" scoped>
.load {
  width: 100vw;
  height: 100vh;
  background-color: rgb(55, 71, 79);
  position: fixed;
  z-index: 100;
  top: 0;
  left: 0;
  .circ {
    -webkit-backface-visibility: hidden;
    width: 180px;
    height: 180px;
    background-color: rgb(55, 71, 79);
    border-radius: 0px 0px 50px 50px;
    position: absolute;
    left: 50%;
    top: 40%;
    transform: translate(-50%, -50%);
    overflow: hidden;
    .loading {
      position: absolute;
      width: 7ch;
      height: 35px;
      text-align: left;
      line-height: 32px;
      margin: -10px auto;
      -webkit-font-smoothing: antialiased;
      font-family: "Julius Sans One", sans-serif;
      font-size: 28px;
      font-weight: 400;
      color: rgba(240, 220, 220, 1);
      left: 2%;
      top: 3%;
      -webkit-animation: fontAnim 3.75s infinite;
      -webkit-animation-timing-function: ease-out;
      word-wrap: break-word;
      display: block;
      overflow: hidden;
    }
    .hands {
      -webkit-backface-visibility: hidden;
      margin-top: 140px;
      width: 120px;
      height: 120px;
      position: absolute;
      background-color: rgb(55, 71, 79);
      border-radius: 20px;
      box-shadow: -1px -4px 0px 0px rgba(240, 220, 220, 1);
      -webkit-transform: rotate(45deg);
      top: 75%;
      left: 16%;
      z-index: 1;
      -webkit-animation: bodyAnim 1.5s infinite alternate;
      -webkit-animation-timing-function: ease-out;
    }
    .body {
      position: relative;
      margin: 90px auto;
      width: 140px;
      height: 120px;
      background-color: rgb(55, 71, 79);
      border-radius: 50px/25px;
      box-shadow: inset -5px 2px 0px 0px rgba(240, 220, 220, 1);
      -webkit-animation: bodyAnim 1.5s infinite alternate;
      -webkit-animation-timing-function: ease-out;
    }
    .head {
      -webkit-backface-visibility: hidden;
      position: relative;
      margin: -250px auto;
      width: 80px;
      height: 80px;
      background-color: rgb(55, 71, 79);
      border-radius: 50px;
      box-shadow: inset -4px 2px 0px 0px rgba(240, 220, 220, 1);
      -webkit-animation: headAnim 1.5s infinite alternate;
      -webkit-animation-timing-function: ease-out;
      .eye {
        width: 20px;
        height: 8px;
        background-color: rgba(240, 220, 220, 1);
        border-radius: 0px 0px 20px 20px;
        position: relative;
        left: 10px;
        top: 40px;
        box-shadow: 40px 0px 0px 0px rgba(240, 220, 220, 1);
      }
    }
  }

  @-webkit-keyframes headAnim {
    0% {
      top: 0px;
    }

    50% {
      top: 10px;
    }

    100% {
      top: 0px;
    }
  }

  @-webkit-keyframes bodyAnim {
    0% {
      top: -5px;
    }

    50% {
      top: 10px;
    }

    100% {
      top: -5px;
    }
  }

  @-webkit-keyframes fontAnim {
    0% {
      width: 7ch;
    }

    16% {
      width: 8ch;
    }

    32% {
      width: 9ch;
    }

    48% {
      width: 10ch;
    }

    64% {
      width: 11ch;
    }

    80% {
      width: 12ch;
    }

    100% {
      width: 13ch;
    }
  }
}
</style>
