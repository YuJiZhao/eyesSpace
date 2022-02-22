declare module '*.png';
declare module '*.json';

interface resp_type {
    code?: string;
    msg?: string;
    [propName: string]: any;
}

//接口等待层
interface Wait {
    status: any;
    show: () => void;
    hide: () => void;
}