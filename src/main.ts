import { createApp } from "vue";
import App from "./App.vue";
import lazyPlugin from "vue3-lazy";
import { $router, $utils, $api, $process, $context, $user, $window } from "@/plugins/index";
import resource from "@/config/resource";

import './assets/css/index.css';
import 'reset-css/reset.css';
import "animate.css";
import '@/libs/statistics.js';

const app = createApp(App);

const plugins = [$router, $utils, $api, $process, $context, $user, $window];
plugins.forEach(plugin => {
    app.use(plugin);
});
app.use(lazyPlugin, {
    loading: resource.loading,
    error: resource.errorPage
});

app.mount('#app');