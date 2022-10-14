<template>
  <avatar-upload
    class="upload"
    field="file"
    :width="300"
    :height="300"
    :noSquare="true"
    :withCredentials="true"
    v-model="show"
    :url="uploadUrl"
    :headers="headers"
    @srcFileSet="srcFileSet"
    @crop-upload-success="cropUploadSuccess"
    @crop-upload-fail="cropUploadFail"
  ></avatar-upload>
</template>

<script lang="ts">
import { defineComponent, inject, ref, watch } from "vue";
import { ProcessInterface, HelpInterface } from "@/d.ts/plugin";
import { urlConfig, siteConfig, codeConfig } from "@/config/program";
import AvatarUpload from "vue-image-crop-upload";

export default defineComponent({
  props: ["show"],
  emits: ["upload-success", "close"],
  components: { AvatarUpload },
  setup(props, ctx) {
    const $process = inject<ProcessInterface>("$process")!;
    const $utils = inject<HelpInterface>("$utils")!;

    let show = ref(false);
    let headers = ref({
      Authorization: $utils.getCookie(siteConfig.tokenHeader),
    });

    function srcFileSet(fileName: string, fileType: string, fileSize: number) {
      if(!siteConfig.avatarImgType.includes(fileType)) {
        $process.tipShow.warn(`不支持${fileType}格式`);
        let clear = setInterval(() => {
          if(document.querySelector(".vicp-step2 .vicp-operate > a")) {
            (document.querySelector(".vicp-step2 .vicp-operate > a") as HTMLElement).click();
            clearInterval(clear);
          }
        }, 100)
      }
      if ($utils.byte2MB(fileSize) > 5) {
        $process.tipShow.warn(`头像图片最大为${siteConfig.avatarMaxSize}MB`);
        let clear = setInterval(() => {
          if(document.querySelector(".vicp-step2 .vicp-operate > a")) {
            (document.querySelector(".vicp-step2 .vicp-operate > a") as HTMLElement).click();
            clearInterval(clear);
          }
        }, 100)
      }
    }

    function cropUploadSuccess(jsonData: any) {
      if(jsonData.code == codeConfig.success) {
        setTimeout(() => {
          ctx.emit("upload-success", jsonData.data.url);
        }, 1000)
      } else {
        $process.tipShow.error(jsonData.msg);
        let clear = setInterval(() => {
          if(document.querySelector(".vicp-step3 .vicp-operate > a")) {
            (document.querySelector(".vicp-step3 .vicp-operate > a") as HTMLElement).click();
            clearInterval(clear);
          }
        }, 100)
      }
    }

    function cropUploadFail() {
      $process.tipShow.error("图片上传失败");
    }

    watch(
      () => props.show,
      (value) => {
        show.value = value;
      },
      { immediate: true }
    )

    watch(
      () => show.value,
      (value) => {
        if(!value) ctx.emit("close");
      }
    )

    return {
      show,
      uploadUrl: urlConfig.avatarUploadUrl,
      headers,
      srcFileSet,
      cropUploadSuccess,
      cropUploadFail,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

@media screen and (max-width: 800px) {
  :deep(.vicp-wrap) {
    width: 370px !important;
    padding: 5px;
    .vicp-crop {
      margin-top: 40px;
    }
    .vicp-range {
      display: none;
    }
    .vicp-crop-right {
      display: flex;
      align-items: center;
    }
  }
}

:deep(.vicp-wrap) {
  width: 500px;
  .vicp-operate a {
    color: $normal;
    box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    border-radius: 5px;
    &:hover {
      background: none;
    }
  }
  .vicp-close {
    display: none;
  }
  .vicp-crop {
    display: flex;
    justify-content: space-around;
    .vicp-crop-right {
      display: flex;
      align-items: center;
    }
  }
}

.upload {
  background: none !important;
}
</style>
