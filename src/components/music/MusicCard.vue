<template>
  <div class="musicCard">
    <div class="dataCount">
      <span>音乐总数：{{ musicListInfo.totalNum }}</span>
      <span>公开音乐：{{ musicListInfo.publicNum }}</span>
      <span>私有音乐：{{ musicListInfo.privateNum }}</span>
      <span>删除音乐：{{ musicListInfo.deleteNum }}</span>
      <span>总播放量：{{ musicListInfo.viewsNum }}</span>
      <span>总点赞量：{{ musicListInfo.likesNum }}</span>
      <span>总评论量：{{ musicListInfo.commentsNum }}</span>
    </div>
    <div class="musicList" v-for="item in musicList" :key="item">
      <div class="listBox">
        <div class="listBoxLeft">
          <div
            class="picture"
            :style="{
              background: 'url(' + item.pic + ')',
              backgroundSize: '100% 100%',
            }"
          ></div>
          <div class="content">
            <div class="title">{{ item.title }}</div>
            <div class="itemData">
              <div><n-tag :bordered="false" :type="tags[item.status].label">{{tags[item.status].value}}</n-tag></div>
              <div>
                <div>歌手: {{ item.author }}</div>
                <div>发布时间: {{ item.createTime }}</div>
              </div>
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
          <n-button class="button" type="info" @click="playMusic(item.id)">
            <n-icon size="18" style="margin-left: -5px"><CaretForwardCircleOutline/></n-icon>
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
      :item-count="musicListInfo.totalNum"
      :next="getMusicList"
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
  emits: ["playMusic"],
  components: { Close, Brush, CaretForwardCircleOutline },
  setup(props, ctx) {
    const message = useMessage();
    const tags = $utils.buildLabVal(["success", "warning", "error"], ["公开", "私有", "删除"]);

    let musicList = ref();
    let page = ref(1);
    let pageSize = 10;
    let musicListInfo = reactive({
      totalNum: 0,
      publicNum: 0,
      privateNum: 0,
      deleteNum: 0,
      viewsNum: 0,
      likesNum: 0,
      commentsNum: 0,
    });

    function playMusic(id) {
      sessionStorage.setItem("musicId", id);
      ctx.emit("playMusic");
    }

    async function getMusicList() {
      $api
        .getMusicList({
          page: page.value,
          pageSize,
        })
        .then((res) => {
          if (res.code == 200) {
            musicList.value = res.data;
          } else {
            message.error("获取视频列表失败！错误信息：" + res.msg);
          }
        });
    }

    async function getMusicListInfo() {
      $api.getMusicListInfo().then((res) => {
        if (res.code == 200) {
          musicListInfo.totalNum = res.data.totalNum;
          musicListInfo.publicNum = res.data.publicNum;
          musicListInfo.privateNum = res.data.privateNum;
          musicListInfo.deleteNum = res.data.deleteNum;
        } else {
          message.error("获取视频详细信息失败！错误信息：" + res.msg);
        }
      });
    }

    onMounted(() => {
      getMusicListInfo();
    });

    return {
      tags,
      musicList,
      page,
      pageSize,
      musicListInfo,
      playMusic,
      getMusicList,
    };
  },
};
</script>

<style scoped lang="scss">
.musicCard {
  .dataCount > span {
    display: inline-block;
    margin: 20px 0;
    margin-right: 50px;
  }
  .musicList {
    .listBox {
      height: 95px;
      display: flex;
      justify-content: space-between;
      margin-top: 30px;
      .listBoxLeft {
        display: flex;
        justify-content: flex-start;
        .picture {
          width: 140px;
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
          .itemData {
            display: flex;
            font-size: 13px;
            div {
              margin-right: 5px;
            }
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
