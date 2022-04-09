<template>
    <div class="announcement" v-if="status">
            <div class="close">x</div>
            <div class="title">{{msg.title}}</div>
            <div class="value">{{msg.content}}</div>
    </div>
</template>

<script lang="ts">
/**
 * 公告组件
 * @desc 每个用户初次使用时打开一次，公告更新则刷新
 */
import { defineComponent, ref, reactive, watch, inject } from "vue";
import { PopupType } from "@/d.ts/modules";
export default defineComponent({
    components: {},
    setup(props, { emit }) {
        const $popup = inject<PopupType>('$popup')!;

        let status = ref($popup.announcementStatus.value);
        let msg = reactive({
            title: $popup.announcementMsg.title,
            content: $popup.announcementMsg.content
        });

        watch(() => $popup.announcementStatus.value, (value) => {
            status.value = value;
            msg.title = $popup.announcementMsg.title;
            msg.content = $popup.announcementMsg.content;
        });

        return {
            status,
            msg
        };
    }
});
</script>

<style lang="scss" scoped>
.announcement {
    .close {

    }

    .title {

    }

    .value {

    }
}
</style>
