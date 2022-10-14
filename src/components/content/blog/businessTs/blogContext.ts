import { BlogContextInterface } from "@/components/content/blog/d.ts/blogContext";

const blogContext: BlogContextInterface = {
    data: [],
    init: (cx) => {
        blogContext.data = cx;
    }
}

export { blogContext };