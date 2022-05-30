<template>
    <div class="markdown">
        <md-editor 
            v-model="state.text" 
            :previewOnly="state.previewOnly"
        />
    </div>
</template>

<script lang="ts">
import { defineComponent, ref, reactive } from "vue";
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
export default defineComponent({
    components: { MdEditor },
    setup() {
        const state = reactive({
            text: '# hello world',
            previewOnly: "true"
        });
        
        return {
            state
        };
    },
});
</script>

<style lang="scss" scoped>
</style>
