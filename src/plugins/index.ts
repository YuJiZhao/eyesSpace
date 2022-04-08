/**
 * 插件注册
 */

import { App } from "vue";
import $router from "@/router";
import service from "@/server/api";
import { User, Popup, CV, Ext } from "@/modules/store";
import utils from "@/utils/helper";

const $popup = {
    install: (app: App) => {
        app.provide('$popup', Popup);
    }
};

const $service = {
    install: (app: App) => {
        app.provide('$service', service);
    }
};

const $context = {
    install: (app: App) => {
        app.provide('$context', CV);
    }
};

const $user = {
    install: (app: App) => {
        app.provide('$user', User);
    }
};

const $ext = {
    install: (app: App) => {
        app.provide('$ext', Ext);
    }
};

const $utils = {
    install: (app: App) => {
        app.provide('$utils', utils);
    }
};

export { $router, $service, $popup, $user, $context, $ext, $utils };
