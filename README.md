## chapter5
- 调整了spring-mvc.xml
- 配置了h2-console
- 添加了src/test/java
    - cn.niceabc.chapter5.IdentityServerTest.java
    - cn.niceabc.chapter5.UserAndGroupInUserTaskTest.java
- databaseSchemaUpdate可以配置的值有：
	- drop-create
	- create-drop
	- create
	- false, schema check version
	- true, schema update
	- 详见org.activiti.engine.impl.db.DbSqlSession.dbSchemaUpdate    

## chapter6
- 连接字符串里的~表示home
    ```xml
    <property name="jdbcUrl" value="jdbc:h2:file:~/activiti-in-action-chapter6;AUTO_SERVER=TRUE" />
    ```
- 测试步骤
    ```text
    mvn antrun:run -Pinit-db
    mvn jetty:run
    ```
    
## chapter7
- spring自动部署.bpmn
    ```text
    me.kafeitu.activiti.chapter7.engine.AutoDeploymentUseSpring
    ```
- 监听器
    ```text
    me.kafeitu.activiti.chapter7.listener.ListenerTest
    ```
- spring事务管理 `业务数据`和`流程数据`
    ```text
    me.kafeitu.activiti.chapter7.service.LeaveWorkflowService
    ``` 
- spring-boot + activiti
    ```text
    org.activiti.spring.boot.ProcessEngineAutoConfiguration
    activiti-spring-boot模块在初始化时会扫描classpath:/processes/**.bpmn20.xml，并部署到引擎中
    ```
- activiti-cdi ??? 为什么要用这个？

## chapter8
- mail task
- 自行搭建邮件服务未成功
- boundaryEvent的使用

## chapter9
- 多实例，某一个task由多个实例组成，如果是usertask，则需要多人次操作

## chapter10
- 子流程
- 调用活动

## chapter11
- 事件，这章还需要再研究
    - 开始
        - start event
        - start timer event
        - start signal event
        - start message event
        - start error event
    - 结束
        - end event
        - end error event
        - end cancel event
        - end terminate event
    - 中间
        - throwing
            - none throwing event
            - signal throwing event
        - catching
            - timer catching event
            - signal catching event
            - message catching event
    - 边界
        - error event
        - timer event
        - signal event
        - message event
        - cancel event
        - compensation event，补偿

## chapter12
- 附件
- 委派
    ```java
    taskService.delegateTask(task.getId(), delegatedUserId);
    ```

## chapter13


