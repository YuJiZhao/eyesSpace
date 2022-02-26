<template>
    <div class="home">
        <Announcement :title="title" :value="value"/>
    </div>
</template>

<script lang="ts">
import { defineComponent, reactive } from "vue";
import { Announcement } from "@/components/popup";

export default defineComponent({
    components: {
        Announcement
    },
    setup() {
        const state = reactive({
            title: "公告",
            value: "你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好"
        });
        return {
            ...state
        };
    },
});
</script>

<style lang="scss" scoped>
.home {
}
</style>
