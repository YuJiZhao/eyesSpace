<template>
  <div class="emojiInput">
    <textarea
      class="emoji-textarea"
      :placeholder="placeholder"
      v-model="text"
      @blur="changeValue"
    ></textarea>
    <div class="emoji-container" v-show="showEmoji"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, watch, inject } from "vue";
import resource from "@/config/resource";
import Emot from "emot";
import { HelpInterface } from "@/d.ts/plugin";
import { goComment } from "@/components/general/comment/businessTs/commentProcess";
import { commentContext } from "@/components/general/comment/config";

export default defineComponent({
  emits: ["changeValue"],
  setup(props, ctx) {
    const $utils = inject<HelpInterface>("$utils")!;
    const showEmoji = inject<boolean>("showEmoji")!;

    let text = ref("");
    let emot: any;
    let isShow = false;
    let emotItems: HTMLElement;

    function changeValue() {
      ctx.emit(
        "changeValue",
        emot.get().content.replace(/^\[#[0-9]+@.{2,10}\]：/, ""), 
        emot.get().contentHTML
      );
    }

    function initEmot() {
      emot = new Emot({
        el: ".emoji-container",
        target: ".emoji-textarea",
        emotMaps: resource.emojiJson,
      });
    }

    function containerSwatch() {
      isShow = !isShow;
      emotItems.style.display = isShow ? "block" : "none";
    }

    function clearText() {
      text.value = "";
      if(showEmoji) {
        emotItems.style.display ="none";
      }
    }

    let clear = setInterval(() => {
      if(!showEmoji) {
        clearInterval(clear);
        return;
      }
      if(document.querySelector(".emot-packages")) {
        emotItems = document.querySelector(".emot-items") as HTMLElement;
        (document.querySelector(".emot-packages") as HTMLElement).onclick = containerSwatch;
        clearInterval(clear);
      }
    }, 1000);

    watch(
      () => goComment.goCommentSentry.value,
      () => {
        text.value = "[#" + goComment.objectId.value + "@" + goComment.replyName.value + "]：";
        $utils.cursorMove(document.querySelector(".emoji-textarea"), text.value.length);
      }
    )

    onMounted(() => {
      initEmot();
    });

    return {
      text,
      placeholder: commentContext.inputPlaceholder,
      showEmoji,
      changeValue,
      clearText
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

:deep(.emoji-container > .emot) {
  border: none;
  background: none;
  .emot-items {
    resize: none;
    display: none;
    .emot-item {
      margin: 0;
    }
  }
  .emot-packages {
    border-top: none;
    width: 40px;
    .emot-package-active {
      background: none;
    }
  }
}

.emojiInput {
  width: calc(100% - 25px);
  margin-left: 2.5px;
  .emoji-textarea {
    width: 100%;
    height: 80px;
    color: $normal;
    background: none;
    border: none;
    border-radius: 5px;
    outline: none;
    resize: vertical;
    box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    padding: 10px;
  }
}
</style>