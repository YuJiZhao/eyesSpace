interface ApiObject {
    safe: () => Promise<void>;
    copywriting: (req: bean.copywriting) => Promise<resp_type>;
}