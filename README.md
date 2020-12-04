# netty-demo
简单的netty-demo


```
协议：
protobuf自定义协议
长度+消息id+消息体(protobuf对象)
```
```
计划：
基于Netty+TCP+Protobuf实现的游戏后端服务器，包含Protobuf协议序列化、TCP拆包与粘包、长连接握手认证、心跳机制、断线重连机制、消息重发机制、读写超时机制、离线消息、线程池，zook管理session等功能。
```
```
现有功能:
netty 服务器启动， Protobuf协议序列化，自定义编码解码，seesion创建与管理，对应消息处理
尚未成功，仍需努力 
```
