import { createApp } from 'vue';
import App from './App.vue';

/**
 * 全局样式
 */
import 'reset-css/reset.css';
import '@/lib/back_ribbon.js';
import '@/lib/title_joke.js';
import '@/lib/click_firework.js';

/**
 * 插件
 */
import { $router, $service, $context, $user, $popup, $ext, $utils } from "./plugins";

const plugins = [$router, $service, $context, $user, $popup, $ext, $utils];

const app = createApp(App);

plugins.forEach(plugin => {
    app.use(plugin);
});

app.mount('#app');