import { BlogDetailContextInterface } from "@/components/content/blogDetail/d.ts/blogDetailContext";

const blogDetailContext: BlogDetailContextInterface = {
    data: {},
    init: (cx) => {
        Object.assign(blogDetailContext.data, cx);
    },
}

export { blogDetailContext };