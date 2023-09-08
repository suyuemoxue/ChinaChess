/*查询战绩*/
function gameAchievements(userName) {
    $('#myModal').modal('show');
    opMysql.searchDataByUserId(userName, function (searchDataCallBackData) {
        outputMyTable(searchDataCallBackData)
    });
    $("#whos").text(userName);
}

function outputMyTable(result) {
    $("#myTable tbody").children().detach();
    let index = 1;
    for (rs of result) {
        // ／  /|\  ━
        // let rsGamePoints = rs.gamePoints === 0 ? "━" : rs.gamePoints;
        let className;
        if ($("#whos").text() == rs.winner) {
            className= "success";
        } else {
            className= "danger";
        }
        $("#myTable tbody").append("<tr class='"+className+"'><td>" + index +
            "</td><td>" + rs.p1 + "</td><td>" + rs.p2 + "</td><td>" + rs.startTime +
            "</td><td>" + rs.endTime + "</td><td>" + rs.winner + "</td></tr>");
        index++;
    }
}