import { CardListInterface, BlogDetailProcessInterface } from "@/components/content/blogDetail/d.ts/blogDetailProcess";
import { ref } from "vue";

const CardList: CardListInterface = {
    cardInitLoad: ref(true),
    cardInitFail: ref(false)
};

const blogDetailProcess: BlogDetailProcessInterface = {
    ...CardList,
}

export default blogDetailProcess;