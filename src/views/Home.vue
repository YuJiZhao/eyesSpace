<template>
    <div class="home">
        <button @click="openAnn">click</button>
        <button @click="closeAnn">close</button>
    </div>
</template>

<script lang="ts">
import { defineComponent, reactive, inject } from "vue";
import { PopupType } from "@/d.ts/modules";
import { announcementMsg } from "@/config/index";

export default defineComponent({
    components: {},
    setup() {
        const $popup = inject<PopupType>('$popup')!;

        function openAnn() {
            $popup.announcementShow(announcementMsg["defaultAnnouncement"]);
        }

        function closeAnn() {
            $popup.announcementHide();
        }

        return {
            openAnn,
            closeAnn
        };
    },
});
</script>

<style lang="scss" scoped>
.home {
}
</style>
