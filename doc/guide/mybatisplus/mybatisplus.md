## java编程方式集成
例子请参考 mybatis-metamodel-test/mybatisplus-test目录  

#### 1.引入库
```xml
    <dependency>
        <groupId>com.github.thinwonton.mybatismetamodel</groupId>
        <artifactId>mybatisplus</artifactId>
        <version>${version}</version>
    </dependency>
```

#### 2.项目中引入编译时注解处理器
编译后，将会在 ${project.build.directory}/generated-sources/annotations 目录中生成元数据模型代码
```xml
    <plugin>
        <groupId>org.bsc.maven</groupId>
        <artifactId>maven-processor-plugin</artifactId>
        <version>3.3.3</version>
        <configuration>
            <processors>
                <processor>
                    com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.gen.MybatisPlusMetaModelGenProcessor
                </processor>
            </processors>
            <options>
                <debug>false</debug>
            </options>
        </configuration>
        <executions>
            <execution>
                <id>process</id>
                <goals>
                    <goal>process</goal>
                </goals>
                <phase>generate-sources</phase>
                <configuration>
                    <outputDirectory>
                        ${project.build.directory}/generated-sources/annotations
                    </outputDirectory>
                </configuration>
            </execution>
            <execution>
                <id>process-test</id>
                <goals>
                    <goal>process-test</goal>
                </goals>
                <phase>generate-test-sources</phase>
                <configuration>
                    <outputDirectory>
                        ${project.build.directory}/generated-test-sources/test-annotations
                    </outputDirectory>
                </configuration>
            </execution>
        </executions>
    </plugin>

```

#### 3.需要生成元数据模型的domain model添加注解@GenMetaModel
例如UserInfo这个ORM类
```java
@GenMetaModel
@Data
@TableName("user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -7703830119762722918L;
    @TableId
    private Long id;
    private String username;
    private String password;
    @TableField("user_type")
    private String usertype;
    private String realname;
    private String address;
    private State state;
}

```
完成后，可以compile编译代码，在 ${project.build.directory}/generated-sources/annotations 目录下，
可以看到生成了 UserInfo_.class 文件，该文件即为元数据类。

#### 4.运行时注入元数据

初始化MetaModelContext，支持两种方式。 

（1）使用MybatisConfiguration
```
MybatisConfiguration configuration = (MybatisConfiguration) getSqlSessionFactory().getConfiguration();

EntityResolver entityResolver = new MybatisPlusEntityResolver();
MetaModelContext metaModelContext = new MetaModelContextImpl(
        new MybatisPlusGlobalConfigFactory(configuration),
        entityResolver);

```

（2）使用SqlSessionFactory 和 mybatis-plus 的 GlobalConfig

初始化后，可以通过 UserInfo_.class 静态获取元数据，也可以通过 metaModelContext 获取表名。

```
// wrapper查询
// 查询address为中国的列表
List<UserInfo> userList = userInfoMapper.selectList(
        new QueryWrapper<UserInfo>().eq(UserInfo_.address.getColumn(), "中国")
);
Assert.assertEquals(10, userList.size());

//获取table名
String tableName = metaModelContext.getTableName(UserInfo_.class);
Assert.assertEquals("user_info", tableName);

```

## spring方式集成
例子请参考 mybatis-metamodel-test/mybatisplus-spring-test目录  

#### 1.引入库
```xml
<dependency>
    <groupId>com.github.thinwonton.mybatismetamodel</groupId>
    <artifactId>mybatisplus-spring</artifactId>
    <version>${version}</version>
</dependency>

```

#### 2.项目中引入编译时注解处理器
编译后，将会在 ${project.build.directory}/generated-sources/annotations 目录中生成元数据模型代码
```xml
 <plugin>
        <groupId>org.bsc.maven</groupId>
        <artifactId>maven-processor-plugin</artifactId>
        <version>3.3.3</version>
        <configuration>
            <processors>
                <processor>
                    com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.gen.MybatisPlusMetaModelGenProcessor
                </processor>
            </processors>
            <options>
                <debug>false</debug>
            </options>
        </configuration>
        <executions>
            <execution>
                <id>process</id>
                <goals>
                    <goal>process</goal>
                </goals>
                <phase>generate-sources</phase>
                <configuration>
                    <outputDirectory>
                        ${project.build.directory}/generated-sources/annotations
                    </outputDirectory>
                </configuration>
            </execution>
            <execution>
                <id>process-test</id>
                <goals>
                    <goal>process-test</goal>
                </goals>
                <phase>generate-test-sources</phase>
                <configuration>
                    <outputDirectory>
                        ${project.build.directory}/generated-test-sources/test-annotations
                    </outputDirectory>
                </configuration>
            </execution>
        </executions>
    </plugin>

```

#### 3.需要生成元数据模型的domain model添加注解@GenMetaModel
例如UserInfo这个ORM类
```java
@GenMetaModel
@Data
@TableName("user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -7703830119762722918L;
    @TableId
    private Long id;
    private String username;
    private String password;
    @TableField("user_type")
    private String usertype;
    private String realname;
    private String address;
    private State state;
}
```
完成后，可以compile编译代码，在 ${project.build.directory}/generated-sources/annotations 目录下，
可以看到生成了 UserInfo_.class 文件，该文件即为元数据类。

#### 4.配置spring bean
这里提供两种方式配置  
**（1）通过GlobalConfig和sqlSessionFactory配置**
```xml
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    <property name="basePackage" value="com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.spring.mapper"/>
</bean>

<!-- MyBatis 动态扫描  -->
<bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <property name="plugins">
        <array>
            <!-- 分页插件配置 -->
            <bean id="paginationInterceptor"
                  class="com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor"/>
        </array>
    </property>
    <property name="configLocation" value="classpath:globalconfig/mybatis-config.xml"/>
    <property name="globalConfig" ref="globalConfig"/>
    <!--<property name="mapperLocations" value="classpath:mapper/*.xml"/>-->
</bean>


<bean id="dbConfig" class="com.baomidou.mybatisplus.core.config.GlobalConfig$DbConfig">
    <property name="tableUnderline" value="true"/>
</bean>

<bean id="globalConfig" class="com.baomidou.mybatisplus.core.config.GlobalConfig">
    <property name="dbConfig" ref="dbConfig"/>
    <property name="banner" value="false"/>
</bean>

<bean class="com.github.thinwonton.mybatis.metamodel.mybatisplus.spring.MetaModelContextFactoryBean">
    <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"/>
    <constructor-arg name="globalConfig" ref="globalConfig"/>
</bean>
```
**（2）通过MybatisConfiguration配置**
```xml
<bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <!--<property name="mapperLocations" value="classpath:mapper/*.xml"/>-->
    <property name="plugins">
        <array>
            <!-- 分页插件配置 -->
            <bean id="paginationInterceptor"
                  class="com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor"/>
        </array>
    </property>
    <property name="configuration" ref="mybatisConfiguration"/>
    <property name="globalConfig" ref="globalConfig"/>
</bean>

<!-- MyBatis 动态扫描  -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    <property name="basePackage" value="com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.spring.mapper"/>
</bean>


<bean id="dbConfig" class="com.baomidou.mybatisplus.core.config.GlobalConfig$DbConfig">
    <property name="tableUnderline" value="true"/>
</bean>

<bean id="globalConfig" class="com.baomidou.mybatisplus.core.config.GlobalConfig">
    <property name="dbConfig" ref="dbConfig"/>
    <property name="banner" value="false"/>
</bean>

<bean id="mybatisConfiguration" class="com.baomidou.mybatisplus.core.MybatisConfiguration">
    <!-- 全局配置注入 -->
    <property name="globalConfig" ref="globalConfig"/>
</bean>

<!--这种方式，通过 mybatis-config.xml 配置mybatis不起效-->
<bean class="com.github.thinwonton.mybatis.metamodel.mybatisplus.spring.MetaModelContextFactoryBean">
    <constructor-arg name="mybatisConfiguration" ref="mybatisConfiguration"/>
</bean>
```

初始化后，可以通过 UserInfo_.class 静态获取元数据，也可以通过 metaModelContext 获取表名。
```
// wrapper查询
// 查询address为中国的列表
List<UserInfo> userList = userInfoMapper.selectList(
        new QueryWrapper<UserInfo>().eq(UserInfo_.address.getColumn(), "中国")
);
Assert.assertEquals(10, userList.size());

//获取table名
String tableName = metaModelContext.getTableName(UserInfo_.class);
Assert.assertEquals("user_info", tableName);

```

## springboot方式集成
例子请参考 mybatis-metamodel-test/tkmapper-springboot-test目录  

#### 1.引入库
```xml
<dependency>
    <groupId>com.github.thinwonton.mybatismetamodel</groupId>
    <artifactId>tkmapper-spring</artifactId>
    <version>${version}</version>
</dependency>
```

#### 2.项目中引入编译时注解处理器
编译后，将会在 ${project.build.directory}/generated-sources/annotations 目录中生成元数据模型代码
```xml
 <plugin>
        <groupId>org.bsc.maven</groupId>
        <artifactId>maven-processor-plugin</artifactId>
        <version>3.3.3</version>
        <configuration>
            <processors>
                <processor>
                    com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.gen.MybatisPlusMetaModelGenProcessor
                </processor>
            </processors>
            <options>
                <debug>false</debug>
            </options>
        </configuration>
        <executions>
            <execution>
                <id>process</id>
                <goals>
                    <goal>process</goal>
                </goals>
                <phase>generate-sources</phase>
                <configuration>
                    <outputDirectory>
                        ${project.build.directory}/generated-sources/annotations
                    </outputDirectory>
                </configuration>
            </execution>
            <execution>
                <id>process-test</id>
                <goals>
                    <goal>process-test</goal>
                </goals>
                <phase>generate-test-sources</phase>
                <configuration>
                    <outputDirectory>
                        ${project.build.directory}/generated-test-sources/test-annotations
                    </outputDirectory>
                </configuration>
            </execution>
        </executions>
    </plugin>
```

#### 3.需要生成元数据模型的domain model添加注解@GenMetaModel
例如UserInfo这个ORM类
```java

@GenMetaModel
@Data
@TableName("user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -7703830119762722918L;
    @TableId
    private Long id;
    private String username;
    private String password;
    @TableField("user_type")
    private String usertype;
    private String realname;
    private String address;
    private State state;
}
```
完成后，可以compile编译代码，在 ${project.build.directory}/generated-sources/annotations 目录下，
可以看到生成了 UserInfo_.class 文件，该文件即为元数据类。

#### 4.配置spring bean
@MapperScan使用mybatis原生的扫描器

```
@Configuration
@MapperScan("com.github.thinwonton.mybatis.metamodelgen.test.mybatisplus.springboot.mapper")
public class MybatisConfig {

    @Bean
    public MetaModelContextFactoryBean metaModelContextFactory(SqlSessionFactory sqlSessionFactory,
                                                               MybatisPlusProperties mybatisPlusProperties) {
        MetaModelContextFactoryBean metaModelContextFactoryBean = new MetaModelContextFactoryBean(
                sqlSessionFactory,
                mybatisPlusProperties.getGlobalConfig());
        return metaModelContextFactoryBean;
    }
}
```

初始化后，可以通过 UserInfo_.class 静态获取元数据，也可以通过 metaModelContext 获取表名。
```
// wrapper查询
// 查询address为中国的列表
List<UserInfo> userList = userInfoMapper.selectList(
        new QueryWrapper<UserInfo>().eq(UserInfo_.address.getColumn(), "中国")
);
Assert.assertEquals(10, userList.size());

//获取table名
String tableName = metaModelContext.getTableName(UserInfo_.class);
Assert.assertEquals("user_info", tableName);

```


