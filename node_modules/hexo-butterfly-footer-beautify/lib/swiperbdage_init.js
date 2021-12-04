var bdageswiper = new Swiper('#ghbdages', {
  direction: 'vertical',
  passiveListeners: true,
  spaceBetween: 30,
  // effect: 'coverflow',
  loop: true,
  autoplay: {
    disableOnInteraction: true,
    delay: 1500
  },
  mousewheel: true,
});

var ghbdage = document.getElementById('ghbdages');
ghbdage.onmouseenter = function() {
  bdageswiper.autoplay.stop();
};
ghbdage.onmouseleave = function() {
  bdageswiper.autoplay.start();
}
