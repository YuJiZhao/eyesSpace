<template>
  <div class="image" @click="openPic">
    <img class="previewPic" v-lazy="url" />
  </div>
  <base-dialog v-if="isOpen" :close="false" @close="closePic">
    <img class="completePic" :src="url">
  </base-dialog>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import BaseDialog from "@/components/general/popup/dialogComponent/BaseDialog.vue";

export default defineComponent({
  components: { BaseDialog },
  props: {
    url: String,
    size: String,
    width: String,
    height: String,
    radius: String
  },
  setup(props) {
    let isOpen = ref(false);
    let boxWidth = ref(props.size || props.width || "0px");
    let boxHeight = ref(props.size || props.height || "0px");
    let imgWidth = ref(boxWidth.value);
    let imgHeight = ref(boxHeight.value);
    let imgTransformX = ref("0px");
    let imgTransformY = ref("0px");

    function openPic() {
      isOpen.value = true;
    }

    function closePic() {
      isOpen.value = false;
    }

    function getSize() {
      const image = new Image();
      image.src = props.url!;
      image.onload = () => {
        let boxWidthNum = Number(boxWidth.value.replace("px", ""));
        let boxHeightNum = Number(boxHeight.value.replace("px", ""));
        if((image.width / image.height) > (boxWidthNum / boxHeightNum)) {
          imgHeight.value = boxHeight.value;
          let widthNum = image.width * (boxHeightNum / image.height);
          imgWidth.value = widthNum + "px";
          imgTransformX.value = (boxWidthNum - widthNum) / 2 + "px";
          imgTransformY.value = "0px";
        } else {
          imgWidth.value = boxWidth.value;
          let heightNum = image.height * (boxWidthNum / image.width);
          imgHeight.value = heightNum + "px";
          imgTransformX.value = "0px";
          imgTransformY.value = (boxHeightNum - heightNum) / 2 + "px";
        }
      }
    }
    onMounted(() => {
      getSize();
    })

    return {
      url: props.url,
      radius: props.radius || "0px",
      boxWidth,
      boxHeight,
      imgWidth,
      imgHeight,
      imgTransformX,
      imgTransformY,
      isOpen,
      openPic,
      closePic
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.image {
  width: v-bind(boxWidth);
  height: v-bind(boxHeight);
  border-radius: v-bind(radius);
  overflow: hidden;
  cursor: pointer;
  .previewPic {
    display: block;
    width: v-bind(imgWidth);
    height: v-bind(imgHeight);
    transform: translateX(v-bind(imgTransformX)) translateY(v-bind(imgTransformY));
  }
}

.completePic {
  display: block;
  width: 100%;
}
</style>