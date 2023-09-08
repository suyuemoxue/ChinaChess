# 基于Java的象棋游戏的设计与实现

此项目是本人毕设，该开发系统以IntelliJ IDEA作为一个主要的开发工具，以Java语言作为后端编程语言，并使用了Spring Boot、MyBatis等Java相关框架，以HTML、CSS、JavaScript语言，以及JQuery、Bootstrap、Vue等前端相关框架共同开发前端，以MySQL作为数据库，以WebSocket协议实现人人对战。

## 功能说明

这个系统主要实现人人对弈，主界面可以进行登录注册、开始游戏、调整背景音效、查看开发者信息，点击开始游戏进入对弈界面，点击匹配可以匹配对手，单人不可开始游戏，开始对弈后双方依次行棋，红方先手，屏幕左侧显示己方名称和对方名称，双方剩余棋子数量，点击名称可查看双方的对战胜负记录，屏幕右侧显示用户名和注销按钮，用户名下方显示当前行棋方、返回主页按钮、音效按钮、认输按钮和重新开始按钮。双方交替行棋，率先吃掉对方的“将”棋或“帅”棋者为胜。某一方点击认输按钮或者掉线，则直接判负。

## 功能结构图
![img](file:///C:\Users\13291\AppData\Local\Temp\ksohtml14096\wps1.jpg)

## 数据库设计

### 用户信息表
![img](file:///C:\Users\13291\AppData\Local\Temp\ksohtml14096\wps2.jpg) 

| 列名  | 类型    | 长度 | 说明     |
| ----- | ------- | ---- | -------- |
| uid   | int     | 32   | 用户编号 |
| uname | varchar | 10   | 用户名   |
| upwd  | varchar | 15   | 用户密码 |

### 游戏记录表
![img](file:///C:\Users\13291\AppData\Local\Temp\ksohtml14096\wps3.jpg)

| 列名      | 类型     | 长度 | 说明          |
| --------- | -------- | ---- | ------------- |
| ids       | bigint   | 64   | 对局编号      |
| p1        | varchar  | 20   | 1号玩家用户名 |
| p2        | varchar  | 20   | 2号玩家用户名 |
| startTime | datatime | 40   | 对局开始时间  |
| endTime   | datatime | 40   | 对局结束时间  |
| winner    | varchar  | 20   | 对局获胜者    |

### 用户在线状态表
![img](file:///C:\Users\13291\AppData\Local\Temp\ksohtml14096\wps4.jpg)

| 列名     | 类型    | 长度 | 说明         |
| -------- | ------- | ---- | ------------ |
| status   | bigint  | 64   | 玩家编号     |
| userName | varchar | 20   | 玩家用户名   |
| playUser | varchar | 2    | 对弈双方代号 |
| status   | varchar | 10   | 在线状态     |
| mess     | varchar | 100  | 备注在线状态 |

### 用户匹配状态表
![img](file:///C:\Users\13291\AppData\Local\Temp\ksohtml14096\wps5.jpg)

| 列名          | 类型    | 长度 | 说明           |
| ------------- | ------- | ---- | -------------- |
| ids           | varchar | 20   | 玩家编号       |
| gameId        | varchar | 20   | 玩家游戏内编号 |
| playUser      | varchar | 20   | 对局双方名称   |
| timeConsuming | varchar | 20   | 耗时           |
| statusType    | varchar | 20   | 开始状态       |
| Mess          | varchar | 50   | 备注信息       |

__**数据库文件已放在本项目中**__

___

启动项目后，打开浏览器，输入项目网址即可使用，由于本项目是PVP，建议使用两个浏览器同时访问。

CSS样式存在适配问题，需要自己调整。