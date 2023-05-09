<template>
  <div class="blogPublish">
    <div class="head">
      <div class="leftBar"><n-input v-model:value="title" round placeholder="请输入博客标题" /></div>
      <n-button type="success" @click="openBlogDialog">发布博客</n-button>
    </div>

    <v-md-editor
      class="editor"
      v-model="text"
      height="400px"
      :disabled-menus="[]"
      :left-toolbar="leftToolbar"
      @change="getWord"
      @upload-image="handleUploadImage"
    />

    <div class="foot">
      <div>{{timeNow}}</div>
      <div>字数: {{words}}</div>
    </div>

    <BlogPublishDialog 
      v-if="isOpen"
      :title="title"
      :content="text"
      :words="words"
      @close="close"
      @clearData="clearData"
    />
  </div>
</template>

<script>
import { defineComponent, onMounted, reactive, ref } from "vue";
import { useMessage } from "naive-ui";
import { urlConfig } from "@/config";
import BlogPublishDialog from "@/components/content/blog/components/BlogPublishDialog.vue";
import $api from "@/server/api";
import $utils from "@/utils/helper";

export default defineComponent({
  components: { BlogPublishDialog },
  setup() {
    const message = useMessage();

    const leftToolbar = "undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code | save | emoji";
    let title = ref("");
    let text = ref("# Hello eyes!");
    let timeNow = ref("");
    let words = ref("");
    let isOpen = ref(false);

    function close() {
      isOpen.value = false;
    }

    function getTime() {
      setInterval(() => {
        let time = $utils.getNowTime();
        timeNow.value = time.year + "年" + time.month + "月" + time.day + "日" + " " + time.week + " " + time.hour + ":" + time.minute + ":" + time.second;
      }, 1000);
    }

    function getWord() {
      words.value = text.value.length;
    }

    function storageBlog() {
      setInterval(() => {
        localStorage.setItem("blog", text.value);
      }, 30000);
    }

    function openBlogDialog() {
      isOpen.value = true;
    }

    function clearData() {
      close()
      title.value = "";
      text.value = "";
    }

    function handleUploadImage(event, insertImage, files) {
      for(let i in files){
        const formData = new FormData();
        formData.append('file', files[i]);
        $api.addBlogPic(formData).then((res) => {
          if(res.code == 200) {
            insertImage({
              url: res.data.url,
              desc: '博客插图',
            });
          } else {
            message.error("上传错误：" + res.msg);
          }
        })
      }
    }

    onMounted(() => {
      getTime();
      storageBlog();
      text.value = localStorage.getItem("blog") || "";
    })

    return {
      leftToolbar,
      title,
      text,
      timeNow,
      words,
      isOpen,
      close,
      openBlogDialog,
      getWord,
      clearData,
      handleUploadImage
    };
  },
});
</script>

<style scoped lang="scss">
.blogPublish {
  .head {
    display: flex;
    justify-content: space-between;
    margin: 10px 0;
    .leftBar {
      width: 400px;
      text-align: center;
    }
  }
  .foot {
    display: flex;
    justify-content: space-between;
    font-size: 10px;
    margin-top: 10px;
  }
}
</style>
