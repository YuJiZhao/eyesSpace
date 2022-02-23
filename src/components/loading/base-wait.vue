<template>
    <div class="wait" v-if="wait">
    </div>
</template>

<script lang="ts">
import { defineComponent, inject } from 'vue';
export default defineComponent({
    setup() {
        const wait = inject<Wait>('$wait')!;
        return {
            wait: false
        };
    }
});
</script>

<style lang="scss" scoped>
.wait {
}
</style>
