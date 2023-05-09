<template>
  <div class="musicUpload">
    <n-form
      ref="formRef"
      :label-width="80"
      :model="formValue"
      size="medium"
      :rules="rules"
    >
      <n-grid :x-gap="12" :y-gap="8" :cols="2">
        <n-grid-item>
          <n-form-item label="歌名" path="title">
            <n-input v-model:value="formValue.title" placeholder="输入歌名" />
          </n-form-item>
        </n-grid-item>
        <n-grid-item>
          <n-form-item label="歌手" path="author">
            <n-input v-model:value="formValue.author" placeholder="歌手" />
          </n-form-item>
        </n-grid-item>
        <n-grid-item>
          <n-form-item label="歌曲状态" path="status">
            <n-select v-model:value="formValue.status" :options="options" />
          </n-form-item>
        </n-grid-item>
        <n-grid-item>
          <n-form-item label="歌词" path="lrc">
            <n-input
              v-model:value="formValue.lrc"
              type="textarea"
              placeholder="输入歌词"
            />
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
        :action="urlConfig.musicCoverUrl"
        :max="1"
        :headers="headers"
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
        :action="urlConfig.musicFileUrl"
        :max="1"
        :headers="headers"
        :accept="acceptMusic"
        :on-finish="finishUploadMusic"
        :on-error="uploadErrorMusic"
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
          <n-p depth="3" style="margin: 8px 0 0 0"> 此处上传音频 </n-p>
        </n-upload-dragger>
      </n-upload>
    </div>
    <n-button class="submitBtn" type="info" @click="addMusic">点击发布</n-button>
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
  emits: ["playMusic"],
  components: { ArchiveOutline },
  setup(props, ctx) {
    const message = useMessage();

    let options = $utils.buildLabVal(["正常", "暂存"], [0, 1]);
    let acceptImg = ".jpg,.jpeg,.png";
    let acceptMusic = ".mp3,.m4a";
    let formValue = reactive({
      url: "",
      pic: "",
      title: "",
      author: "",
      lrc: "",
      ownerComment: "",
      status: 0,
    });
    let rules = {
      title: {
        required: true,
        message: "请输入歌名",
        trigger: ["blur"],
      },
    };
    let headers = {
      Authorization: programConfig.token
    }

    function finishUploadImg(options) {
      let res = JSON.parse(options.event.target.response);
      if (res.code == 200) {
        formValue.pic = res.data.url;
        message.success("封面上传成功");
      }
    }

    function finishUploadMusic(options) {
      let res = JSON.parse(options.event.target.response);
      if (res.code == 200) {
        formValue.url = res.data.url;
        message.success("音频上传成功");
      }
    }

    function uploadErrorImg() {
      formValue.pic = "";
    }

    function uploadErrorMusic() {
      formValue.url = "";
    }

    async function addMusic() {
      if (formValue.pic == "" || formValue.url == "") {
        formValue.pic == ""
          ? message.error("请上传封面！")
          : message.error("请上传音频！");
        return;
      }
      await $api.addMusic(formValue).then((res) => {
        if (res.code == 200) {
          message.success("音频添加成功！");
          sessionStorage.setItem("musicId", res.data.id);
          ctx.emit("playMusic");
        } else {
          message.error("音频上传失败！错误信息：" + res.msg);
        }
      });
    }

    return {
      formValue,
      options,
      rules,
      acceptImg,
      acceptMusic,
      urlConfig,
      headers,
      finishUploadImg,
      finishUploadMusic,
      uploadErrorImg,
      uploadErrorMusic,
      addMusic,
    };
  },
};
</script>

<style scoped lang="scss">
.musicUpload {
  .bottom {
    display: flex;
  }
  .submitBtn {
    margin-top: 20px;
    margin-left: 1000px;
  }
}
</style>
