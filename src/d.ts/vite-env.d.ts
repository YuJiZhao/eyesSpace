/// <reference types="vite/client" />

declare module "*.png";
declare module "*.json";

declare module "*.vue" {
  import type { DefineComponent } from "vue"
  const component: DefineComponent<{}, {}, any>
  export default component
}

declare module "crypto-js";
declare module "emot";
declare module "vue-image-crop-upload";
declare module 'xgplayer/dist/core_player';
declare module 'xgplayer/dist/controls/fullscreen';
declare module 'xgplayer/dist/controls/loading';
declare module 'xgplayer/dist/controls/play';
declare module 'xgplayer/dist/controls/poster';
declare module 'xgplayer/dist/controls/progress';
declare module 'xgplayer/dist/controls/time';
declare module 'xgplayer/dist/controls/volume';
declare module 'xgplayer/dist/controls/flex';