# Mybatis学习
## 一、创建一个Maven工程作为父工程
在IDEA中创建一个Maven工程,然后删除src目录,这样就形成了一个空工程,在pom.xml中引入
```xml
<!-- 导入依赖   -->
    <dependencies>
<!--        数据库链接驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.18</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.3</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>

    </dependencies>
```
创建父工程的目的是为了下面的子工程不用重新导包
## 二、创建子工程
在父工程新建一个Maven的module,会自动依赖父工程,然后根据mybatis官网,开始下面的步骤
### 从 XML 中构建 SqlSessionFactory
我们每次单元测试都需要创建sqlSession 所以将这些抽取成一个工具类
```java
// maven 会将resources目录下的文件进行打包,所以我们这个文件放到resources下
String resource = "mybatis-config.xml";
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
<!--先注释掉,因为我们目前还没有对应的mappers文件能够对应-->
<!--   
    <mappers>
        <mapper resource="org/mybatis/example/BlogMapper.xml"/>
    </mappers>
-->
</configuration>
```

然后继续编写工具类,提供获得sqlSession的方法

### 创建pojo实体类以及dao接口
创建pojo目录,创建数据库表对应的实体类,创建对应的dao接口

### 编写该dao接口对应的Mapper.xml
在dao接口目录下编写该接口对应的Mapper.xml文件
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace 指定该mapper文件对应的接口-->
<mapper namespace="com.paranoid.dao.UserDao">
<!--id 对应接口中的方法  resultType 指定返回的数据的对应类型-->
    <select id="getUserList" resultType="com.paranoid.pojo.User">
    select * from user;
  </select>
</mapper>
```
### 编写单元测试类
```java
@Test
public void getUserList(){
    SqlSession sqlSession = MybatisUtil.getSqlSession();
    UserDao mapper = sqlSession.getMapper(UserDao.class);
    List<User> userList = mapper.getUserList();

    for(User user: userList){
        System.out.println(user.toString());
    }
}
```
### 报错分析
一.提示找不到对应的mapper文件
mybatis中 每一个mapper都需要在mybatis配置文件中注册,我们之前的操作仅仅是将dao和mapper对应了起来,在`mybatis-config.xml`中添加
```xml
<mappers>
        <mapper resource="com/paranoid/dao/UserMapper.xml"/>
    </mappers>
```
进行mapper文件的注册

二.提示` Could not find resource com/paranoid/dao/UserMapper.xml`

这个提示证明我们将对应的mapper注册进去了,在执行时开始读取这个xml,但是maven打包是默认将`src/main/resources`中的`xml`文件打包进去,并不会打包`src/main/java`中的`xml`

所以需要在父工程的`pom.xml`中添加
```xml
<build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                    <include>**/*.tld</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                    <include>**/*.tld</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
```
这样文件就可以找到 能够查询出结果