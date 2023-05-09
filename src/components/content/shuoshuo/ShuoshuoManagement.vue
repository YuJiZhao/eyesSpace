<template>
  <div class="shuoshuoManagement">
    <div class="dataCount">
      <span>说说总数：{{ shuoshuoListInfo.totalNum }}</span>
      <span>公开说说：{{ shuoshuoListInfo.publicNum }}</span>
      <span>私有说说：{{ shuoshuoListInfo.privateNum }}</span>
      <span>删除说说：{{ shuoshuoListInfo.deleteNum }}</span>
      <span>总阅读量：{{ shuoshuoListInfo.viewsNum }}</span>
      <span>总评论量：{{ shuoshuoListInfo.commentsNum }}</span>
    </div>

    <div class="shuoshuoList">
      <div class="shuoshuoListItem" v-for="i in list" :key="i">
        <div
          class="avatar"
          style="
            background-image: url('https://img1.imgtp.com/2022/07/26/watQ3QJy.jpeg');
          "
        ></div>
        <div class="box">
          <div class="triangle"></div>
          <div class="content">{{i.content}}</div>
          <div class="pics">
            <div
              class="pic"
              v-for="p in i.picList"
              :key="p"
              :style="{ backgroundImage: 'url(' + p + ')' }"
            ></div>
          </div>
          <div class="foot">
            <div class="time">2022-07-29 00:00:16</div>
            <div class="comment">
              <n-icon size="25" style="transform: translateY(6px)"
                ><ChatboxEllipsesOutline
              /></n-icon>
              <span>0</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <n-pagination
      class="pagination"
      show-quick-jumper
      v-model:page="page"
      :default-page-size="pageSize"
      :item-count="shuoshuoListInfo.totalNum"
      :next="getShuoshuoList"
    >
      <template #goto> 跳转至： </template>
    </n-pagination>
  </div>
</template>

<script>
import { defineComponent, onMounted, reactive, ref } from "vue";
import { useMessage } from "naive-ui";
import { ChatboxEllipsesOutline } from "@vicons/ionicons5";
import $api from "@/server/api";

export default defineComponent({
  components: { ChatboxEllipsesOutline },
  setup() {
    const message = useMessage();
    let list = ref([]);
    let pics = ref([
      "https://img1.imgtp.com/2022/07/30/CFC0ydFF.jpg",
      "https://img1.imgtp.com/2022/07/30/CFC0ydFF.jpg",
      "https://img1.imgtp.com/2022/07/30/CFC0ydFF.jpg",
      "https://img1.imgtp.com/2022/07/30/CFC0ydFF.jpg",
      "https://img1.imgtp.com/2022/07/30/CFC0ydFF.jpg",
      "https://img1.imgtp.com/2022/07/30/CFC0ydFF.jpg",
    ]);
    let shuoshuoListInfo = reactive({
      totalNum: 0,
      publicNum: 0,
      privateNum: 0,
      deleteNum: 0,
      viewsNum: 0,
      likesNum: 0,
      commentsNum: 0,
    });
    let page = ref(1);
    let pageSize = 10;

    async function getShuoshuoList() {
      await $api.getShuoshuoList({
        page: page.value,
        pageSize
      }).then((res) => {
        if(res.code == 200) {
          list.value = res.data;
        } else {
          message.error("获取说说列表失败！错误信息：" + res.msg);
        }
      });
    }

    async function getShuoshuoListInfo() {
      await $api.getShuoshuoListInfo().then((res) => {
        if(res.code == 200) {
          shuoshuoListInfo.totalNum = res.data.totalNum;
          shuoshuoListInfo.publicNum = res.data.publicNum;
          shuoshuoListInfo.privateNum = res.data.privateNum;
          shuoshuoListInfo.deleteNum = res.data.deleteNum;
          shuoshuoListInfo.viewsNum = res.data.viewsNum;
          shuoshuoListInfo.commentsNum = res.data.commentsNum;
        } else {
          message.error("获取说说整体信息失败！错误信息：" + res.msg);
        }
      });
    }

    onMounted(() => {
      getShuoshuoListInfo();
    })

    return {
      list,
      pics,
      shuoshuoListInfo,
      page,
      pageSize,
      getShuoshuoList,
    };
  },
});
</script>

<style scoped lang="scss">
.shuoshuoManagement {
  .dataCount > span {
    display: inline-block;
    margin: 20px 0;
    margin-right: 50px;
  }
  .shuoshuoList {
    width: 900px;
    margin: 0 auto;
    .shuoshuoListItem {
      display: flex;
      min-height: 80px;
      margin-bottom: 50px;
      .avatar {
        width: 50px;
        height: 50px;
        background: red;
        background-size: 100% 100%;
        border-radius: 50%;
        border: 1px solid rgb(209, 207, 207);
        margin-right: 20px;
      }
      .box {
        width: 700px;
        border-radius: 5px;
        padding: 10px;
        background: rgb(73, 177, 245);
        position: relative;
        color: #fff;
        .triangle {
          position: absolute;
          width: 10px;
          height: 10px;
          background: rgb(73, 177, 245);
          top: 6px;
          left: -5px;
          transform: rotate(45deg);
        }
        .foot {
          display: flex;
          justify-content: space-between;
          height: 30px;
          margin-top: 20px;
          line-height: 30px;
          border-top: 1px dashed #fff;
          .comment {
            line-height: 30px;
            margin-right: 20px;
            span {
              display: inline-block;
              margin-left: 10px;
            }
          }
        }
        .pics {
          display: flex;
          flex-wrap: wrap;
          .pic {
            width: 150px;
            height: 150px;
            background-size: 100% 100%;
            margin-top: 10px;
            margin-right: 5px;
          }
        }
      }
    }
  }
  .pagination {
    margin: 20px 0;
    margin-left: 600px;
  }
}
</style>
