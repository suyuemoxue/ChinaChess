//预览图远程URL或者本地地址
var backUrl = "/image/jinmu.jpeg";
// console.log(bgURL);
//清除背景.css('background-repeat','no-repeat')
/*$('*').css('background', 'none');*/
// $('html').css('background', 'url(' + backUrl + ')').css('background-size', 'cover').css('height', '100%').css(
//     'width',
//     '100%').css("backgroundRepeat", "no-repeat").css("backgroundPosition", "center");

//开始清除播放
// document.querySelector("#myVideo").pause();

// 双击事件

//只执行一次
function once(fn) {
    let done = false;
    return function (...args) {
        if (!done) {
            done = true;
            fn.call({}, ...args);
        }
    };
}

const executeOnce = once(function () {
    $("a").css("color", "white");
});
//背景视频预览图远程URL或者本地地址
const videoURL = "video/background.mp4";
/*const videoURL = "backgroundController";*/
// muted
// autoplay=''
if (location.pathname == "/mainMenuController") {
    $("body").before(
        "<video id='myVideo' autoplay muted width=100% height=100%  class='fullscreenvideo' preload=''  playsinline='' poster='" +
        backUrl + "'    loop=''><source src='" +
        videoURL + "' type='video/mp4'>"
    );
} else if (location.pathname == "/chess") {
    $("body").before(
        "<video id='myVideo' width=100% height=100%  muted class='fullscreenvideo' preload='' playsinline='' poster='" +
        backUrl + "'    loop=''><source src='" +
        videoURL + "' type='video/mp4'>"
    );
}

$("#myVideo").bind('contextmenu', function () {
    return false;
})


const executePlay = once(function () {
    console.log("播放");
    playMyVideo();
    //监听播放器
    // $("html").hover(function () {
    //         // setInterval(function() {
    //         // $("body").css("backgroundImage", "none").css("transform", "none")
    //
    //         // }, 1000);
    //         // document.querySelector("#myVideo").play();
    //         playMyVideo();
    //         /*$("body").fadeTo(1000, 0.1).fadeTo(1000, 0.2).fadeTo(1000, 0.3).fadeTo(1000, 0.4).fadeTo(1000,
    //             0.5).fadeTo(1000, 0.6).fadeTo(1000, 0.7).fadeTo(1000, 0.8).fadeTo(1000, 0.9).fadeTo(
    //             1000, 1);*/
    //         /*$("#main").fadeTo(1000, 0.1).fadeTo(1000, 0.2).fadeTo(1000, 0.3).fadeTo(1000, 0.4).fadeTo(1000,
    //             0.5).fadeTo(1000, 0.6).fadeTo(1000, 0.7).fadeTo(1000, 0.8).fadeTo(1000, 0.9).fadeTo(
    //             1000, 1);*/
    //         $("#main").fadeTo(1000,
    //             0.5).fadeTo(2000, 0.6).fadeTo(3000, 0.7).fadeTo(4000, 0.8).fadeTo(5000, 0.9).fadeTo(
    //             6000, 1);
    //
    //     },
    //     function () {
    //         pauseMyVideo();
    //     });
});
var myVideo = document.querySelector("#myVideo");
//设置默认静音值为0
myVideo.volume=0;

function playMyVideo() {
    console.log("播放视频");
    //重新播放 myVideo.load();
    let playPromise = myVideo.play()
    if (playPromise !== undefined) {
        playPromise.then(() => {
            myVideo.load();
            myVideo.play();
        }).catch(() => {

        });
    }
}

function pauseMyVideo() {
    myVideo.pause();
}

$(document).ready(function () {
    //
    // $("html").dblclick(function () {
    //     console.log("双击事件");
    //     // $("body").toggle();
    //     // $("body").css("transform", "rotate3d(1,0,1,-" + 90 + "deg)").css("transition-duration", "5s").css(
    //     // 	"transition-timing-function", "ease-in");
    //     //显示隐藏
    //     // if ($("html").css("transform") == "none") {
    //     //     $("html").css("transform", "scale3d(1,0,2)").css("transition-duration", "3s").css(
    //     //         "transition-timing-function", "ease-in");
    //     //
    //     // } else {
    //     //     $("html").css("backgroundImage", "none").css("transform", "none");
    //     //
    //     //
    //     // }
    //     executeOnce();
    //     executePlay()
    //     playMyVideo();
    // });
});
/*


$("html").hover(function () {
    console.log("炫听事件")
    executeOnce();
    executePlay();
});
*/
window.onload = function () {
    // executeOnce();
    // executePlay();
}
