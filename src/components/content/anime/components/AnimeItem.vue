<template>
  <div class="animeItem">
    <div class="cover"></div>
    <div class="content">
        <div class="title">{{data.title}}</div>
        <div class="info">
            <span>时期: {{data.period}}</span>
            <span>类型: {{data.type}}</span>
        </div>
        <div class="introduce">{{data.introduce}}</div>
        <div class="footer">
            <div class="time">{{data.createTime}}</div>
            <div class="data">
                <div>点击量: {{data.view}}</div>
                <div>评论数: {{data.comment}}</div>
            </div>
        </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  components: { },
  props: ["data", "index"],
  setup(props) {
    return {
        data: props.data,
        direction: props.index % 2 ? "row" : "row-reverse",
        cover: "url(" + props.data.cover + ")"
    };
  },
});
</script>

<style lang="scss" scoped>
@import "@/assets/scss/index.scss";

.animeItem {
  width: calc(100% - 5px);
  min-height: 200px;
  margin: 0 auto;
  margin-top: 2px;
  margin-bottom: 20px;
  border-radius: 5px;
  box-shadow: 0 0 4px rgba($color: $black, $alpha: 0.7);
  -webkit-box-shadow: 0 0 4px rgba($color: $black, $alpha: 0.7);
  -moz-box-shadow: 0 0 4px rgba($color: $black, $alpha: 0.7);
  color: $normal;
  display: flex;
  flex-direction: v-bind(direction);
  &:hover {
    .cover {
        background-size: auto 110%;
    }
  }
  .cover {
    width: 40%;
    background-image: v-bind(cover);
    background-size: auto 100%;
    background-position: center center;
    background-repeat: no-repeat;
    transition: 0.3s;
    border-radius: 5px;
  }
  .content {
    width: 60%;
    padding: 10px;
    .title {
        min-height: 40px;
        font-size: 24px;
        text-align: center;
        line-height: 30px;
    }
    .info span {
        height: 20px;
        line-height: 20px;
        margin-right: 10px;
    }
    .introduce {
        margin: 10px 0;
        text-indent: 28px;
        line-height: 18px;
    }
    .footer {
        height: 20px;
        line-height: 20px;
        display: flex;
        justify-content: space-between;
        .data {
            display: flex;
            div {
                margin-left: 10px;
            }
        }
    }
  }
}

@media screen and (max-width: 1040px) {
  .animeItem {
    flex-direction: column;
    &:hover {
      .cover {
        background-size: 110% auto;
      }
    }    
    .cover {
      height: 150px;
      width: 100%;
      background-size: 100% auto;
      background-position: center top;
    }
    .content {
      width: 100%;
    }
  }
}
</style>