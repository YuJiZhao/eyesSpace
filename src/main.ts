import { createApp } from 'vue'
import App from './App.vue'

/**
 * 全局样式
 */
import 'normalize.css/normalize.css'
import 'lib-flexible/flexible.js'

/**
 * 插件
 */
import { $router, $service, $context, $user, $popup, $ext, $utils } from "./plugins";

const plugins = [$router, $service, $context, $user, $popup, $ext, $utils];

const app = createApp(App);

plugins.forEach(plugin => {
    app.use(plugin);
});

app.mount('#app')