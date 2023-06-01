<template>
  <div class="friendChainList">
    <div class="chainItem" v-for="item in dataList" :key="item.id" @click="jump(item.address)">
        <div class="cover" :style="{backgroundImage: 'url(' + item.avatar + ')'}"></div>
        <div class="box">
            <div class="name">{{item.name}}</div>
            <div class="introduce">{{item.introduce}}</div>
            <div class="status">{{statusConvert[item.status]}}</div>
        </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { statusConvert } from "./config";

export default defineComponent({
  props: ["data"],
  setup(props) {
    
    function jump(address: string) {
        window.open(address);
    }

    return {
        dataList: props.data,
        statusConvert,
        jump
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.friendChainList {
    display: flex;
    flex-wrap: wrap;
    color: $normal;
    .chainItem {
        width: 42%;
        height: 120px;
        margin: 20px 4%;
        display: flex;
        border-radius: 5px;
        box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
        -webkit-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
        -moz-box-shadow: 0 0 5px rgba($color: $black, $alpha: 0.7);
        cursor: pointer;
        .cover {
            border-radius: 5px 50% 50% 5px;
            background-size: 100% 100%;
            flex: 0 0 120px;
        }
        .box {
            padding: 5px;
            flex: 1;
            .name {
                height: 30px;
                line-height: 30px;
                font-size: 20px;
                overflow: hidden;
                color: $title;
            }
            .introduce {
                height: 60px;
                overflow: hidden;
            }
            .status {
                height: 20px;
                line-height: 20px;
                text-align: right;
            }
        }
    }
}

@media screen and (max-width: 1040px) {
    .chainItem {
        width: calc(100% - 4px) !important;
        margin: 10px 2px !important;
    }
}
</style>