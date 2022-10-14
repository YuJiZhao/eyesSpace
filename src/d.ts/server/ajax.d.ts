import { UrlReqType } from "@/constant";

export declare type AddHeaderType = (existent?: any) => any;

export declare type BuildGetUrlType = (baseUrl: string, req: any) => string;

export declare type BuildMixGetUrlType = (baseUrl: string, req: MixGetUrlReqInterface) => string;
interface MixGetUrlReqInterface {
    path: Array<any>;
    param: any;
}

export declare type GetType = (url: string, req?: any, type?: UrlReqType) => Promise<RespInterface>;

export declare type PostType = (url: string, req?: any) => Promise<RespInterface>;

export declare type PutType = (url: string, req?: any) => Promise<RespInterface>;

export declare type DelType = (url: string, req?: any, type?: UrlReqType) => Promise<RespInterface>;