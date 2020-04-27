# grpc-achieve

[![license](https://img.shields.io/badge/license-MIT-green.svg?style=flat&logo=github)](https://www.mit-license.org)
[![java](https://img.shields.io/badge/java-1.8-brightgreen.svg?style=flat&logo=java)](https://www.oracle.com/java/technologies/javase-downloads.html)
[![gradle](https://img.shields.io/badge/gradle-5.6.2-brightgreen.svg?style=flat&logo=gradle)](https://docs.gradle.org/5.6.2/userguide/installation.html)
[![build](https://github.com/aaric/grpc-achieve/workflows/build/badge.svg)](https://github.com/aaric/grpc-achieve/actions)
[![release](https://img.shields.io/badge/release-0.4.0-blue.svg)](https://github.com/aaric/grpc-achieve/releases)

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
