export default {
    // 构建get请求的URL
    buildGetUrl: (baseUrl, req) => {
        if(req == null) return baseUrl;
        let url = baseUrl + "?";
        let propertyList = Object.keys(req);
        for(let i = 0; i < propertyList.length; i++) {
            url += propertyList[i] + "=" + req[propertyList[i]];
            if(i != propertyList.length - 1) url += "&";
        }
        return url;
    },

    // 构建 [{label: "", value: ""}] 类型数据
    buildLabVal: (labArr, valArr) => {
        if(labArr.length != valArr.length || labArr.length == 0) return false;
        let res = [];
        for(let i = 0; i < labArr.length; i++) {
            res.push({
                label: labArr[i],
                value: valArr[i]
            });
        }
        return res;
    },

    // 获取当前时间
    getNowTime: () => {
        let arr = [ "星期日","星期一","星期二","星期三","星期四","星期五","星期六",];
        let date = new Date();
        let year = date.getFullYear();
        let month = date.getMonth() + 1;
        let dates = date.getDate();
        let day = date.getDay();
        let hour = date.getHours();
        let minute = date.getMinutes();
        let second = date.getSeconds();
        return {
            year, month, dates, day, 
            week: arr[day],
            hour, minute, second
        }
    }
};