import { BlogProcessInterface, BlogExtInterface } from "@/components/content/blog/d.ts/blogProcess";
import { reactive, ref } from "vue";

const ext: BlogExtInterface = {
}

const blogProcess: BlogProcessInterface = {
    ...ext
}

export default blogProcess;