/*var mysqlHost = "http://localhost:8888/chat_points/";*/
var mysqlHost = location.origin + "/";
var opMysql = {

    deleteTable: function () {

        ajaxGet(mysqlHost + "DeleteTableServlet", {tableName: tbName}, function (rowsAffected) {
            showTipBarrageFunction("Mysql:" + rowsAffected + packageResult.opIndexDB.deleteTable);
        })

    },
    clearTable: function () {

        ajaxGet(mysqlHost + "ClearTableDataServlet", {tableName: tbName}, function (rowsAffected) {
            showTipBarrageFunction("Mysql:" + rowsAffected + packageResult.opIndexDB.clearTable);
        });

    },
    searchData: function (userName) {
        let serData = {
            "tableName": "game",
            "userName": userName
        }
        console.log(serData);
        console.log(mysqlHost + "searchDataController");
        ajaxGet(mysqlHost + "searchDataController", serData, function (result) {
            /*swal(result);*/
            console.log(result);
            outputMyTable(result);
        });
        return;
        ajaxGet(mysqlHost + "SearchDataController", sdblData, function (result) {
            // console.log(result);
            outputMyTable(result.listOutputUser);
            // $("#myTable tbody").children().detach();
            // for (rs of result.listOutputUser) {
            //     // ／  /|\  ━
            //     let rsGamePoints = rs.gamePoints === 0 ? "━" : rs.gamePoints;
            //     $("#myTable tbody").append("<tr><th>" + index +
            //         "</th><th><a href='https://bj.afreecatv.com/" + rs.id + "' target='_blank'>" + rs.userNick + "(" + rs.id + ")</a></th><th>" + convertGrade(rs.grade) + "</th><th>" + rs.chatPoints +
            //         "</th><th>" + rs.chatTimes + "</th><th>" + rsGamePoints + "</th><th>" + rs.allPoints +
            //         "</th><th>" + rs.allTimes + "</th><th>" + rs.date + "</th></tr>");
            //     index++;
            // }
            showEveryPageIndex(result.quantity, pageIndex);
        });
    },
    getMaxData: function (callback) {
        let todayDate = $("#timeFrequencys").text().substring(0, $("#timeFrequencys").text().indexOf("\t"));
        let todaySortCondition = {
            "table_name": tbName,
            // "searchType": "chatPoints",
            "today": todayDate
        }
        ajaxGet(mysqlHost + "SearchTodayMaxSortDataServlet", todaySortCondition, function (data) {

            callback(data);
        });

    },
    searchMaxData: function (serachType, callback) {
        let todayDate = $("#timeFrequencys").text().substring(0, $("#timeFrequencys").text().indexOf("\t"));
        let maxSortCondition = {
            "table_name": tbName,
            "searchType": serachType,
            "today": todayDate
        }
        ajaxGet(mysqlHost + "SearchTodayMaxSortDataServlet", maxSortCondition, function (data) {
            callback(data);
        });

    },
    searchDataByUserId: function (userName,searchDataCallBack) {
        let serData = {
            "tableName": "game",
            "userName": userName
        }
        console.log(serData);
        console.log(mysqlHost + "searchDataController");
        ajaxGet(mysqlHost + "searchDataController", serData, function (result) {
            /*swal(result);*/
            console.log(result);
            searchDataCallBack(result);
        });
        return;

        let todayDate = $("#timeFrequencys").text().substring(0, $("#timeFrequencys").text().indexOf("\t"));
        let searchUserData = userData;

        // console.log("searchUserData",searchUserData);
        // console.log("searchData",searchData);
        // searchUserData.id
        var dataString = "@" + searchUserData.userNick;

        // sql = "SELECT * FROM " + tbName + " WHERE id = ?;";
        //console.log(sql);
        var data;
        let searchRankDataCondition = {
            "tableName": tbName,
            "id": searchUserData.id,
            // "searchType": searchType,
            // "today": todayDate
        }
        // if (searchData.tex == "!채팅통계" || searchData.tex == "!채팅통계조회") {
        //     ajaxGet(mysqlHost + "SearchDataServlet", searchRankDataCondition, function (dataResult) {
        //         // console.log("服务返回数据",dataResult);
        //         dataString = dataString + ":채팅 횟수:" + dataResult.chatTimes + ",채팅 포인트:" + dataResult.chatPoints + ",게임 포인트:" +
        //             dataResult.gamePoints + ",총 횟수:" + dataResult.allTimes + ",총 포인트:" + dataResult.allPoints;
        //         // console.log(dataString);
        //         //发送信息
        //         sendMessageCustom(dataString, 1, 4);
        //     });
        // }
    },
    insertData: function (userData) {
        let todayDate = $("#timeFrequencys").text().substring(0, $("#timeFrequencys").text().indexOf("\t"));
        userData.tbName = tbName;
        userData.date = todayDate;
        userData.isResetTotalPointsEveryMonth = $("#resetTotalPointsEveryMonth").prop("checked");
        // console.log(userData);
        let uNick = userData.userNick;
        $.post(mysqlHost + "InsertDataServlet", userData, function (resData, status, xhr) {
            // console.log(status);
            // console.log(xhr);
            // console.log(resData);

            if (resData.updateType == "update") {

            } else if (resData.updateType == "updateDate") {
                showTipBarrageFunction(uNick + " " + (resData.date.date) + packageResult.opIndexDB.insertData[0]);
            } else if (resData.updateType == "resetMonth") {
                let logString = uNick + " " + (resData.date.month + 1);
                showTipBarrageFunction(logString + packageResult.opIndexDB.insertData[1]);
            } else if (resData.updateType == "updateMonth") {
                let logString = uNick + " " + (resData.date.month + 1);
                showTipBarrageFunction(logString + packageResult.opIndexDB.insertData[2]);
            } else {

            }
            if ($("#mysql").parent().css("background-color") != "green") {
                $("#mysql").parent().css("background-color", "green");

                // $("#websql").parent().css("background-color","");
                // $("#mysql").prop("checked",true);
                localStorageType = "mysql";
                //导入其他数据库
                // loadDatabase(localStorageType);
                $("#indexDB").parent().css("background-color", "");
            }

        }).fail(function (xhr, status, info) {
            // console.log("---------------服务器连接失败-------------------");
            // console.log(userData);
            // $("#mysql").parent().removeClass("label-success").addClass("label-success");
            if ($("#mysql").parent().css("background-color") != "red") {
                $("#mysql").parent().css("background-color", "red");

                // $("#websql").parent().css("background-color","green");
                // $("#indexDB").prop("checked",true);
                localStorageType = "indexdb";
                $("#indexDB").parent().css("background-color", "green");

            }
            // opWebsql.insertData(userData);
            opIndexDB.insertData(userData);
            // console.log("---------------服务器连接失败-------------------");

        });

    },
    addData: function (userData) {

        let addDataCondition = userData;
        addDataCondition.tableName = tbName;
        ajaxGet(mysqlHost + "CustAddDataServlet", addDataCondition, function (data) {
            // console.log(data);
        });
    },
    updateData: function (diceData, callback) {
        let todayDate = $("#timeFrequencys").text().substring(0, $("#timeFrequencys").text().indexOf("\t"));
        // today= new Date(todayDate).getDate();
        diceData.today = todayDate;
        diceData.tableName = tbName;
        ajaxGet(mysqlHost + "UpdateDiceDataServlet", diceData, function (data) {

            callback(data);
        });
    },
    exportDataFunction: function (callback) {
        ajaxGet(mysqlHost + "ExportAllDataServlet", {tableName: tbName}, function (data) {
            // console.log(data);
            callback(data);
        });
    },
    inportDataFunction: function (userDataArray, callBack) {

        // let ajaxPostData = {"tableName": tbName, "userList": JSON.stringify(userDataArray)};
        // ajaxPost(mysqlHost + "InportAllDataServlet", ajaxPostData, function (result) {
        //     showTipBarrageFunction("Mysql:" + result + packageResult.opIndexDB
        //         .inportDataFunction);
        //     callBack(result);
        // });
        //检测是否是数组if(Array.isArray(userDataArray)){}
        let ajaxPostData = {"tableName": tbName};
        // console.log(ajaxPostData);
        //分组的大小10个一组 不应该依赖于delayInputTextId
        let spliceArraLength = numberOfGroups(userDataArray.length);
        //测试数据
        // spliceArraLength = 5;

        // if(userDataArray.length/spliceArraLength<1){
        //     spliceArraLength=userDataArray.length;
        // }
        if (userDataArray.length <= spliceArraLength) {
            // console.log("直接上传");
            ajaxPostData.userList = JSON.stringify(userDataArray);
            ajaxPost(mysqlHost + "InportAllDataServlet", ajaxPostData, function (result) {
                showTipBarrageFunction("Mysql:" + result + packageResult.opIndexDB
                    .inportDataFunction);
                //刷新显示
                changePage(pgIndex);
                callBack(result);
            }, function () {
                closeInportPorgress("danger");
                return;
            });
        } else {
            // console.log(userDataArray.length / spliceArraLength);
            // if(userDataArray.length>10000){25
            // }
            // 限制提交不超过10次
            // if(userDataArray.length/spliceArraLength>10){
            //     spliceArraLength=parseInt(userDataArray.length/10);
            // }
            // console.log("分割数组");
            // console.log(userDataArray.length);
            // let conmmitArrayData = split_array(userDataArray, spliceArraLength);
            let conmmitArrayData;
            //限制链接数量
            // conmmitArrayData = avgGroup(userDataArray, 50);
            //限制每一组数量
            conmmitArrayData = split_array(userDataArray, spliceArraLength);
            let arraIndex = 0;
            let resultLength = 0;
            commitArray(arraIndex);
            $("#importBarParent").show();

            function commitArray(arraIndex) {
                let jsonUserData = JSON.stringify(conmmitArrayData[arraIndex]);
                ajaxPostData.userList = jsonUserData;
                ajaxPost(mysqlHost + "InportAllDataServlet", ajaxPostData, function (result) {
                    if (result > 0) {
                        resultLength += result;
                        // showTipBarrageFunction("Mysql:" + userDataArray.length + "/" + resultLength + packageResult.opIndexDB
                        //     .inportDataFunction);
                        openInportPorgress(resultLength, userDataArray.length, conmmitArrayData.length, arraIndex);

                        // console.log("提交" + conmmitArrayData.length + "/" + (arraIndex + 1) + "个数据完成");
                        arraIndex++;
                        if (arraIndex >= conmmitArrayData.length) {
                            showTipBarrageFunction("Mysql:" + resultLength + packageResult.opIndexDB.inportDataFunction);
                            // console.log("提交完成!");
                            closeInportPorgress("success");
                            //刷新显示
                            changePage(pgIndex);
                            callBack(result);
                            return;
                        }
                        // $("#delayInputTextId").val()
                        setTimeout(function () {
                            commitArray(arraIndex);
                        }, 100);
                    } else {
                        closeInportPorgress("danger");
                        // console.log("遇到错误");
                        return;
                    }
                }, function () {
                    closeInportPorgress("danger");
                    return;
                });
            }
        }

    }
}

