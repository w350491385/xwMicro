技术：maven + spring + mybatis + quartz + hessian + mysql + etcd + hystrix (jdk必须是1.8或以上)

/**
在xwMicroServer下面
按数据库表身材对应的dao mapper model xml文件和java类
*/
mvn mybatis-generator:generate //生成数据库操作代码

1.服务注册机制
自定义etcd连接池，生成dir目录规则:/server+接口类类名全称,并监听此目录，设目录时效ttl(默认100秒)，例如：com.cn.demo.UserService 改服务接口的目录为：/server/com.cn.demo.UserService;
服务的服务提供者按key ,value 方式保存在该目录小；key格式：ip:port; value格式：ip:port/接口类类名全称

2.服务消费者
自定义etcd连接池，消费这监听/server+接口类类名全称 按key,value方式保存服务提供者url地址

3.系统配置
系统配置按/system/(register配置的group)/config,并监听此目录
数据源：datasource+ . + propertyName 例如：mysql的property文件中配置的 jdbc.username，在etcd里面配置：datasource.jdbc.username
redis：redis+ . + propertyName 例如：redis的property文件中配置的 port，在etcd里面配置：redis.port
schedule:schedule+ . + propertyName 例如：Quartz的property文件中配置的 corn，在etcd里面配置：schedule.triggerName_groupName
other:schedule+ . + propertyName 例如：property文件中配置的 peropertyName，在etcd里面配置：other.peropertyName

