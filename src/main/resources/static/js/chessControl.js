// 创建WebSocket
/*const url = '127.0.0.1:'+window.location.port+'/control';*/
/*const url = 'localhost:'+window.location.port+'/control';*/
const url = location.host + '/control';
const webSocket = new WebSocket('ws://' + url);
//打开事件
webSocket.onopen = function () {
    console.log("websocket已成功打开");
    //socket.send("这是来自客户端的消息" + location.href + new Date());
    // var msg = '{"test":"1"}';
    // console.log(msg);
    //发送消息
    // webSocket.send(msg);
};

var $all = $("#main img[src^='image/']");
var allLength = 32;
//你是黑子还是红子
var ofTeam;
// 对手是黑子还是红子
var mkTeam;
//获得消息事件
webSocket.onmessage = (event => {
    console.log("onmessage");
    var makeInformation = eval('(' + event.data + ')');
    console.log(makeInformation);
    // 将Object对象的属性 转换数组,获得大小
    let makeInformationLength = Object.keys(makeInformation).length;
    //判断服务器消息是否存在对手信息
    if (makeInformationLength > 2 && "maker" in makeInformation) {
        //更新对手信息
        $("#makerId").text(makeInformation.maker);
        $("#makerButton").click(function () {
            gameAchievements(makeInformation.maker);
        });

        /*     //黑棋子数量
         let blackLength = $("#main img[src^='image/BLACK']").length;
           //红棋子数量
           let redLength = $("#main img[src^='image/RED']").length;*/
        //初始化数据
        $("#makerChessNumber,#userChessNumber").html(16);
        //初始化高度
        $(".makerParentDiv,.useParentDiv").css("height","50%");
        //初始化长度
        allLength = 32;


        mkTeam = makeInformation.makerColor;
        $("#makerWinSpan").text(makeInformation.makerWin);
        $("#makerFailSpan").text(makeInformation.makerFail);
        //转换棋子头像
        if (makeInformation.makerColor == "RED") {
            // 黑棋子头像
            $("#makerImg").attr("src", "image/REDKING.GIF");
            // 背景色字体颜色
            $(".useParentDiv").addClass("blackChess").removeClass("redChess");

            // 红棋子头像
            $("#userImg").attr("src", "image/BLACKKING.GIF");
            // 背景色字体颜色
            $(".makerParentDiv").addClass("redChess").removeClass("blackChess");
            /*旋转棋盘*/
            // $(".chessDiv,.chessImg").addClass("rotateDom");
            //设置当前用户棋子颜色
            ofTeam = "BLACK";
            //观察元素发生变化
            let targetNode = $('#chessDiv')[0]
            let targetNodechange = {attributes: true, childList: true, subtree: true};

            function callback(mutationsList, observer) {
                // for (let mutation of mutationsList) {
                //     if (mutation.type === 'childList') {
                //         console.log('子节点增加或者删除');
                //     } else if (mutation.type === 'attributes') {
                //         console.log('The ' + mutation.attributeName + ' 属性被修改.');
                //     }
                // }
                //目标元素发生变化时执行的代码
                console.log("元素属性发生变化");
                /* 旋转棋盘*/
                $("#chessDiv,.chessImg").addClass("rotateDom");
            }

            //执行监听
            var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
            var mutationObserver = new MutationObserver(callback);
            mutationObserver.observe(targetNode, targetNodechange);
            //停止监听
            //mutationObserver.disconnect();

        } else if (makeInformation.makerColor == "BLACK") {
            // 红棋子头像
            $("#makerImg").attr("src", "image/BLACKKING.GIF");
            // 背景色字体颜色
            $(".useParentDiv").addClass("redChess").removeClass("blackChess");

            // 黑棋子头像
            $("#userImg").attr("src", "image/REDKING.GIF");
            // 背景色字体颜色
            $(".makerParentDiv").addClass("blackChess").removeClass("redChess");
            //设置当前用户棋子颜色
            ofTeam = "RED";

        }
        //刷新个人战绩信息
        $("#userWinSpan").html(makeInformation.userWin);
        $("#userFailSpan").text(makeInformation.userFail);
        return;
    }
    //判断服务器消息是否存在个人战绩信息
    if (makeInformationLength == 2 && "fail" in makeInformation && "win" in makeInformation) {
        $("#userWinSpan").text(makeInformation.win);
        $("#userFailSpan").text(makeInformation.fail);
        return;
    }
    //判断服务器消息是否存在认输信息
    if (makeInformationLength == 1 && "admitDefeatMessage" in makeInformation) {
        // alert(makeInformation.admitDefeatMessage);
        swal(makeInformation.admitDefeatMessage);
        // 定时隐藏
        // swal({text: makeInformation.admitDefeatMessage,
        //     timer: 2000});
        return;
    }

    if ("authority" in makeInformation && "board" in makeInformation && "endFlag" in makeInformation) {
        console.log("ofTeam:" + vm.chessBoard.ofTeam + ",authority" + makeInformation.authority);
        let $panelTitle = $("#beginBtn #panel-title");
        let $panelBody = $("#beginBtn .panel-body");

        if (ofTeam == "RED") {
            $panelBody.html("<span style='color: red'>你是红棋</span>");

        } else if (ofTeam == "BLACK") {
            $panelBody.html("<span style='color: black'>你是黑棋</span>");

        } else {
        }
        // vm.chessBoard.authority
        // 棋子移动提示
        if (makeInformation.authority == ofTeam) {
            // $panelBody.html("该你移动了～");
            $panelTitle.html("<span style='color: " + ofTeam + "'>该你移动了～</span>");
        } else {
            $panelTitle.html("<span style='color: " + mkTeam + "'>等待对手移动～</span>");
        }
        // 计算总棋子数量决定音效
        console.log("---------------------------------------------");
        var board = makeInformation.board;
        var boardLength = 0;
        for (let i = 0; i < board.length; i++) {
            for (let j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    /*  if (board[i][j].color != 'undefined') {
                          allLength++;
                      }*/
                    if ("color" in board[i][j]) {
                        boardLength++;
                    }
                }
            }
        }
        /*document.querySelector("#backAudio").play();*/
        if (boardLength == allLength) {
            playAudio("audio/移动.mp3");
        } else if (boardLength = allLength - 1) {
            playAudio("audio/象棋吃.mp3");
            allLength = boardLength;
            // 通过深入 Document 内部对 body 进行检测，获取窗口大小
            if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth) {
                winHeight = document.documentElement.clientHeight;
                // winWidth = document.documentElement.clientWidth;
            }
            let blackLength = $("#main img[src^='image/BLACK']").length;
            let redLength = $("#main img[src^='image/RED']").length;
            blackLength = blackLength - 1;
            redLength = redLength - 1;
            // $("#userChessNumber")
            //加高度
            // let addHeight = winHeight / 16;
            let addHeight;
            console.log("加高度" + addHeight);
            if (makeInformation.authority == mkTeam) {
                console.log(mkTeam + "子被吃");
                // 对手棋子数量
                if (mkTeam == "RED") {
                    console.log("对手的红子被吃" + redLength);
                    $("#makerChessNumber").html(redLength);

                } else if (mkTeam == "BLACK") {
                    console.log("对手的黑子被吃" + blackLength);
                    $("#makerChessNumber").html(blackLength);
                }
                addHeight = winHeight / 2 / blackLength;
                // $(".makerParentDiv").css("height", $(".makerParentDiv").height() - addHeight + "px");
                // $(".useParentDiv").css("height", $(".useParentDiv").height() + addHeight + "px");
            } else if (makeInformation.authority == ofTeam) {
                console.log(ofTeam + "子被吃");
                // 我的棋子数量
                if (ofTeam == "RED") {
                    console.log("我的红子被吃" + redLength);
                    $("#userChessNumber").html(redLength);
                } else if (ofTeam == "BLACK") {
                    console.log("我的黑子被吃" + redLength);
                    $("#userChessNumber").html(blackLength);
                }
                addHeight = winHeight / 2 / redLength;
                // $(".makerParentDiv").css("height", $(".makerParentDiv").height() + addHeight + "px");
                // $(".useParentDiv").css("height", $(".useParentDiv").height() - addHeight + "px");
            }
            /* 32/100*/
        }
        console.log("---------------------------------------------");
    }
    vm.chessBoard = eval('(' + event.data + ')');
    if (vm.chessBoard.endFlag) {
        playAudio("audio/将军(女声).mp3");
        vm.endGame();
    }
});


/*function playAudio(src) {
    document.querySelector("#backAudio").setAttribute("src", src);
    document.querySelector("#backAudio").load();
    document.querySelector("#backAudio").play();
}*/
var backAudio = document.querySelector("#backAudio");

function playAudio(src) {

    if (backAudio.getAttribute("src") != src) {
        backAudio.setAttribute("src", src);
        //重新载入
        backAudio.load();
    }
    let playPromise = backAudio.play();
    if (playPromise !== undefined) {
        playPromise.then(() => {
            backAudio.play();
        }).catch(() => {

        })
    }

}

//关闭事件
webSocket.onclose = function () {
    console.log("websocket已关闭");
};
//发生了错误事件
webSocket.onerror = function () {
    console.log("websocket发生了错误");
}

//认输
function admitDefeat() {
    /*  let b = confirm("确定投降吗？");*/
    swal({
        title: "投降", text: "确定投降吗？", icon: "warning", // buttons: true,
        // buttons: "确定",
        buttons: ["取消", "确认"], dangerMode: true,
    }).then((willDelete) => {
        if (willDelete) {
            webSocket.send(JSON.stringify({
                admitDefeat: 1
            }));
        }
    });
    //重置游戏
    // webSocket.send(JSON.stringify({
    //     way: '1'
    // }));
}

/*窗口尺寸变化调整尺寸*/
window.onresize = function () {

    let cssJson = {
        "margin-top": "1%", "margin-bottom": "1%", " margin-left": "1%", "margin-right": "1%"
    }
    let cssJson1 = {
        "margin-top": "10px", "margin-bottom": "9px", " margin-left": "9px", "margin-right": "9px"
    }
    // 调整象棋尺寸
    $(".chessImg,.emptyImg").css(cssJson1);

}

// 创建VUE实例
var vm = new Vue({
    el: '#main', data: {
        //当前用户的颜色
        beginGame: false,       // 开始游戏按钮标志
        chessBoard: {           // 棋盘
            board: [],          // 虚拟棋格
            authority: null,    // 行棋方
            endFlag: null,      // 结束标志,
        }, // 坐标
        XA: null, YA: null, XB: null, YB: null, tableNum: null // 桌号
    }, methods: {
        // 开始或重新游戏
        initGame() {
            $("#admitDefeatButton").show();
            if (this.beginGame) {
                swal({
                    title: "重开！", text: "您确定要重新游戏吗？", icon: "warning", buttons: ["取消", "确认"], dangerMode: true,
                }).then((willDelete) => {
                    // buttons: true,
                    // buttons: "确定",
                    if (willDelete) {
                        webSocket.send(JSON.stringify({
                            way: '1'
                        }));
                    }
                });
            } else {
                // 监听棋子
                // setTimeout(function () {
                //     addListener();
                // }, 2000);
                // console.log("点击开始游戏");
                webSocket.send('{}');
                this.beginGame = true;
            }


            /*allLength=$all.length;*/
        }, // 移动棋子
        moveChess(chess, XN, YN) {
            console.log("-----------------------------------moveChess-----------------------------------");
            playAudio("audio/步行.mp3");
            console.log(chess);


            // 游戏尚未结束
            if (!this.chessBoard.endFlag) {
                console.log("!this.chessBoard.endFlag");

                if (this.XA == null && chess != null && chess.color == this.chessBoard.authority) {
                    console.log("this.chessBoard.authority");
                    this.XA = XN;
                    this.YA = YN;
                } else if (this.XA != null && (this.XA != XN || this.YA != YN)) {
                    console.log("!this.chessBoard.authority");
                    this.XB = XN;
                    this.YB = YN;
                    webSocket.send(JSON.stringify({
                        way: "2", XA: this.XA, YA: this.YA, XB: this.XB, YB: this.YB
                    }));
                    this.empty();
                }
            }
            console.log("-----------------------------------moveChess-----------------------------------");
        }, // 结束游戏
        endGame() {
            console.log("-----------------------------------endGame-----------------------------------");
            console.log(this.chessBoard.authority);
            let msg = '红棋';
            if (this.chessBoard.authority == 'BLACK') {
                msg = '黑棋';
            }else if(this.chessBoard.authority=="RED"){

            }else{

            }
            // TODO 可用layer弹窗组件等代替
            // alert("恭喜，获胜的是：" + msg);
            swal("恭喜，获胜的是：" + msg);
            console.log("-----------------------------------endGame-----------------------------------");
        },

        // 清空坐标记录
        empty() {
            this.XA = null;
            this.YA = null;
            this.XB = null;
            this.YB = null;
        },
    }
});
function addListener() {

    var map = document.getElementById('loginUserDiv');

    let blackChess = $("#main img[src^='image/BLACK']");
     blackChess.forEach(function(index,each){
            console.log(index);
     });
    let redChess = $("#main img[src^='image/RED']");

    console.log(map);
    map.style.backgroundColor = "red";
    var isDrop = false
    var disx, disy
    // 按下时
    map.onmousedown = function (event) {
        console.log("按下时");
        disx = event.pageX - map.offsetLeft
        disy = event.pageY - map.offsetTop
        if(isDrop){
            isDrop = false;
        }else{
            isDrop = true;
        }
    }
    // 按下松开时
    window.onmouseup = function () {
        console.log("松开时");
        // isDrop = false
    }

    //移动
    window.onmousemove = function (event) {
        if (isDrop) {
            console.log("X:" + (event.pageX - disx) + ",Y:" + (event.pageY - disy));
            map.style.left = (event.pageX - disx) + 'px'
            map.style.top = (event.pageY - disy) + 'px'
        }
        console.log("移动");
    }

}

function addListener1() {
    // var map = document.getElementById('map');
    // var map = document.getElementsByClassName("panel");
    // console.log(map.length);
    // map = map[0];
    var map = document.getElementById('loginUserDiv');
    console.log(map);
    map.style.backgroundColor = "red";
    var isDrop = false
    var disx, disy
    // 按下时
    map.onmousedown = function (event) {
        console.log("按下时");
        disx = event.pageX - map.offsetLeft
        disy = event.pageY - map.offsetTop
        isDrop = true
    }
    // 按下松开时
    window.onmouseup = function () {
        console.log("松开时");
        isDrop = false
    }
    let blackLength = $("#main img[src^='image/BLACK']").length;
    console.log(blackLength);
    let redLength = $("#main img[src^='image/RED']").length;
    console.log(redLength);
    //移动
    window.onmousemove = function (event) {
        if (isDrop) {
            console.log("X:" + (event.pageX - disx) + ",Y:" + (event.pageY - disy));
            map.style.left = (event.pageX - disx) + 'px'
            map.style.top = (event.pageY - disy) + 'px'
        }
        console.log("移动");
    }

}

window.onload = function () {
    console.log("-----------------------------------onmouse-----------------------------------");
    console.log("-----------------------------------onmouse-----------------------------------");
}

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

//调用函数只执行一次
const executeOnceSoundEffect = once(function (slider) {
    console.log("触发事件");
    slider.onclick = function () {
        document.querySelector('#backAudio').volume = slider.value / 100;
    }
});

//音效按钮
function soundEffect() {
    var slider = document.createElement("input");
    slider.type = "range";
    slider.id = "soundEffect";
    //静音默认值
    // slider.value=0;
    //获取视频视频声音值给进度条状态
    slider.value = backAudio.volume * 100;
    swal({
        content: slider,
    });
    // executeOnceSoundEffect(slider);

    slider.onclick = function () {
        document.querySelector('#backAudio').volume = slider.value / 100;
        /*  let backAudio = document.querySelector("#backAudio");
   backAudio.setAttribute("src", "audio/将军(女声).mp3");*/
        /*播放*/
        playAudio("audio/将军(男声).mp3");
    }
}
