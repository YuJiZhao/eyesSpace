import { createApp } from 'vue'
import App from './App.vue'

// vue路由
import router from './router'

// animate动画
import 'animate.css/animate.min.css'

/**
 * markdown编辑器
 * @link https://code-farmer-i.github.io/vue-markdown-editor/zh/
 */
import VMdPreview from '@kangc/v-md-editor/lib/preview';  // 只引入预览模式
import '@kangc/v-md-editor/lib/style/preview.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';  // 选择vuepress主题
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
import Prism from 'prismjs';  // Prism语言包支持
import 'prismjs/components/prism-json';  // 代码高亮
import createCopyCodePlugin from '@kangc/v-md-editor/lib/plugins/copy-code/index';  // 快捷复制代码
import '@kangc/v-md-editor/lib/plugins/copy-code/copy-code.css';
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index';  // 显示代码行数

VMdPreview.use(vuepressTheme, {
    Prism,
});
VMdPreview.use(createCopyCodePlugin());
VMdPreview.use(createLineNumbertPlugin());

const app = createApp(App)
app.use(router)
app.use(VMdPreview);

app.mount('#app')
