<template>
  <div class="login">
    <div class="card">
      <div class="body">
        <div class="header">登录</div>
        <input class="email" type="text" name="userEmail" placeholder="email" v-model="email" />
        <div class="imgVeriCode">
          <input class="code" type="text" name="userCode" placeholder="image code" v-model="imgCode" />
          <Wait class="img" :show="imgLoad" :fail="isImgFail" size="25px" failSize="25px">
            <img :src="base64Img" @click="getImgCode" />
          </Wait>
        </div>
        <div class="emailVeriCode">
          <input class="code" type="text" name="userCode" placeholder="email code" v-model="emailCode" />
          <Wait class="emailBtn" :show="emailLoad" size="25px">
            <div @click="sendEmail">{{ sendBtn }}</div>
          </Wait>
        </div>
        <div class="loginBtn" @click="doLogin">登录</div>
      </div>
      <div class="footer">若检测为未注册用户，则自动注册</div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, inject, onActivated } from "vue";
import { useRouter } from "vue-router";
import { HelpInterface, ProcessInterface, UserInterface } from "@/d.ts/plugin";
import $api from "@/server/api";
import useProcessControl from "@/hooks/useProcessControl";
import { urlConfig, codeConfig } from "@/config/program";
import { Wait } from "@/components/general/popup";
import { writerMeta } from "@/router/help";
import { metaInfo } from "@/config/site";

export default defineComponent({
  name: "Login",
  components: { Wait },
  beforeRouteEnter: () => {
    writerMeta(metaInfo.login);
  },
  setup() {
    const $router = useRouter();
    const $utils = inject<HelpInterface>("$utils")!;
    const $user = inject<UserInterface>("$user")!;
    const $process = inject<ProcessInterface>("$process")!;

    let email = ref("");
    let emailCode = ref("");
    let imgCode = ref("");
    let key = "";
    let sendBtn = ref("发送邮箱验证码");
    let lock = 0; // 邮件发送锁
    let base64Img = ref("");
    let imgLoad = ref(true);
    let isImgFail = ref(false);
    let emailLoad = ref(false);
    let isSendEmail = 0;

    // 发送邮件后计数
    function countDown() {
      let time = 60;
      sendBtn.value = time + "s后可重新发送";
      let clear = setInterval(() => {
        time--;
        sendBtn.value = time + "s后可重新发送";
        if (time == 0) {
          clearInterval(clear);
          sendBtn.value = "发送邮箱验证码";
          lock = 0;
        }
      }, 1000);
    }

    // 获取图形验证码
    async function getImgCode() {
      imgLoad.value = true;
      await $api.getImgCode().then(({code, msg, data}) => {
        if(code == codeConfig.success) {
          key = data.key;
          base64Img.value = data.base64Img;
          imgLoad.value = false;
        } else {
          $process.tipShow.error("图形验证码加载出错");
          isImgFail.value = true;
        }
      });
    }

    // 发送邮件
    async function sendEmail() {
      if (lock != 0) return;
      // 邮箱是否为空
      if(!email.value.trim()) {
        $process.tipShow.warn("请输入邮箱！");
        return;
      }
      // 检查邮箱格式
      if (!$utils.checkEmail(email.value)) {
        $process.tipShow.warn("邮箱格式错误！");
        return;
      }
      // 检查是否填写图形验证码
      if (!imgCode.value.trim()) {
        $process.tipShow.warn("请填写图形验证码！");
        return;
      }
      // 检查是否存在key值
      if (key == "") {
        $process.tipShow.warn("请等待图形验证码加载！");
        return;
      }

      // 发邮件
      emailLoad.value = true;
      await $api
        .getEmailCode({
          email: getEncryptedEmail(),
          imgCode: imgCode.value.trim(),
          key,
        })
        .then(({code, msg}) => {
          if (code == codeConfig.success) {
            $process.tipShow.success("邮件发送成功！");
            lock = 1;
            isSendEmail = 1;
            countDown();
          } else {
            $process.tipShow.error(msg);
          }
          emailLoad.value = false;
        });
    }

    // 执行登录
    function doLogin() {
      if (isSendEmail == 0) {
        $process.tipShow.warn("请先验证邮箱！");
        return;
      }
      if (emailCode.value.trim() == "") {
        $process.tipShow.warn("请输入邮箱验证码！");
        return;
      }
      $utils.debounce(async () => {
        await $api
          .login({
            email: getEncryptedEmail(),
            emailCode: emailCode.value.trim(),
          })
          .then(({code, msg}) => {
            if (code == codeConfig.success) {
              $process.tipShow.success("登录成功！");
              initUser();
            } else {
              $process.tipShow.error(msg);
            }
          });
      }, 1000);
    }

    async function initUser() {
      $process.loadShow();
      $api.getUserInfo().then(({code, msg, data}) => {
        if(code == codeConfig.success) {
          $user.init(data);
          window.location.replace(urlConfig.siteUrl);
        } else {
          $process.loadHide();
          $process.alertShow({
            title: "获取用户信息失败",
            content: "失败信息：" + msg
          });
        }
      });;
    }

    function getEncryptedEmail() {
      let encryptedEmail = email.value.trim();
      let index = encryptedEmail.indexOf("@");
      return $utils.encryption(encryptedEmail.slice(0, index)) + encryptedEmail.slice(index);
    }

    onMounted(() => {
      getImgCode();
    });

    onActivated(() => {
      useProcessControl();
    });

    return {
      email,
      emailCode,
      imgCode,
      base64Img,
      imgLoad,
      emailLoad,
      isImgFail,
      sendBtn,
      getImgCode,
      sendEmail,
      doLogin,
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.login {
  .card {
    width: 350px;
    height: 340px;
    padding: 20px;
    margin: 0 auto;
    margin-top: 80px;
    margin-bottom: 5px;
    border-radius: 10px;
    box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    position: relative;
    .body {
      width: 100%;
      margin: 0 auto;
      .header {
        margin: 0 auto;
        margin-bottom: 20px;
        font-size: 30px;
        color: $title;
        text-align: center;
      }
      input {
        width: 100%;
        height: 30px;
        border: none;
        box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
        -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
        -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
        border-radius: 15px;
        color: $normal;
        background: none;
        text-align: center;
        outline: none;
        margin-bottom: 20px;
      }
      .imgVeriCode {
        display: flex;
        justify-content: space-between;
        .code {
          width: 100px;
        }
        .img {
          width: 150px;
          height: 34px;
          margin-right: -6px;
          border: none;
          box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
          -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
          -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
          border-radius: 5px;
          img {
            cursor: pointer;
          }
        }
      }
      .emailVeriCode {
        display: flex;
        justify-content: space-between;
        .code {
          width: 100px;
        }
        .emailBtn {
          width: 150px;
          height: 34px;
          border: none;
          box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
          -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
          -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
          border-radius: 5px;
          background: none;
          margin-right: -6px;
          div {
            color: $normal;
            line-height: 34px;
            text-align: center;
            cursor: pointer;
          }
        }
      }
      .loginBtn {
        height: 30px;
        width: 100px;
        margin: 0px auto;
        line-height: 30px;
        text-align: center;
        color: $normal;
        background: none;
        border: none;
        box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
        -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
        -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
        border-radius: 15px;
        cursor: pointer;
      }
    }
    .footer {
      position: absolute;
      bottom: 20px;
      color: $normal;
      width: calc(100% - 40px);
      text-align: center;
    }
  }
}

@media screen and (max-width: 800px) {
  .card {
    width: 330px !important;
  }
}
</style>