<template>
  <base-dialog>
    <div class="idCard">
      <img class="avatar" :src="userInfo.avatar" @click="changeAvatar">
      <div class="info">
        <div class="item id">
          <div class="title">用户id:</div>
          <div class="division"></div>
          <div class="content">{{userInfo.id}}</div>
        </div>
        <div class="item name"> 
          <div class="title">用户昵称:</div>
          <div class="division"></div>
          <div class="content">
            <input ref="nameRef" type="text" v-model="userInfo.name" @focus="nameFocus" @blur="nameBlur" />
          </div>
        </div>
        <div class="item email"> 
          <div class="title">注册邮箱:</div>
          <div class="division"></div>
          <div class="content">{{userInfo.email}}</div>
        </div>
        <div class="item createTime"> 
          <div class="title">注册日期:</div>
          <div class="division"></div>
          <div class="content">{{userInfo.createTime}}</div>
        </div>
      </div>
      <div class="btnGroup">
        <transition name="saveAnimate">
          <div class="save" v-if="isShowSave" @click="saveChange">保存更改</div>
        </transition>
        <div class="exit" @click="exit">退出登录</div>
      </div>
    </div>
  </base-dialog>
  <avatar-upload :show="isShowUpload" @close="isShowUpload = false" @upload-success="changeAvatarSuccess"/>
</template>

<script lang="ts">
import { defineComponent, inject, onMounted, reactive, ref } from "vue";
import { UserInterface, ProcessInterface, HelpInterface, ApiObject } from "@/d.ts/plugin";
import BaseDialog from "@/components/general/popup/dialogComponent/BaseDialog.vue";
import resource from "@/config/resource";
import { codeConfig, siteConfig } from "@/config/program";
import AvatarUpload from "@/components/general/popup/dialogComponent/AvatarUpload.vue";

export default defineComponent({
  components: { BaseDialog, AvatarUpload },
  setup() {
    const $user = inject<UserInterface>("$user")!;
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;
    const $utils = inject<HelpInterface>("$utils")!;

    let isShowSave = ref(false);
    let nameRef = ref();
    let userInfo = reactive({
      id: 0,
      name: "",
      email: "",
      createTime: "",
      avatar: resource.defaultAvatar
    });
    let isShowUpload = ref(false);

    function nameFocus() {
      nameRef.value.style.color = "rgb(127, 200, 248)";
      isShowSave.value = true;
    }
    
    function nameBlur() {
      nameRef.value.style.color = "#666";
    }

    async function saveChange() {
      await $api.updateUserInfo({
        name: userInfo.name
      }).then(({code, msg}) => {
        if(code == codeConfig.success) {
          $process.tipShow.success("修改信息成功");
          location.reload();
        } else {
          $process.tipShow.error(msg);
        }
      })
    }

    function changeAvatar() {
      isShowUpload.value = true;
    }

    function changeAvatarSuccess(url: string) {
      location.reload();
    }

    function exit() {
      $utils.delCookie(siteConfig.tokenHeader);
      location.reload();
    }

    onMounted(() => {
      userInfo.id = $user.data.id!;
      userInfo.name = $user.data.username!;
      userInfo.email = $user.data.email!;
      userInfo.createTime = $user.data.createTime!;
      userInfo.avatar = $user.data.avatar!;
    })

    return {
      nameRef,
      isShowSave,
      isShowUpload,
      userInfo,
      nameBlur,
      nameFocus,
      saveChange,
      exit,
      changeAvatar,
      changeAvatarSuccess
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.saveAnimate-enter-active {
  animation: fadeIn 0.5s;
}
.saveAnimate-leave-active {
  animation: fadeOut 0.5s;
}

.idCard {
  color: $normal;
  .avatar {
    width: 80px;
    height: 80px;
    display: block;
    border-radius: 50%;
    box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    margin: 0 auto;
    margin-bottom: 20px;
    cursor: pointer;
  }
  .info {
    .item {
      height: 30px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      .title {
        flex: 5;
        text-align: right;
      }
      .division {
        flex: 1;
      }
      .content {
        flex: 8;
      }
    }
    .name {
      .content > input {
        height: 30px;
        font-size: 16px;
        color: $normal;
        font-family: 'eyes';
        border: none;
        background: none;
        padding: 0;
        outline: none;
        transition: 0.3s;
        cursor: pointer;
      }
    }
  }
  .btnGroup {
    display: flex;
    justify-content: right;
    margin-top: 10px;
    .save, .exit {
      width: 80px;
      height: 30px;
      border-radius: 5px;
      line-height: 30px;
      text-align: center;
      box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
      -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
      -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
      cursor: pointer;
    }
    .exit {
      margin-left: 10px;
    }
  }
}
</style>