# netty-demo
springboot + netty 非阻塞NIO模型


```
协议：google protobuf   
数据传输格式：消息总体长度 + 消息id + 消息体(protobuf对象)
开发工具：eclipse + maven + jdk1.8 + netty4.X + boot2.X
```
```
启动方式 -->正常springboot启动
```
```
开发任务：
基于springboot+Netty+TCP+Protobuf实现的游戏后端服务器，包含Protobuf协议序列化、TCP拆包与粘包、长连接握手认证、心跳机制、断线重连机
制、消息重发机制、读写超时机制、离线消息、线程池、zook管理session以及异步主动推送客户端消息等功能。
```
```
现有功能:
netty 服务器启动， Protobuf协议序列化，自定义编码解码，seesion创建与管理，对应消息处理，通过spring事件机制异步推送消息
尚未成功，仍需努力 
```
