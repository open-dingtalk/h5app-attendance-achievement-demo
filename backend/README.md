## backend 后端服务
### 修改 application.yml 配置信息
```yaml
# 下面是企业内部应用的一些配置信息
app:
  app_key: 
  app_secret: 
  corp_id: 
  agent_id: 

  # 需要抓取的考勤数据的日期范围
  attendance:
    fromdate: 2022-01-01 00:00:00
    todate: 2022-01-10 00:00:00
```

### 启动
配置好后，直接运行 Application 即可。