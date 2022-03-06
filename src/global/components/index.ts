/**
 * 全局组件注册
 */
import { App } from "vue";
import { Load, Wait, Alert, Announcement, MessageBox, Notification } from "@/components/popup";

export default{
    install (app: App) {
        app.component('Load', Load);
        app.component('Wait', Wait);
        app.component('Alert', Alert);
        app.component('Announcement', Announcement);
        app.component('MessageBox', MessageBox);
        app.component('Notification', Notification);
    }
}