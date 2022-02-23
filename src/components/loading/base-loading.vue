<template>
    <div id="loading" v-if="wait">
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
export default defineComponent({
    name: 'loadding',
    props: ['wait'],
    setup() { }
});
</script>

<style scoped>
#loading {
}
</style>
