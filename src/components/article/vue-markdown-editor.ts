import App from './App.vue'

/**
 * markdown编辑器
 * @link https://code-farmer-i.github.io/vue-markdown-editor/zh/
 */
// TODO：等md-editor-v3稳定再做替换
// TODO：将md组件局部注册
// BUG：按理说应该只引入预览模块，但这样会无法显示内容，因此先将就
import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
// import VMdPreview from '@kangc/v-md-editor/lib/preview';  // 只引入预览模式
// import '@kangc/v-md-editor/lib/style/preview.css';

import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';  // 选择vuepress主题
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
import Prism from 'prismjs';  // Prism语言包支持
import 'prismjs/components/prism-json';  // 代码高亮
import createCopyCodePlugin from '@kangc/v-md-editor/lib/plugins/copy-code/index';  // 快捷复制代码
import '@kangc/v-md-editor/lib/plugins/copy-code/copy-code.css';
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index';  // 显示代码行数

// VMdPreview.use(vuepressTheme, {
//     Prism,
// });
// VMdPreview.use(createCopyCodePlugin());
// VMdPreview.use(createLineNumbertPlugin());

VueMarkdownEditor.use(vuepressTheme, {
    Prism,
});
VueMarkdownEditor.use(createCopyCodePlugin());
VueMarkdownEditor.use(createLineNumbertPlugin());

// app.use(VMdPreview);
// app.use(VueMarkdownEditor);