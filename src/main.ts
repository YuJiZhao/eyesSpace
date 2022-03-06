import { createApp } from 'vue'
import App from './App.vue'

/**
 * 全局样式
 */
import 'normalize.css/normalize.css'
import 'lib-flexible/flexible.js'
import "@/assets/scss/theme/default/global.scss";

/**
 * 全局组件
 */
import components from '@/global/components';

/**
 * 插件
 */
import { $router, $service, $context, $user, $popup, $ext, $utils } from "./global/plugins";

const plugins = [$router, $service, $context, $user, $popup, $ext, $utils];

const app = createApp(App);

app.use(components);

plugins.forEach(plugin => {
    app.use(plugin);
});

app.mount('#app')