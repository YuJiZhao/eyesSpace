<template>
  <div class="versionList">
    <div class="versionItem" v-for="item in versionListData" :key="item">
      <version-item :data="item" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import VersionItem from "./components/VersionItem.vue";

export default defineComponent({
  components: { VersionItem },
  props: ["versionListData"],
  setup(props) {
    return {
      versionListData: props.versionListData
    };
  },
});
</script>

<style lang="scss" scoped>
.versionList {
  width: 100%;
  min-width: 335px;
  margin: 0 auto;
}
</style>