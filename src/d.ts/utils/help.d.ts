export declare interface HelpInterface {
    checkEmail: (email: string) => boolean;
    debounce: (fn: () => void, delay?: number) => void;
    throttle: (fn: () => void, delay?: number) => void;
    setCookie: (name: string, value: string, exdays: number) => void;
    getCookie: (name: string) => string;
    delCookie: (name: string) => void;
    encryption: (str: string) => string;
    getTimeDisff: (t1: Date, t2: Date) => string;
    doCopy: (content: string) => void;
    estimateReadTime: (words: number) => string;
    cursorMove: (el: any, spos: number) => void;
    byte2MB: (size: number) => number;
    iosAgent: () => any;
}