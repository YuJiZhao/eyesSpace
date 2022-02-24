/// <reference types="vite/client" />

declare module '*.png';
declare module '*.json';

declare module '*.vue' {
  import { DefineComponent } from 'vue'
  const component: DefineComponent<{}, {}, any>
  export default component
}

// 自定义插件
declare module '@/server/api';
declare module '@/modules/store';

// markdown编辑器
declare module '@kangc/v-md-editor';
// declare module '@kangc/v-md-editor/lib/preview';
declare module '@kangc/v-md-editor/lib/theme/vuepress.js';
declare module 'prismjs';
declare module '@kangc/v-md-editor/lib/plugins/copy-code/index';
declare module '@kangc/v-md-editor/lib/plugins/line-number/index';