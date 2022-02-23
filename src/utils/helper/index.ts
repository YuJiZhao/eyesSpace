//@ts-nocheck
import { strEnc } from './des';
import commonDef from './commonDef';
const utils = {
  isCorrectCode: (smsCode: string) => {
    return /^[0-9]*$/.test(smsCode);
  },
  GetQueryString: name => {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
      return unescape(r[2]);
    } else {
      var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
      if (window.location.hash.split('?') != null && window.location.hash.split('?').length > 1) {
        var r = window.location.hash.split('?')[1].match(reg);
        if (r != null) {
          return unescape(r[2]);
        }
        return null;
      }
      return null;
    }
  },
  trim: str => {
    if (typeof str !== 'string') {
      return str;
    }
    return str.replace(/(^\s*)|(\s*$)/g, '');
  },
  isBlank: str => {
    if (str == null || typeof str == 'undefined') {
      return true;
    }
    var trimStr = this.trim(str);
    if (!trimStr) {
      return true;
    } else {
      return false;
    }
  },
  encrypt: str => {
    return strEnc(str, commonDef.commonDef.desKeys.firstKey, commonDef.commonDef.desKeys.secondKey, commonDef.commonDef.desKeys.thirdKey);
  },

  validateSpecilChar: s => {
    //js验证 `~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？
    var pattern = new RegExp("[~'!@#$%^&*()-+_=:]");
    if (s != '' && s != null) {
      if (pattern.test(s)) {
        return false;
      }
      return true;
    }
    return false;
  },
  //是否ios
  isIOS: () => {
    var u = navigator.userAgent;
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    return isiOS;
  },
  isWeiXin: () => {
    var ua = window.navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) && ua.match(/MicroMessenger/i)!.toString() && ua.match(/MicroMessenger/i)!.toString() == 'micromessenger') {
      return true;
    } else {
      return false;
    }
  },
  isPhone: (phone: string) => {
    return /^1[23456789]\d{9}$/.test(phone);
  }
};
export default utils;
