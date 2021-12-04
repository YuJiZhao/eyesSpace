
   fetch('https://wttr.in/'+returnCitySN["cip"]+'?format="%l+\\+%c+\\+%t+\\+%h"').then(res=>res.text()).then(
    data => {
       if(document.getElementById('hexo_electric_clock')){
        var res_text = data.replace(/not found/g,'not found,not found').replace(/"/g,'').replace(/\+/g,'').replace(/,/g,'\\').replace(/ /g,'').replace(/°C/g,'');
        res_list = res_text.split('\\');
        var clock_box = document.getElementById('hexo_electric_clock');
        clock_box_html = `  
  <div class="clock-row">
<span id="card-clock-clockdate" class="card-clock-clockdate"></span>
<span class="card-clock-weather">${res_list[2]} ${res_list[3]} *C</span>
<span class="card-clock-humidity">💧 ${res_list[4]}</span>
</div>
  <div class="clock-row"><span id="card-clock-time" class="card-clock-time"></span></div>
  
  <div class="clock-row">
  <span class="card-clock-ip">${returnCitySN["cip"]}</span>
<span class="card-clock-location">${res_list[0]}</span>
  <span id="card-clock-dackorlight" class="card-clock-dackorlight"></span>
</div>
`;
        var week = ['SUN', 'MON', 'TUE', 'WED','THU' ,'FRI', 'SAT'];
        var card_clock_loading_dom = document.getElementById('card-clock-loading');
        card_clock_loading_dom.innerHTML='';
        clock_box.innerHTML= clock_box_html;
        function updateTime() {
            var cd = new Date();
            var card_clock_time = zeroPadding(cd.getHours(), 2) + ':' + zeroPadding(cd.getMinutes(), 2) + ':' + zeroPadding(cd.getSeconds(), 2);
            var card_clock_date = zeroPadding(cd.getFullYear(), 4) + '-' + zeroPadding(cd.getMonth()+1, 2) + '-' + zeroPadding(cd.getDate(), 2) + ' '+ week[cd.getDay()];
            var card_clock_dackorlight = cd.getHours();
            var card_clock_dackorlight_str;
            if(card_clock_dackorlight >12) {
                card_clock_dackorlight -= 12;
                card_clock_dackorlight_str = " PM";
            }else{
                card_clock_dackorlight_str = " AM";
            }
            if(document.getElementById('card-clock-time')){
            var card_clock_time_dom = document.getElementById('card-clock-time');
            var card_clock_date_dom = document.getElementById('card-clock-clockdate');
            var card_clock_dackorlight_dom = document.getElementById('card-clock-dackorlight');
            card_clock_time_dom.innerHTML= card_clock_time;
            card_clock_date_dom.innerHTML= card_clock_date;
            card_clock_dackorlight_dom.innerHTML= card_clock_dackorlight_str
                }
        }

        function zeroPadding(num, digit) {
            var zero = '';
            for(var i = 0; i < digit; i++) {
                zero += '0';
            }
            return (zero + num).slice(-digit);
        }
        
        
           var timerID = setInterval(updateTime, 1000);
           updateTime();
           
       

        console.log(res_list)
       }
    }
)
   
