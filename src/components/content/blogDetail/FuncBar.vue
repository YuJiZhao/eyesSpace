<template>
  <div class="funcBar">
    <div 
        class="funcBtn" 
        v-for="item in status" :key="item"
        :style="{backgroundImage: 'url(' + item.icon + ')'}"
        @click="funcObject[item.clickFunc]"
    >
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, ref, watch, inject } from "vue";
import { ProcessInterface, ApiObject } from "@/d.ts/plugin";
import { useRouter } from "vue-router";
import { blogDetailContext } from "@/components/content/blogDetail/businessTs/blogDetailContext";
import blogDetailProcess from "@/components/content/blogDetail/businessTs/blogDetailProcess";
import resource from "@/config/resource";
import { codeConfig } from "@/config/program";
import blogProcess from "../blog/businessTs/blogProcess";

export default defineComponent({
  components: {  },
  setup() {
    const $process = inject<ProcessInterface>("$process")!;
    const $api = inject<ApiObject>("$api")!;

    let id = useRouter().currentRoute.value.params.id;
    let isLike = ref(false);
    let isCollect = ref(false);

    let status = reactive({
        like: {
            icon: resource.like,
            clickFunc: "doBlogLike"
        },
        collect: {
            icon: resource.collect,
            clickFunc: "doBlogCollect"
        },
        comment: {
            icon: resource.comment,
            clickFunc: "doBlogComment"
        }
    });

    let funcObject = {
        doBlogLike: async () => {
            await $api.doBlogLike({id}).then(({code, msg}) => {
                if(code == codeConfig.success) {
                    $process.tipShow.success(isLike.value ? "取消点赞成功" : "点赞成功");
                    blogDetailProcess.changeLikesNum(isLike.value ? -1 : 1);
                    isLike.value = !isLike.value;
                    changeLikeIcon(isLike.value);
                } else {
                    $process.tipShow.error(msg);
                }
            })
        },
        doBlogCollect: async () => {
            await $api.doBlogCollect({id}).then(({code, msg}) => {
                if(code == codeConfig.success) {
                    $process.tipShow.success(isCollect.value ? "取消收藏成功" : "收藏成功");
                    blogDetailProcess.changecollectNum(isCollect.value ? -1 : 1);
                    isCollect.value = !isCollect.value;
                    changeCollectIcon(isCollect.value);
                } else {
                    $process.tipShow.error(msg);
                }
            })
        },
        doBlogComment: () => {
            $process.alertShow({
                title: "服务终止",
                content: "评论功能暂未上线，敬请期待"
            })
        }
    }

    function changeLikeIcon(flag: boolean) {
        status.like.icon = flag
                         ? resource.likeActive
                         : resource.like;
    }

    function changeCollectIcon(flag: boolean) {
        status.collect.icon = flag
                            ? resource.collectActive
                            : resource.collect;
    }

    let clear = watch(
      () => blogDetailProcess.cardInitLoad.value,
      (value) => {
        if(!value) {
            isLike.value = blogDetailContext.data.existLike!;
            isCollect.value = blogDetailContext.data.existCollect!;
            changeLikeIcon(isLike.value);
            changeCollectIcon(isCollect.value)
            clear();
        }
      })

    return {
        status,
        funcObject
    };
  },
});
</script>

<style lang="scss" scoped>
.funcBar {
    display: flex;
    justify-content: flex-end;
    margin: 10px 0;
    .funcBtn {
        width: 30px;
        height: 30px;
        background-size: 100% 100%;
        margin-left: 20px;
        cursor: pointer;
    }
}
</style>