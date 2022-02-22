<script setup lang="ts">
import { ref } from "@vue/reactivity";

// BUG：无法显示markdown内容
const markdown = ref("# hello world");

</script>

<template>
    <div class="home">
        <v-md-editor :text="markdown"></v-md-editor>
        <v-md-editor :model-value="markdown" mode="preview"></v-md-editor>
        <i>你好</i>
    </div>
</template>

<style lang="scss" scoped>
.home {
}
</style>
