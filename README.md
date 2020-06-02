# grpc-achieve

[![license](https://img.shields.io/badge/license-MIT-green.svg?style=flat&logo=github)](https://www.mit-license.org)
[![java](https://img.shields.io/badge/java-1.8-brightgreen.svg?style=flat&logo=java)](https://www.oracle.com/java/technologies/javase-downloads.html)
[![gradle](https://img.shields.io/badge/gradle-6.3-brightgreen.svg?style=flat&logo=gradle)](https://docs.gradle.org/6.3/userguide/installation.html)
[![build](https://github.com/aaric/grpc-achieve/workflows/build/badge.svg)](https://github.com/aaric/grpc-achieve/actions)
[![release](https://img.shields.io/badge/release-0.6.3-blue.svg)](https://github.com/aaric/grpc-achieve/releases)

> gRPC Learning.


## `Windows 10`通过`choco`安装命令行工具

```powershell
choco search protoc
choco install protoc
```

## 创建`HBase`测试表命令

```bash
hbase> create_namespace 'default'
hbase> create 'test', \
  {NAME=>'base',VERSIONS=>1,BLOCKCACHE=>true,BLOOMFILTER=>'ROW',COMPRESSION=>'SNAPPY',REPLICATION_SCOPE =>1}, \
  {SPLITS => ['1','2','3','4','5','6','7','8','9','a','b','c','d','e','f']}
```

## `gRPC`与`HBase`客户端冲突分析

> [`compile "org.apache.hbase:hbase-shaded-client:1.2.0"`](https://mvnrepository.com/artifact/org.apache.hbase/hbase-shaded-client)

```text
# io.grpc:grpc-all:1.28.1
  -> com.google.guava:guava:28.1-android  //最低版本：26.0-android
# org.apache.hbase:hbase-client:1.2.12
  -> com.google.guava:guava:12.0.1  //最高版本：16.0
# org.apache.hbase:hbase-client -> 1.3.6
  -> com.google.guava:guava:12.0.1
```

## `gRPC`流模式

### 服务端流模式

客户端请求一次，服务端可返回多次结果。

### 客户端流模式

客户端请求多次，服务端一次返回结果。

### 双流模式

客户端请求多次，服务端多次返回结果。
