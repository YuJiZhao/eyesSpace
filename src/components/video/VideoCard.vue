<template>
  <div class="videoCard">
    <div class="dataCount">
      <span>视频总数：{{ videoListInfo.totalNum }}</span>
      <span>公开视频：{{ videoListInfo.publicNum }}</span>
      <span>私有视频：{{ videoListInfo.privateNum }}</span>
      <span>删除视频：{{ videoListInfo.deleteNum }}</span>
    </div>
    <div class="videoList" v-for="item in videoList" :key="item">
      <div class="listBox">
        <div class="listBoxLeft">
          <div
            class="picture"
            :style="{
              background: 'url(' + item.pictureUrl + ')',
              backgroundSize: '100% 100%',
            }"
          ></div>
          <div class="content">
            <div class="title">{{ item.title }}</div>
            <div class="upTime">
              <n-tag :bordered="false" :type="tags[item.status].label">{{tags[item.status].value}}</n-tag>
              {{ item.createTime }}
            </div>
            <div class="data">
              <span>播放量: {{ item.views }}</span>
              <span>点赞量: {{ item.likes }}</span>
              <span>点赞率: {{ item.views == 0 ? 0 : item.likes / item.views }}%</span>
              <span>评论数: {{ item.comments }}</span>
              <span>评论率: {{ item.views == 0 ? 0 : item.comments / item.views }}%</span>
            </div>
          </div>
        </div>
        <div class="operation">
          <n-button class="button" type="info" @click="playVideo(item.id)">
            <n-icon size="18" style="margin-left: -5px"
              ><CaretForwardCircleOutline
            /></n-icon>
            播放
          </n-button>
          <n-button class="button" type="info">
            <n-icon size="18" style="margin-left: -5px"><Brush /></n-icon>
            编辑
          </n-button>
          <n-button class="button" type="error">
            <n-icon size="18" style="margin-left: -5px"><Close /></n-icon>
            删除
          </n-button>
        </div>
      </div>
      <n-divider />
    </div>
    <n-pagination
      class="pagination"
      show-quick-jumper
      v-model:page="page"
      :default-page-size="pageSize"
      :item-count="videoListInfo.totalNum"
      :next="getVideoList"
    >
      <template #goto> 跳转至： </template>
    </n-pagination>
  </div>
</template>

<script>
import { onMounted, reactive, ref } from "vue";
import { useMessage } from "naive-ui";
import { Close, Brush, CaretForwardCircleOutline } from "@vicons/ionicons5";
import $api from "@/server/api";
import $utils from "@/utils/helper";

export default {
  emits: ["playVideo"],
  components: { Close, Brush, CaretForwardCircleOutline },
  setup(props, ctx) {
    const message = useMessage();
    const tags = $utils.buildLabVal(["success", "warning", "error"], ["公开", "私有", "删除"]);

    let videoList = ref();
    let page = ref(1);
    let pageSize = 10;
    let videoListInfo = reactive({
      totalNum: 0,
      publicNum: 0,
      privateNum: 0,
      deleteNum: 0,
    });

    function playVideo(id) {
      sessionStorage.setItem("videoId", id);
      ctx.emit("playVideo");
    }

    async function getVideoList() {
      await $api
        .getVideoList({
          page: page.value,
          pageSize,
        })
        .then((res) => {
          if (res.code == 200) {
            videoList.value = res.data;
          } else {
            message.error("获取视频列表失败！错误信息：" + res.msg);
          }
        });
    }

    async function getVideoListInfo() {
      await $api.getVideoListInfo().then((res) => {
        if (res.code == 200) {
          videoListInfo.totalNum = res.data.totalNum;
          videoListInfo.publicNum = res.data.publicNum;
          videoListInfo.privateNum = res.data.privateNum;
          videoListInfo.deleteNum = res.data.deleteNum;
        } else {
          message.error("获取视频详细信息失败！错误信息：" + res.msg);
        }
      });
    }

    onMounted(() => {
      getVideoListInfo();
    });

    return {
      tags,
      videoList,
      page,
      pageSize,
      videoListInfo,
      playVideo,
      getVideoList,
    };
  },
};
</script>

<style scoped lang="scss">
.videoCard {
  .dataCount > span {
    display: inline-block;
    margin: 20px 0;
    margin-right: 50px;
  }
  .videoList {
    .listBox {
      height: 95px;
      display: flex;
      justify-content: space-between;
      margin-top: 30px;
      .listBoxLeft {
        display: flex;
        justify-content: flex-start;
        .picture {
          width: 154px;
        }
        .content {
          display: flex;
          flex-direction: column;
          justify-content: space-between;
          margin-left: 20px;
          .title {
            font-size: 18px;
          }
          .data > span {
            display: inline-block;
            margin-right: 30px;
          }
        }
      }
      .operation > .button {
        margin-right: 10px;
      }
    }
  }
  .pagination {
    margin: 20px 0;
    margin-left: 800px;
  }
}
</style>
