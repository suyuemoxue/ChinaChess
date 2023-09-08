//关于按钮
function about() {
    swal("作者： 宋相杰\n学号：2120102104\n班级：21级网络工程(专升本)1班", {
        buttons: false,
    });
}

//音效按钮
function soundEffect() {
    myVideo.removeAttribute("muted");
    var slider = document.createElement("input");
    //静音默认值
    // slider.value=0;
    //获取视频视频声音值给进度条状态
    slider.value = myVideo.volume * 100;
    slider.type = "range";
    slider.id = "soundEffect"
    swal({
        content: slider,
    });
    slider.onclick = function () {
        //进度条值传给视频声音
        myVideo.volume = slider.value / 100;
        //取消静音状态
        if (myVideo.muted) {
            myVideo.muted = false;
        }
    }
}

// 设置
function mainToggle() {
    $(".mainToggle").toggle();
    if ($(".mainToggle")[0].style.display == "none") {
        $(".glyphicon-cog").show();
        $(".glyphicon-chevron-left").hide();
        /*  $(".glyphicon-chevron-left,.glyphicon-hand-left").hide();
          $(".glyphicon-chevron-right,.glyphicon-hand-right").show();*/
    } else {
        $(".glyphicon-cog").hide();
        $(".glyphicon-chevron-left").show();
        /* $(".glyphicon-chevron-right,.glyphicon-hand-right").hide();
         $(".glyphicon-chevron-left,.glyphicon-hand-left").show();*/
    }
}

$(document).ready(function () {
    //主菜单事件
    $("#menuDiv,#userDiv").hover(function () {
            console.log("hover事件");
            this.style.opacity = 1;
            // opacity: 0.5;
            // filter: alpha(opacity=40); /* 针对 IE8 以及更早的版本 */
          
            $("body").css("transform", "");

            // $("body").fadeTo(1000,1);
            // stop();
        },
        function () {
            this.style.opacity = 0.3;
            // start();
        });
    // $("#menuDiv").fadeTo(1000,
    //     0.5).fadeTo(2000, 0.6).fadeTo(3000, 0.7).fadeTo(4000, 0.8).fadeTo(5000, 0.9).fadeTo(
    //     6000, 1);
    function stop() {
        if ("undefined" != typeof backInterval) {
            clearInterval(backInterval);
        }
    }
    // start();
    function start() {
        stop();
        var deg = 0;
        backInterval = setInterval(function () {
            console.log("计时器正在运动！");
            $("body").fadeTo(500, 0.1).fadeTo(500, 0.2).fadeTo(500, 0.3).fadeTo(500, 0.4).fadeTo(500,
                0.5).fadeTo(500, 0.6).fadeTo(500, 0.7).fadeTo(500, 0.8).fadeTo(500, 0.9).fadeTo(
                500, 1);
            deg += 180;
            $("body").css("transform", "rotate3d(1,1,1," + deg +
                "deg) scale(" + parseInt(Math.random() * 3 / 1) + "," + parseInt(Math.random() * 3 /
                    1) + ")").css("transition-duration",
                "5s").css(
                "transition-timing-function", "ease-in");
        }, 5000);
    }
});
