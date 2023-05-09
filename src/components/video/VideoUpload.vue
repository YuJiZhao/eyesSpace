<template>
  <div class="videoUpload">
    <n-form
      ref="formRef"
      :label-width="80"
      :model="formValue"
      size="medium"
      :rules="rules"
    >
      <n-grid :x-gap="12" :y-gap="8" :cols="2">
        <n-grid-item>
          <n-form-item label="标题" path="title">
            <n-input v-model:value="formValue.title" placeholder="输入标题" />
          </n-form-item>
        </n-grid-item>
        <n-grid-item>
          <n-form-item label="原作者" path="originalAuthor">
            <n-input
              v-model:value="formValue.originalAuthor"
              placeholder="原作者"
            />
          </n-form-item>
        </n-grid-item>
        <n-grid-item>
          <n-form-item label="原视频地址" path="originalUrl">
            <n-input
              v-model:value="formValue.originalUrl"
              placeholder="输入原视频地址"
            />
          </n-form-item>
        </n-grid-item>
        <n-grid-item>
          <n-form-item label="状态" path="status">
            <n-select v-model:value="formValue.status" :options="options" />
          </n-form-item>
        </n-grid-item>
        <n-grid-item>
          <n-form-item label="我的评论" path="ownerComment">
            <n-input
              v-model:value="formValue.ownerComment"
              type="textarea"
              placeholder="输入评论"
            />
          </n-form-item>
        </n-grid-item>
      </n-grid>
    </n-form>

    <div class="bottom">
      <n-upload
        :directory-dnd="true"
        :action="urlConfig.videoCoverUrl"
        :headers="headers"
        :max="1"
        :accept="acceptImg"
        :on-finish="finishUploadImg"
        :on-error="uploadErrorImg"
      >
        <n-upload-dragger>
          <div style="margin-bottom: 12px">
            <n-icon size="48" :depth="3">
              <ArchiveOutline />
            </n-icon>
          </div>
          <n-text style="font-size: 16px">
            点击或者拖动文件到该区域来上传
          </n-text>
          <n-p depth="3" style="margin: 8px 0 0 0"> 此处上传封面 </n-p>
        </n-upload-dragger>
      </n-upload>

      <n-upload
        :directory-dnd="true"
        :action="urlConfig.videoFileUrl"
        :headers="headers"
        :max="1"
        :accept="acceptVideo"
        :on-finish="finishUploadVideo"
        :on-error="uploadErrorVideo"
      >
        <n-upload-dragger>
          <div style="margin-bottom: 12px">
            <n-icon size="48" :depth="3">
              <ArchiveOutline />
            </n-icon>
          </div>
          <n-text style="font-size: 16px">
            点击或者拖动文件到该区域来上传
          </n-text>
          <n-p depth="3" style="margin: 8px 0 0 0"> 此处上传视频 </n-p>
        </n-upload-dragger>
      </n-upload>
    </div>
    <n-button class="submitBtn" type="info" @click="addVideo">点击发布</n-button>
  </div>
</template>

<script>
import { reactive } from "vue";
import { useMessage } from "naive-ui";
import { ArchiveOutline } from "@vicons/ionicons5";
import { urlConfig, programConfig } from "@/config";
import $api from "@/server/api";
import $utils from "@/utils/helper";

export default {
  emits: ["playVideo"],
  components: { ArchiveOutline },
  setup(props, ctx) {
    const message = useMessage();

    let options = $utils.buildLabVal(["正常", "暂存"], [0, 1]);
    let acceptImg = ".jpg,.jpeg,.png";
    let acceptVideo = ".mp4";
    let formValue = reactive({
      videoUrl: "",
      pictureUrl: "",
      title: "",
      originalAuthor: "",
      originalUrl: "",
      ownerComment: "",
      status: 0,
    });
    let rules = {
      title: {
        required: true,
        message: "请输入视频标题",
        trigger: ["blur"],
      },
    };
    let headers = {
      Authorization: programConfig.token
    }

    function finishUploadImg(options) {
      let res = JSON.parse(options.event.target.response);
      if (res.code == 200) {
        formValue.pictureUrl = res.data.url;
        message.success("封面上传成功");
      }
    }

    function finishUploadVideo(options) {
      let res = JSON.parse(options.event.target.response);
      if (res.code == 200) {
        formValue.videoUrl = res.data.url;
        message.success("视频上传成功");
      }
    }

    function uploadErrorImg() {
      formValue.pictureUrl = "";
    }

    function uploadErrorVideo() {
      formValue.videoUrl = "";
    }

    async function addVideo() {
      if (formValue.pictureUrl == "" || formValue.videoUrl == "") {
        formValue.pictureUrl == ""
          ? message.error("请上传封面！")
          : message.error("请上传视频！");
        return;
      }
      await $api.addVideo(formValue).then((res) => {
        if (res.code == 200) {
          message.success("视频添加成功！");
          sessionStorage.setItem("videoId", res.data.id);
          ctx.emit("playVideo");
        } else {
          message.error("视频上传失败！错误信息：" + res.msg);
        }
      });
    }

    return {
      formValue,
      options,
      rules,
      acceptImg,
      acceptVideo,
      urlConfig,
      headers,
      finishUploadImg,
      finishUploadVideo,
      uploadErrorImg,
      uploadErrorVideo,
      addVideo,
    };
  },
};
</script>

<style scoped lang="scss">
.videoUpload {
  .bottom {
    display: flex;
  }
  .submitBtn {
    margin-top: 20px;
    margin-left: 1000px;
  }
}
</style>
