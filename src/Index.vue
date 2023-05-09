<template>
  <n-space vertical class="app">
    <n-layout has-sider>
      <n-config-provider
        :theme="themes.darkTheme"
        :theme-overrides="themes.appThemeOverrides"
      >
        <n-layout-sider
          class="appSider"
          bordered
          collapse-mode="width"
          :collapsed-width="64"
          :width="240"
          :collapsed="menuCollapsed"
          :native-scrollbar="false"
          show-trigger="bar"
          @collapse="menuCollapsed = true"
          @expand="menuCollapsed = false"
        >
          <n-layout-header bordered class="sysLogo">
            <img src="./static/img/logo.png" alt="" class="logoImg" />
            <span v-show="!menuCollapsed">耶瞳空间</span>
          </n-layout-header>
          <n-layout
            class="headTittle"
            position="absolute"
            :native-scrollbar="false"
          >
            <n-menu
              :accordion="true"
              :collapsed="menuCollapsed"
              :collapsed-width="64"
              :collapsed-icon-size="22"
              :options="menuOptions"
              :render-label="renderMenuLabel"
              :expanded-keys="expandedKeys"
              :expand-icon="expandIcon"
              :value="menuValue"
              :on-update:value="handleMenuChange"
              :on-update:expanded-keys="handleMenuExpandedKeys"
            />
          </n-layout>
        </n-layout-sider>
      </n-config-provider>

      <!-- 右边内容区 -->
      <n-layout class="content">
        <!-- 内容头部 -->
        <div class="contentHeader">
          <div class="nav">
            <n-space justify="space-between">
              <div class="left">
                <n-button
                  text
                  style="font-size: 20px"
                  @click="menuCollapsed = !menuCollapsed"
                >
                  <n-icon>
                    <TrailSignSharp />
                  </n-icon>
                </n-button>
                <n-breadcrumb>
                  <n-breadcrumb-item>
                    <n-dropdown
                      trigger="hover"
                      :options="breadcrumbOptions"
                      @select="handleMenuChange"
                    >
                      管理系统
                    </n-dropdown>
                  </n-breadcrumb-item>
                  <n-breadcrumb-item>
                    {{ tabName }}
                  </n-breadcrumb-item>
                </n-breadcrumb>
              </div>
              <div class="right">
                <!-- <n-button text style="font-size: 25px">
                  <n-icon><Mail /></n-icon>
                </n-button> -->
                <n-dropdown>
                  <n-avatar
                    round
                    size="medium"
                    :src="avatarUrl"
                  />
                </n-dropdown>
              </div>
            </n-space>
          </div>
          <!-- 侧边栏 -->
          <div class="tabs">
            <n-config-provider :theme-overrides="themes.tabThemeOverrides">
              <n-tabs
                v-model:value="tabName"
                type="card"
                tab-style="min-width: 80px;"
                @close="handleTagClose"
                @update:value="handleTagChange"
              >
                <n-tab-pane
                  v-for="panel in panels"
                  :key="panel"
                  :name="panel"
                  :closable="true"
                >
                  <!-- <div style="display: none"></div> -->
                </n-tab-pane>
              </n-tabs>
            </n-config-provider>
          </div>
        </div>
        <!-- 内容区 -->
        <div class="contentApp">
          <router-view v-slot="{ Component }" class="appView">
            <component :is="Component" />
          </router-view>
        </div>
      </n-layout>
    </n-layout>
  </n-space>
</template>

<script>
import { defineComponent, h, reactive, ref } from "vue";
import { useRouter } from "vue-router";
import {
  ChevronDownSharp,
  LinkSharp,
  RefreshSharp,
  SwapHorizontalSharp,
  TrailSignOutline as TrailSignSharp,
  SearchSharp,
  Contract,
  ResizeSharp,
  Mail
} from "@vicons/ionicons5";
import { NIcon, darkTheme, useMessage, useLoadingBar } from "naive-ui";
import appThemeOverrides from "./themes/sideBar.json";
import tabThemeOverrides from "./themes/tab.json";
import { useMenuOptions } from "@/hooks/useMenu";
import { urlConfig } from "@/config";

export default defineComponent({
  components: {
    RefreshSharp,
    SwapHorizontalSharp,
    TrailSignSharp,
    SearchSharp,
    Contract,
    ResizeSharp,
    Mail
  },
  setup() {
    const router = useRouter();
    const message = useMessage();
    const loadingBar = useLoadingBar();

    let menuValue = ref("");
    let expandedKeys = ref([]);
    let menuCollapsed = ref(false);
    let breadcrumbOptions = reactive([]);
    let tagsKeyRef = ref("主控中心");
    let panelsReactive = reactive(["主控中心"]);

    // 菜单数据
    let menuOptions = useMenuOptions();

    // 处理菜单点击事件
    function handleMenuChange(key) {
      routerJump(key);
    }

    // 处理菜单折叠面板变化事件
    function handleMenuExpandedKeys(keys) {
      expandedKeys.value = keys;
    }

    // 处理标签关闭事件
    function handleTagClose(name) {
      if (panelsReactive.length === 1) {
        message.error("最后一个了");
        return;
      }
      const index = panelsReactive.findIndex((v) => name === v);
      panelsReactive.splice(index, 1);
      if (tagsKeyRef.value === name) {
        tagsKeyRef.value = panelsReactive[index - 1];
      }
      routerJump(panelsReactive[index - 1]);
    }

    // 处理标签点击事件
    function handleTagChange(value) {
      routerJump(value);
    }

    //跳转到指定目标的路由（中文名和key均可）
    function routerJump(destination) {
      loadingBar.start();
      function gotoPath(menuItem) {
        if (!panelsReactive.includes(menuItem.label)) {
          panelsReactive.push(menuItem.label);
        }
        tagsKeyRef.value = menuItem.label;
        menuValue.value = menuItem.key;
        router.push({ path: "/" + menuItem.key });
        setTimeout(() => {
          loadingBar.finish();
        });
      }
      for (let item of menuOptions) {
        if (item.label == destination || item.key == destination) {
          gotoPath(item);
          return;
        }
        if (item.children) {
          for (let child of item.children) {
            if (child.label == destination || child.key == destination) {
              gotoPath(child);
              expandedKeys.value = [item.key];
              return;
            }
          }
        }
      }
      loadingBar.error();
    }

    function renderMenuLabel(option) {
      if ("href" in option) {
        return h("a", { href: option.href, target: "_blank" }, option.label);
      }
      return option.label;
    }

    function renderMenuIcon(option) {
      if (option.key === "sheep-man") return true;
      if (option.key === "food") return null;
      if (option.icon != undefined) {
      } else {
        return h(NIcon, null, { default: () => h(LinkSharp) });
      }
    }

    function expandIcon() {
      return h(NIcon, null, { default: () => h(ChevronDownSharp) });
    }

    return {
      themes: {
        darkTheme,
        appThemeOverrides,
        tabThemeOverrides,
      },
      menuCollapsed,
      menuOptions,
      menuValue,
      expandedKeys,
      panels: panelsReactive,
      tabName: tagsKeyRef,
      breadcrumbOptions,
      avatarUrl: urlConfig.avatarUrl,
      handleMenuChange,
      handleMenuExpandedKeys,
      handleTagClose,
      handleTagChange,
      renderMenuLabel,
      renderMenuIcon,
      expandIcon,
    };
  },
});
</script>

<style lang="scss" scoped>
.app {
  .appSider {
    height: 100vh;
    .sysLogo {
      margin: 10px 0;
      display: flex;
      justify-content: center;
      align-items: center;
      .logoImg {
        height: 40px;
        margin: 5px;
      }
      span {
        margin: 5px;
        line-height: 40px;
        font-size: 17px;
        font-weight: bold;
        white-space: nowrap;
        overflow: hidden;
      }
    }
    .headTittle {
      margin-top: 60px;
    }
  }
  .content {
    display: flex;
    flex-direction: column;
    .contentHeader {
      .nav {
        height: 64px;
        overflow: hidden;
        box-shadow: 0 1px 4px rgb(0 21 41 / 8%);
        background-color: #fff;
        div.left {
          display: flex;
          align-items: center;
          height: 64px;
          padding: 0 18px;
          .n-button {
            margin-right: 18px;
          }
        }
        div.right {
          display: flex;
          align-items: center;
          height: 64px;
          padding: 0 18px;
          .n-button {
            margin-right: 18px;
          }
        }
      }
      .tabs {
        padding: 4px 4px 0 4px;
        background-color: #ffffff00;
        user-select: none;
        .n-tab-pane {
          display: none;
        }
      }
    }
    .contentApp {
      overflow-y: scroll;
      height: calc(100vh - 107px);
      box-sizing: border-box;
      padding: 30px;
      padding-bottom: 0;
      .appView {
        max-width: 1300px;
        min-width: 1100px;
        height: 100%;
        margin: 0 auto;
      }
    }
  }
}
</style>