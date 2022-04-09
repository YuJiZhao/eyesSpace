<template>
    <div class="alert" v-if="status">
        <div class="title">{{msg.title}}</div>
        <div class="value">{{msg.content}}</div>
    </div>
</template>

<script lang="ts">
/**
 * 提示组件
 */
import { defineComponent, ref, reactive, watch, inject } from "vue";
import { PopupType } from "@/d.ts/modules";
export default defineComponent({
    components: {},
    setup(props, { emit }) {
        const $popup = inject<PopupType>('$popup')!;

        let status = ref($popup.alertStatus.value);
        let msg = reactive({
            title: $popup.alertMsg.title,
            content: $popup.alertMsg.content
        });

        watch(() => $popup.alertStatus.value, (value) => {
            status.value = value;
            msg.title = $popup.alertMsg.title;
            msg.content = $popup.alertMsg.content;
        });

        return {
            status,
            msg
        };
    }
});
</script>

<style lang="scss" scoped>
.alert {
}
</style>
