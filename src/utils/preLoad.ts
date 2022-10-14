type OptionsType = {
    data: Array<string>;
    success: (res: Array<HTMLImageElement>) => void;
}

export default (options: OptionsType): void => {
    let imgObj: Array<HTMLImageElement> = [];
    let total = options.data.length;
    let count = 0;
    for (let i = 0; i < total; i++) {
        let img = new Image();
        img.src = options.data[i];
        imgObj.push(img);
        img.onload = function () {
            count++;
            if (count == total) {
                options.success(imgObj);
            }
        }
    }
}