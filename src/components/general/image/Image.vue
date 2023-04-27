<template>
  <div class="image">
    <img class="previewPic" v-lazy="url" @click.stop="handlePreview" />
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import { v3ImgPreviewFn } from "v3-img-preview";

export default defineComponent({
  components: {},
  props: {
    url: String,
    size: String,
    width: String,
    height: String,
    radius: String,
    imgArray: Array,
  },
  setup(props) {
    let boxWidth = ref(props.size || props.width || "0px");
    let boxHeight = ref(props.size || props.height || "0px");
    let imgWidth = ref(boxWidth.value);
    let imgHeight = ref(boxHeight.value);
    let imgTransformX = ref("0px");
    let imgTransformY = ref("0px");
    let sources = props.imgArray || [props.url];

    function getSize() {
      const image = new Image();
      image.src = props.url!;
      image.onload = () => {
        let boxWidthNum = Number(boxWidth.value.replace("px", ""));
        let boxHeightNum = Number(boxHeight.value.replace("px", ""));
        if (image.width / image.height > boxWidthNum / boxHeightNum) {
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
      };
    }

    const handlePreview = () => {
      v3ImgPreviewFn({
        images: <string[]>sources,
        index: sources.indexOf(props.url)
      });
    };

    onMounted(() => {
      getSize();
    });

    return {
      url: props.url,
      radius: props.radius || "0px",
      boxWidth,
      boxHeight,
      imgWidth,
      imgHeight,
      imgTransformX,
      imgTransformY,
      handlePreview
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
    transform: translateX(v-bind(imgTransformX))
      translateY(v-bind(imgTransformY));
  }
}

.completePic {
  display: block;
  width: 100%;
}
</style>