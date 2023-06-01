<template>
  <div class="friendApply">
    <div class="title">友链申请</div>
    <div class="line">
      <input type="text" placeholder="网站名称" v-model="chainInfo.name">
      <input type="text" placeholder="网站链接" v-model="chainInfo.address">
    </div>
    <div class="line">
      <input type="text" placeholder="网站介绍" v-model="chainInfo.introduce">
      <input type="text" placeholder="头像链接" v-model="chainInfo.avatar">
    </div>
    <div class="btn" @click="applyFriendChain">提交申请</div>
  </div>
</template>

<script lang="ts">
import { defineComponent, inject, reactive } from "vue";
import { ProcessInterface, ApiObject, UserInterface } from "@/d.ts/plugin";
import { codeConfig } from "@/config/program";

export default defineComponent({
  setup() {
    const $api = inject<ApiObject>("$api")!;
    const $process = inject<ProcessInterface>("$process")!;
    const $user = inject<UserInterface>("$user")!;

    let chainInfo = reactive({
      name: "",
      introduce: "",
      avatar: "",
      address: ""
    });

    function applyFriendChain() {
      if (!$user.status) {
        $process.tipShow.error("请先登录！");
        return;
      }
      $api.applyFriendChain({...chainInfo}).then(({code, msg}) => {
        if (code == codeConfig.success) {
          $process.tipShow.error("提交成功，结果会以邮件通知！");
          chainInfo.name = "";
          chainInfo.introduce = "";
          chainInfo.avatar = "";
          chainInfo.address = "";
        } else {
          $process.tipShow.error("提交失败！错误信息：" + msg);
        }
      });
    }

    return {
      chainInfo,
      applyFriendChain
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.friendApply {
  width: calc(100% - 10px);
  margin: 20px auto;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
  color: $normal;
  .title {
    height: 35px;
    line-height: 35px;
    font-size: 24px;
    color: $title;
    margin-left: 3%;
    margin-bottom: 10px;
  }
  .line {
    width: 100%;
    display: flex;
    input {
      width: 44%;
      height: 24px;
      display: block;
      margin: 5px 3%;
      text-align: center;
      outline: none;
      color: $normal;
      border: none;
      border-radius: 3px;
      box-shadow: 0 0 2px rgba($color: $black, $alpha: 0.7);
      -webkit-box-shadow: 0 0 2px rgba($color: $black, $alpha: 0.7);
      -moz-box-shadow: 0 0 2px rgba($color: $black, $alpha: 0.7);
    }
  }
  .btn {
    width: 80px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    border-radius: 3px;
    box-shadow: 0 0 2px rgba($color: $black, $alpha: 0.7);
    -webkit-box-shadow: 0 0 2px rgba($color: $black, $alpha: 0.7);
    -moz-box-shadow: 0 0 2px rgba($color: $black, $alpha: 0.7);
    margin: 10px auto;
    cursor: pointer;
  }
}
</style>