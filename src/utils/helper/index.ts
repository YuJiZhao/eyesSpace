const utils = {
  getCookie: (name: string) => {
    let arr,
        reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if ((arr = document.cookie.match(reg))) return unescape(arr[2]);
    else return "null";
  }
};
export default utils;
