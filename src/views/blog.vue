<template>
    <div class="blog">
        <Markdown />
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import Markdown from "@/components/article/markdown.vue";

export default defineComponent({
    components: { Markdown },
    setup() {
        return {};
    },
});
</script>

<style lang="scss" scoped>
.blog {
}
</style>
