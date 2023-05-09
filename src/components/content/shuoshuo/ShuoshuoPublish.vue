<template>
  <div class="shuoshuoPublish">
    <v-md-editor v-model="text" height="300px" :left-toolbar="leftToolbar" />
    <n-upload
      :action="actionUrl"
      list-type="image-card"
      :accept="accept"
      :on-finish="finishUpload"
      :headers="{
        'Authorization': authorization
      }"
      @preview="handlePreview"
    />
    <n-modal v-model:show="showModal" preset="card" style="width: 600px">
      <img :src="previewImageUrl" style="width: 100%" />
    </n-modal>

    <div class="foot">
      <n-radio-group class="radioGroup" v-model:value="status" name="status">
        <n-space>
          <n-radio value="0"> 公开 </n-radio>
          <n-radio value="1"> 私有 </n-radio>
        </n-space>
      </n-radio-group>
      <div class="button">
        <n-button type="success" @click="addShuoshuo">发布说说</n-button>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent, reactive, ref } from "vue";
import { useMessage } from "naive-ui";
import { urlConfig, programConfig } from "@/config";
import $api from "@/server/api";

export default defineComponent({
  components: {},
  setup() {
    const message = useMessage();

    const leftToolbar =
      "undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code | save | emoji";
    const text = ref("# Hello eyes!");

    const accept = ".jpg,.png,.jpeg";
    let showModal = ref(false);
    let previewImageUrl = ref("");
    let fileList = ref([]);
    let status = ref(0);

    function handlePreview(file) {
      let url = "";
      fileList.value.forEach((v) => {
        if (v.pid == file.id) url = v.url;
      });
      previewImageUrl.value = url;
      showModal.value = true;
    }

    function finishUpload(options) {
      let res = JSON.parse(options.event.target.response);
      if (res.code == 200) {
        fileList.value.push({
          pid: options.file.id,
          url: res.data.url,
        });
        message.success("图片上传成功");
      }
    }

    async function addShuoshuo() {
      await $api
        .addShuoshuo({
          content: text.value,
          picList: fileList.value,
          status: status.value,
        })
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            message.success("发布说说成功");
          } else {
            message.error("发布说说失败，错误信息：" + res.msg);
          }
        });
    }

    return {
      leftToolbar,
      text,
      accept,
      actionUrl: urlConfig.shuoPicUploadUrl,
      authorization: programConfig.token,
      status,
      showModal,
      previewImageUrl,
      handlePreview,
      finishUpload,
      addShuoshuo,
    };
  },
});
</script>

<style scoped lang="scss">
.shuoshuoPublish {
  .foot {
    display: flex;
    justify-content: flex-end;
    .radioGroup {
      line-height: 34px;
      margin-right: 20px;
    }
  }
}
</style>
