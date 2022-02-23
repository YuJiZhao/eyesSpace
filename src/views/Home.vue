<template>
    <div class="home">
        <!-- <v-md-editor :text="markdown"></v-md-editor> -->
        <v-md-editor :model-value="markdown" mode="preview"></v-md-editor>
    </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
export default defineComponent({
    components: {},
    setup() {
        const markdown = "# hello world";
        return {
            markdown
        };
    },
});
</script>

<style lang="scss" scoped>
.home {
}
</style>
