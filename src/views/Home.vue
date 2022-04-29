<template>
    <div class="home">
        <div>load：</div>
        <button @click="openLoad">click</button>
        <button @click="closeLoad">close</button>
        <div>公告：</div>
        <button @click="openAnn">click</button>
        <button @click="closeAnn">close</button>
        <div>提示：</div>
        <button @click="openAlert">click</button>
        <button @click="closeAlert">close</button>
        <div>打赏：</div>
        <button @click="openReward">click</button>
        <button @click="closeReward">close</button>
    </div>
</template>

<script lang="ts">
import { defineComponent, reactive, inject } from "vue";
import { PopupType } from "@/d.ts/modules";
import { alertMsg, announcementMsg } from "@/config/index";

export default defineComponent({
    components: {},
    setup() {
        const $popup = inject<PopupType>('$popup')!;

        // load
        function openLoad() {
            $popup.loadShow();
        }
        function closeLoad() {
            $popup.loadHide();
        }

        // 公告
        function openAnn() {
            $popup.announcementShow(announcementMsg["defaultAnnouncement"]);
        }
        function closeAnn() {
            $popup.announcementHide();
        }

        // 提示
        function openAlert() {
            $popup.alertShow(alertMsg["copyAlert"]);
        }
        function closeAlert() {
            $popup.alertHide();
        }

        // 打赏
        function openReward() {
            $popup.rewardShow();
        }
        function closeReward() {
            $popup.rewardHide();
        }

        return {
            openLoad,
            closeLoad,
            openAnn,
            closeAnn,
            openAlert,
            closeAlert,
            openReward,
            closeReward
        };
    },
});
</script>

<style lang="scss" scoped>
.home {
}
</style>
