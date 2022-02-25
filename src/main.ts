import { createApp } from 'vue'
import App from './App.vue'
import 'lib-flexible/flexible.js'
// import 'animate.css/animate.min.css'

import { $router, $service, $context, $user, $wait, $ext, $utils } from './plugins';
const plugins = [$router, $service, $context, $user, $wait, $ext, $utils];

const app = createApp(App)
plugins.forEach(plugin => {
    app.use(plugin);
});

app.mount('#app')