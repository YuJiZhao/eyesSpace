import service from '@/server/api';
import $router from '../router';
import { User, Wait, CV, Ext } from '@/modules/store';
import { App } from 'vue';
import utils from '@/utils/helper';

const $wait = {
    install: (app: App) => {
        app.provide('$wait', Wait);
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

export { $router, $service, $wait, $user, $context, $ext, $utils };
