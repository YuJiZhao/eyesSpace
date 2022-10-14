export default (): void => {
    let timer = setInterval(() => {
        if (window.pageYOffset != 0) {
            window.scroll(0, Math.max(window.pageYOffset - 50, 0));
        } else {
            clearInterval(timer);
        }
    }, 10);
}