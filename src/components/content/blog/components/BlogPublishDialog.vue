<template>
  <div class="mask"></div>
  <div class="blogPublishDialog">
    <div class="title">发布博客</div>

    <n-form
      ref="formRef"
      :model="model"
      label-placement="left"
      label-width="auto"
    >
      <n-form-item label="文章摘要" path="summary">
        <n-input
          v-model:value="model.summary"
          type="textarea"
          placeholder="文章摘要"
        />
      </n-form-item>

      <n-form-item label="分类专栏" path="newCategory">
        <n-dynamic-tags v-model:value="model.newCategory" :max="1">
          <template #trigger="{ activate }">
            <n-button size="small" type="primary" dashed @click="activate()">
              <template #icon>
                <n-icon><Add /></n-icon>
              </template>
              新增
            </n-button>
          </template>
        </n-dynamic-tags>
      </n-form-item>

      <div class="categoryBox">
        <div class="top">现有专栏:</div>
        <div>
          <n-radio-group
            class="radioGroups"
            v-model:value="model.category"
            name="category"
          >
            <n-space>
              <n-radio value="Java">Java</n-radio>
              <n-radio value="JavaScript">JavaScript</n-radio>
              <n-radio value="人工智能">人工智能</n-radio>
              <n-radio value="大数据">大数据</n-radio>
              <n-radio value="数据库">数据库</n-radio>
              <n-radio value="运维">运维</n-radio>
              <n-radio value="计算机网络">计算机网络</n-radio>
              <n-radio value="算法">算法</n-radio>
              <n-radio value="客户端">客户端</n-radio>
              <n-radio value="php">php</n-radio>
              <n-radio value="脚本">脚本</n-radio>
              <n-radio value="测试">测试</n-radio>
              <n-radio value="C++">C++</n-radio>
              <n-radio value="软件工程">软件工程</n-radio>
              <n-radio value="Python">Python</n-radio>
              <n-radio value="游戏开发">游戏开发</n-radio>
              <n-radio value="机器学习">机器学习</n-radio>
              <n-radio value="杂">杂</n-radio>
            </n-space>
          </n-radio-group>
        </div>
      </div>

      <n-form-item label="文章标签" path="label">
        <n-dynamic-tags v-model:value="model.labels" :max="3">
          <template #trigger="{ activate }">
            <n-button size="small" type="primary" dashed @click="activate()">
              <template #icon>
                <n-icon><Add /></n-icon>
              </template>
              新增
            </n-button>
          </template>
        </n-dynamic-tags>
      </n-form-item>

      <n-radio-group
        class="radioGroup"
        v-model:value="model.status"
        name="status"
      >
        <n-space>
          发布形式
          <n-radio value="0"> 公开 </n-radio>
          <n-radio value="1"> 私有 </n-radio>
        </n-space>
      </n-radio-group>
    </n-form>
    <div class="buttonGroup">
      <n-button class="button" @click="$emit('close')">取消发布</n-button>
      <n-button type="success" @click="addBlog">确认发布</n-button>
    </div>
  </div>
</template>

<script>
import { defineComponent, reactive, ref } from "vue";
import { useMessage } from "naive-ui";
import { Add } from "@vicons/ionicons5";
import $api from "@/server/api";

export default defineComponent({
  emits: ["close", "clearData"],
  props: ["title", "content", "words"],
  components: { Add },
  setup(props, ctx) {
    const message = useMessage();

    let model = reactive({
      summary: props.content.substr(0, 100),
      category: "杂",
      newCategory: [],
      status: 0,
      labels: []
    });

    async function addBlog() {
        await $api.addBlog({
            title: props.title,
            content: props.content,
            words: props.words,
            summary: model.summary,
            category: model.newCategory.length ? model.newCategory[0] : model.category,
            status: model.status,
            labels: model.labels
        }).then((res) => {
          if(res.code == 200) {
            message.success("发布成功！博客id：" + res.data.id);
            ctx.emit("clearData");
          } else {
            message.error(res.msg);
          }
        });
    }

    return {
      model,
      addBlog
    };
  },
});
</script>

<style scoped lang="scss">
.mask {
  width: 100vw;
  height: 100vh;
  background: rgba($color: #000000, $alpha: 0.5);
  position: fixed;
  top: 0;
  left: 0;
  z-index: 99;
}

.blogPublishDialog {
  width: 500px;
  min-height: 300px;
  background-color: #fff;
  border-radius: 10px;
  padding: 20px;
  position: fixed;
  z-index: 100;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  .title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 20px;
  }
  .radioGroup {
    line-height: 34px;
    margin-right: 20px;
  }
  .buttonGroup {
    margin-top: 10px;
    margin-left: 280px;
    .button {
      margin-right: 10px;
    }
  }
  .categoryBox {
    width: 390px;
    min-height: 50px;
    border: 1px dashed #c1c0c0;
    margin-left: 68px;
    margin-top: -20px;
    margin-bottom: 20px;
    padding: 10px;
    .top {
      border-bottom: 1px dashed #c1c0c0;
      height: 30px;
      line-height: 20px;
      margin-bottom: 10px;
    }
  }
}
</style>
