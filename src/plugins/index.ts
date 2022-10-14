// 插件注册
import { App } from "vue";
import $router from "@/router";
import api from "@/server/api";
import process from "@/modules/process";
import { context } from "@/modules/context";
import { user } from "@/modules/user";
import window from "@/modules/window";
import utils from "@/utils/helper";

const $api = {
    install: (app: App) => {
        app.provide('$api', api);
    }
};

const $utils = {
    install: (app: App) => {
        app.provide('$utils', utils);
    }
};

const $process = {
    install: (app: App) => {
        app.provide('$process', process);
    }
};

const $context = {
    install: (app: App) => {
        app.provide('$context', context);
    }
};

const $user = {
    install: (app: App) => {
        app.provide('$user', user);
    }
};

const $window = {
    install: (app: App) => {
        app.provide('$window', window);
    }
};

export { $router, $utils, $api, $process, $context, $user, $window };