import { AxiosRequestConfig } from "axios";
import progress from "@/utils/nprogress";

export default (request: AxiosRequestConfig<any>) => {
    progress.start();
}