<template>
  <div class="card baseCard">
    <slot />
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.baseCard {
    width: 100%;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
    -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
}
</style>
