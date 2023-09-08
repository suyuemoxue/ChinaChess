//获取任意dom对象
// console.log("common.js");
//获取任意dom对象
function getDomById(domId) {
    domId = document.getElementById(domId);
    return domId;
}

function getDomByName(domName) {
    domName = document.getElementsByName(domName);
    return domName;
}

function getDomByClassName(domClassName) {
    domClassName = document.getElementsByClassName(domClassName);
    return domClassName;
}

var dx = {
    "sfz": function (id) {
        var domId = document.getElementById(id);
        return domId;
    },
    "lx": function (className) {
        var className = document.getElementsByClassName(className);
        return className;
    },
    "nm": function (name) {
        var name = document.getElementsByClassName(name);
        return name;
    }
}
var dynamicLoading = {
    css: function (path) {
        if (!path || path.length === 0) {
            throw new Error('argument "path" is required !');
        }
        var head = document.getElementsByTagName('head')[0];
        var link = document.createElement('link');
        link.href = path;
        link.rel = 'stylesheet';
        link.type = 'text/css';
        head.appendChild(link);
    },
    js: function (path) {
        if (!path || path.length === 0) {
            throw new Error('argument "path" is required !');
        }
        var head = document.getElementsByTagName('head')[0];
        var script = document.createElement('script');
        // script.async = "async";
        // script.defer = "defer";
        script.src = path;
        script.type = 'text/javascript';

        head.appendChild(script);
    }
};


function setDomTimeById(domTimeId) {

    setDomTimeInterval = setInterval(function () {
        var xhr = new XMLHttpRequest();
        if (!xhr) {
            xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhr.open("HEAD", "https:www.afreecatv.com", true);
        xhr.onreadystatechange =
            function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    /* alert(xhr.responseText);
                    alert(xhr.getResponseHeader("Date")); */
                    /* alert(dateTimeFormate(xhr.getResponseHeader("Date"))); */
                    // console.log(new Date(xhr.getResponseHeader("Date")).toLocaleString());
                    nowTime = dateTimeFormate(xhr.getResponseHeader(
                        "Date"));
                    getDomById(domTimeId).innerHTML = nowTime;
                    //版本校验
                    // if (localStorageType == "indexdb") {   }
                    if (typeof opIndexDB !== "undefined") {
                        opIndexDB.versionSynchronization();
                    }
                }
            }
        xhr.send(null);
    }, 1000);
}

function httpRequest(url, callback) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            callback(xhr.responseText);
        }
    }
    xhr.send();
}

function httpRequestData(callback) {
    var xhr = new XMLHttpRequest();
    if (!xhr) {
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xhr.open("HEAD", location.href, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {

            callback(dateTimeFormate(xhr.getResponseHeader("Date")));
        }
    }
    xhr.send();
}

//网络时间
httpRequestData(function (date) {
    console.log(date);
});
//调用
// httpRequest(msgUrl, function(result) {
// 	console.log(result);
// });

//base64封装的函数
function Base64() {
    // private property
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    // public method for encoding
    this.encode = function (input) {
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;
        input = _utf8_encode(input);
        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output +
                _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
                _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
        }
        return output;
    }

    // public method for decoding
    this.decode = function (input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        while (i < input.length) {
            enc1 = _keyStr.indexOf(input.charAt(i++));
            enc2 = _keyStr.indexOf(input.charAt(i++));
            enc3 = _keyStr.indexOf(input.charAt(i++));
            enc4 = _keyStr.indexOf(input.charAt(i++));
            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;
            output = output + String.fromCharCode(chr1);
            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }
        }
        output = _utf8_decode(output);
        return output;
    }

    // private method for UTF-8 encoding
    _utf8_encode = function (string) {
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }
        return utftext;
    }

    // private method for UTF-8 decoding
    _utf8_decode = function (utftext) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;
        while (i < utftext.length) {
            c = utftext.charCodeAt(i);
            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utftext.charCodeAt(i + 1);
                c3 = utftext.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    }
}

//判断是否是Base64
function isBase64(str) {
    if (str === '' || str.trim() === '') {
        return false;
    }
    try {
        return btoa(atob(str)) == str;
    } catch (err) {
        return false;
    }
}

//判断俩个对象是否相等
function isObjectValueEqual(a, b) {
    //取对象a和b的属性名
    var aProps = Object.getOwnPropertyNames(a);
    var bProps = Object.getOwnPropertyNames(b);
    //判断属性名的length是否一致
    if (aProps.length != bProps.length) {
        return false;
    }
    //循环取出属性名，再判断属性值是否一致
    for (var i = 0; i < aProps.length; i++) {
        var propName = aProps[i];
        if (a[propName] !== b[propName]) {
            return false;
        }
    }
    return true;
}

// var isChange = isObjectValueEqual({"11": "2"}, {"22": "qweqwe"});
// if (isChange == true) {
//     console.log('没有修改，不需要提示是否保存');
// } else {
//     console.log('发生修改，提示是否保存');
// }


function readTxt(file, callBack) {
    var reader = new FileReader();
    reader.readAsText(file, 'utf-8');
    reader.onload = function () {
        reader.result.split('\n').forEach(function (txt, i) {
            callBack(txt);
        });
    };
    reader.onerror = function () {
        // console.log('读取失败');
        console.log(reader.error);
    };
}

//普通下载
function downloadTxt(fileName, content) {
    let a = document.createElement('a');
    a.href = 'data:text/plain;charset=utf-8,' + content
    a.download = fileName
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
}

// 二、通过文件流的形式下载
// 通过FileReader转化为base64字符串下载
function downloadTxtBolb(fileName, content) {
    let blob = new Blob([content], {
        type: "text/plain;charset=utf-8"
    });
    let reader = new FileReader();
    reader.readAsDataURL(blob);
    reader.onload = function (e) {
        let a = document.createElement('a');
        a.download = fileName;
        a.href = e.target.result;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    }
}

// eval()
// function evil(fn) {
//     var Fn = Function;  //一个变量指向Function，防止有些前端编译工具报错
//     return new Fn("return " + fn)();
// }
// evil(fnStr);
//
// Function( "return "+ fnStr)();
// setTimeout( "result." +fnStr, 1 );

function evil(fn) {
    var Fn = Function;  //一个变量指向Function，防止有些前端编译工具报错
    return new Fn("return " + fn)();
}

function getRandom(no) {
    return parseInt(Math.random() * (no) / 1 + 1);
}


// Javascript的数字类型是以64位的IEEE 754格式存储的。

function toDecimal(x) {
    var val = Number(x)
    if (!isNaN(parseFloat(val))) {
        val = val.toFixed(1);//把 Number 四舍五入为指定小数位数的数字。
    }
    return val;
}

//保留小数点后面几位
function roundFun(value, n) {
    return Math.round(value * Math.pow(10, n)) / Math.pow(10, n);
}

// 四舍六入五考虑，五后非零就进一，五后为零看奇偶，五前为偶应舍去，五前为奇要进一。
// toFixed()

//保留n位小数并格式化输出（不足的部分补0）
var fomatFloat = function (value, n) {
    var f = Math.round(value * Math.pow(10, n)) / Math.pow(10, n);
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        s += '.';
    }
    for (var i = s.length - s.indexOf('.'); i <= n; i++) {
        s += "0";
    }
    return s;
}


// $.get()
// $.getJson();
//$.post();

// POST /url 创建
// DELETE /url/xxx 删除
// PUT /url/xxx 更新
// GET /url/xxx 查看
function ajaxGet(url, data, callback) {
    $.ajax({
        url: url,
        type: 'get',
        data: data,
        success: function (result, statis) {
            callback(result)
        },
        error: function (error, errorMessage) {

        }
    });
}

function ajaxPost(url, ajaxPostData, callback, errorCallback) {
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        data: ajaxPostData,
        success: function (result, statis) {
            callback(result);
        },
        error: function (error, errorMessage) {
            // console.log(error);
            errorCallback();
        }
    });
}

function ajaxPostFormData(url, formData, callback) {
    $.ajax({
        url: "http://localhost:8888/chat_points/InportAllDataServlet",
        type: 'post',
        dataType: 'json',
        processData: false,
        data: formData,
        success: function (result, statis) {
            console.log(result)
        },
        error: function (error, errorMessage) {
            // console.log(error)
        }
    });
}


function fetchGet(url) {
    fetch(url).then((response) => {
        if (response.ok) {
            response.text().then(function (resolve) {
            });
        }
    }, (error) => {
    });
}


function fechGet(fgURL) {
    fetch(fgURL).then((response) => {
        if (response.ok) {
            response.text().then(function (resolve) {
                console.log(resolve);
                // sendMessageToPlayAfreecatv(actionJson);
            })
        }
    }, (error) => {
        urlInit++;
        if (urlInit >= url.length) {
            urlInit = 0;
        }
        setTimeout(function () {
            fechGet(url[urlInit])
        }, 3000);

    });
}

function fenchArray(url) {
    if (Array.isArray(url)) {
        var urlInit = 0;
        fgURL = url[urlInit]
        fechGet(fgURL);
    } else {
        fetch(url).then((response) => {
            if (response.ok) {
                response.text().then(function (resolve) {
                });
            }
        }, (error) => {
        });
    }
}

//聚焦函数
function setContentEditableSelection(idDom) {
    var el = getDomById(idDom);
    var selection = window.getSelection();
    var range = document.createRange();
    selection.removeAllRanges();
    range.selectNodeContents(el);
    range.collapse(false);
    selection.addRange(range);
    el.focus();
}

//替换
function replaceSmall(text) {
    text = text.toString().replaceAll('0', '₀').replaceAll('1', '₁')
        .replaceAll('2', '₂').replaceAll('3', '₃').replaceAll('4', '₄')
        .replaceAll('5', '₅').replaceAll('6', '₆').replaceAll('7', '₇')
        .replaceAll('8', '₈').replaceAll('9', '₉').replaceAll('+', '₊')
        .replaceAll('-', '₋');
    // .replaceAll(' ', '');
    return text;
}

//获得随机颜色
function getRandomColor() {
    const rdColor = ['Red', 'Orange', 'Yellow', 'Green', 'Cyan', 'Blue', 'Purple'];
    let cr = rdColor[parseInt(Math.random() * 10 % (rdColor.length))];
    return cr;
}

//指定滚动条滚动到指定位置
function scrollToLocation(parent, son) {
    var mainContainer = $(parent),
        scrollToContainer = mainContainer.find(son); //滚动到<div id="thisMainPanel">中类名为son-panel的最后一个div处
    //scrollToContainer = mainContainer.find('.son-panel:eq(5)');//滚动到<div id="thisMainPanel">中类名为son-panel的第六个处
    //非动画效果
    //mainContainer.scrollTop(
    //  scrollToContainer.offset().top - mainContainer.offset().top + mainContainer.scrollTop()
    //);
    //动画效果
    mainContainer.animate({
        scrollTop: scrollToContainer.offset().top - mainContainer.offset().top + mainContainer.scrollTop()
    }, 2000); //2秒滑动到指定位置
}

//滚动条滚动到最上面
function scrollToTop(domName) {
    if ($(domName).scrollTop() != 0) {
        //直接滚动
        // $("#increaseLogPre").scrollTop(0);
        //带动画的滚动
        $(domName).animate({
            scrollTop: 0
        }, 500);
    }
}

//分割一个数组
/*
function groupArray(array, subGroupLength) {
    let index = 0;
    let newArray = [];
    while(index < array.length) {
        newArray.push(array.slice(index, index += subGroupLength));
    }
    return newArray;
}*/
function split_array(arr, len) {//arr需要拆分的原数组，len小数组包含多少个元素
    let a_len = arr.length;
    let result = []; //结果数组
    for (let i = 0; i < a_len; i += len) {
        result.push(arr.slice(i, i + len)); //循环取原数组N个元素,每次取从上次取的下一个开始取。
    }
    return result; //结果是一个数组，里面的元素就是已拆分的小数组
}


// if(202%100>0){
//     202/100+1;
// }

function avgGroup(arr, len) {
    // var arr=[1,2,3,4,5,6,7];
    let rstResult = new Array(len);
    for (let j = 0; j < arr.length; j++) {
        let index = j % rstResult.length;
        if (Array.isArray(rstResult[index])) {
            rstResult[index].push(arr[j]);
        } else {
            rstResult[index] = [arr[j]];
        }
    }
    // console.log(rstResult);
    return rstResult;
}


function split_array1(arr) {//arr需要拆分的原数组，len小数组包含多少个元素
    let a_len = arr.length;
    let len = 1;
    let result = []; //结果数组
    for (let i = 0; i < a_len; i += len) {
        result.push(arr.slice(i, i + len)); //循环取原数组N个元素,每次取从上次取的下一个开始取。
    }
    return result; //结果是一个数组，里面的元素就是已拆分的小数组
}


// function split_array2(arr, len) {
//     // 202个数据
//     var arr=[1,2,3,412,5,344,5123123,412...]
//     var myArray=new Array(100);
//     var j=0;
//     for(let i = 0; i < myArray.length; i ++){
//         myArray[i]=arr[j];
//         j++;
//         if(j>=100){
//             return;
//         }
//     }
// }
function abd() {
    // 202个数据
    var arr = [1, 2, 3, 4, 5];
    var lth = 1;
    var rstResult = new Array(lth);
    for (let i = 0; i < rstResult.length; i++) {
        rstResult[i] = [];
    }
    console.log(rstResult);
    for (let j = 0; j < arr.length; j += lth) {
        console.log(arr[j]);
        var result = [];
        for (let k = j; k < lth + j; k++) {
            if (typeof arr[k] != "undefined") {
                result.push(arr[k]);
            } else {
                break;
            }
        }
        console.log(result);
        for (let i = 0; i < result.length; i++) {
            rstResult[i].push(result[i]);
        }
    }
    console.log("-------------------");
    console.log(rstResult);

}

function aaaa() {
    // 形式1
    var arr = [1, 2, 3, 4, 5, 6, 7];
    var rstResult = new Array(3);
    for (let k = 0; k < rstResult.length; k++) {
        rstResult[k] = [];
    }
    for (let j = 0; j < arr.length; j++) {
        // console.log(arr[j]);
        for (let i = 0; i < rstResult.length; i++) {
            if (j % (rstResult.length) == i) {
                rstResult[i].push(arr[j]);
                break;
            }
        }
    }
    console.log(rstResult);


    // 形式2
    var arr = [1, 2, 3, 4, 5, 6, 7];
    var rstResult = new Array(3);
    // for(let k=0; k<rstResult.length;k++){
    //     rstResult[k]=[];
    // }
    for (let j = 0; j < arr.length; j++) {
        for (let i = 0; i < rstResult.length; i++) {
            if (j % (rstResult.length) == i) {
                // 1.typeof rstResult[i]!="undefined"
                //2.Array.isArray(rstResult[i])
                //3.if(rstResult[i]){}
                if (Array.isArray(rstResult[i])) {
                    rstResult[i].push(arr[j]);
                } else {
                    rstResult[i] = [arr[j]];
                }
                break;
            }
        }
    }
    console.log(rstResult);
}

