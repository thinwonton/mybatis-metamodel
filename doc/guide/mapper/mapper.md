## java编程方式集成
例子请参考 mybatis-metamodel-test/tkmapper-test目录  

#### 1.引入库
```xml
   <dependency>
        <groupId>com.github.thinwonton.mybatismetamodel</groupId>
        <artifactId>tkmapper</artifactId>
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
                com.github.thinwonton.mybatis.metamodel.tkmapper.processor.gen.TKMapperMetaModelGenProcessor
            </processor>
        </processors>
        <options>
            <debug>false</debug>
            <usePrimitiveType>false</usePrimitiveType>
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
@Table(name = "user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -7703830119762722918L;
    @Id
    private Long id;
    private String username;
    private String password;
    @Column(name = "user_type")
    private String usertype;
    private String realname;
    private String address;
    private State state;
}

```
完成后，可以compile编译代码，在 ${project.build.directory}/generated-sources/annotations 目录下，
可以看到生成了 UserInfo_.class 文件，该文件即为元数据类。

#### 4.运行时注入元数据

初始化MetaModelContext，这里sqlSessionFactory和MapperHelper需要事先初始化。
```
EntityResolver entityResolver = new TKMapperEntityResolver();
MetaModelContext metaModelContext = new MetaModelContextImpl(
        new TKMapperGlobalConfigFactory(getSqlSessionFactory(), getMapperHelper()),
        entityResolver);

```

初始化后，可以通过 UserInfo_.class 静态获取元数据，也可以通过 metaModelContext 获取表名。
```
// 根据 example 条件查询
String queryUserName = "hugo_1";
Example userInfoQueryExample = new Example(UserInfo.class);
Example.Criteria criteria = userInfoQueryExample.createCriteria();
criteria.andEqualTo(UserInfo_.username.getColumn(), queryUserName);

//获取table名
String tableName = metaModelContext.getTableName(UserInfo_.class);
Assert.assertEquals("user_info", tableName);

```

## spring方式集成
例子请参考 mybatis-metamodel-test/tkmapper-spring-test目录  

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
                com.github.thinwonton.mybatis.metamodel.tkmapper.processor.gen.TKMapperMetaModelGenProcessor
            </processor>
        </processors>
        <options>
            <debug>false</debug>
            <usePrimitiveType>false</usePrimitiveType>
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
@Table(name = "user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -7703830119762722918L;
    @Id
    private Long id;
    private String username;
    private String password;
    @Column(name = "user_type")
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
**（1）通过mapperHelper和sqlSessionFactory配置**
```xml
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
</bean>

<bean id="config" class="tk.mybatis.mapper.entity.Config">
    <property name="style" value="camelhump"/>
</bean>

<bean id="mapperHelper" class="tk.mybatis.mapper.mapperhelper.MapperHelper">
    <property name="config" ref="config"/>
</bean>

<bean id="mapperScannerConfigurer" class="tk.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="mapperHelper" ref="mapperHelper"/>
    <property name="basePackage" value="com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.mapper"/>
</bean>

<bean class="com.github.thinwonton.mybatis.metamodel.tkmapper.spring.MetaModelContextFactoryBean">
    <property name="mapperHelper" ref="mapperHelper"/>
    <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
</bean>

```
**（2）通过Configuration配置**
```xml
<bean id="config" class="tk.mybatis.mapper.entity.Config">
    <property name="style" value="camelhump"/>
</bean>

<bean id="mapperHelper" class="tk.mybatis.mapper.mapperhelper.MapperHelper">
    <property name="config" ref="config"/>
</bean>

<!--使用 Configuration 方式进行配置-->
<bean id="mybatisConfig" class="tk.mybatis.mapper.session.Configuration">
    <property name="mapperHelper" ref="mapperHelper"/>
</bean>

<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <property name="configuration" ref="mybatisConfig"/>
</bean>

<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="basePackage" value="com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.spring.mapper"/>
    <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
</bean>

<bean class="com.github.thinwonton.mybatis.metamodel.tkmapper.spring.MetaModelContextFactoryBean">
    <property name="mapperHelper" ref="mapperHelper"/>
    <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
</bean>
```

初始化后，可以通过 UserInfo_.class 静态获取元数据，也可以通过 metaModelContext 获取表名。
```
// 根据 example 条件查询
String queryUserName = "hugo_1";
Example userInfoQueryExample = new Example(UserInfo.class);
Example.Criteria criteria = userInfoQueryExample.createCriteria();
criteria.andEqualTo(UserInfo_.username.getColumn(), queryUserName);

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
                com.github.thinwonton.mybatis.metamodel.tkmapper.processor.gen.TKMapperMetaModelGenProcessor
            </processor>
        </processors>
        <options>
            <debug>false</debug>
            <usePrimitiveType>false</usePrimitiveType>
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
@Table(name = "user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -7703830119762722918L;
    @Id
    private Long id;
    private String username;
    private String password;
    @Column(name = "user_type")
    private String usertype;
    private String realname;
    private String address;
    private State state;
}

```
完成后，可以compile编译代码，在 ${project.build.directory}/generated-sources/annotations 目录下，
可以看到生成了 UserInfo_.class 文件，该文件即为元数据类。

#### 4.配置spring bean
这里的mapperHelper必须使用SpringBootBindUtil.bind()绑定springboot的配置，否则MetaModelContext初始化过程将读取不到
相应的配置
```
@MapperScan(
        basePackages = "com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.springboot.mapper",
        mapperHelperRef = "mapperHelper"
)
@Configuration
public class MybatisConfigurer {
    @Bean
    public MapperHelper mapperHelper(Environment environment) {
        Config config = SpringBootBindUtil.bind(environment, Config.class, Config.PREFIX);
        MapperHelper mapperHelper = new MapperHelper();
        mapperHelper.setConfig(config);
        return mapperHelper;
    }

    @Bean
    public MetaModelContextFactoryBean metaModelContext(SqlSessionFactory sqlSessionFactory, MapperHelper mapperHelper) {
        MetaModelContextFactoryBean metaModelContextFactoryBean = new MetaModelContextFactoryBean();
        metaModelContextFactoryBean.setMapperHelper(mapperHelper);
        metaModelContextFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        return metaModelContextFactoryBean;
    }

}
```

初始化后，可以通过 UserInfo_.class 静态获取元数据，也可以通过 metaModelContext 获取表名。
```
// 根据 example 条件查询
String queryUserName = "hugo_1";
Example userInfoQueryExample = new Example(UserInfo.class);
Example.Criteria criteria = userInfoQueryExample.createCriteria();
criteria.andEqualTo(UserInfo_.username.getColumn(), queryUserName);

//获取table名
String tableName = metaModelContext.getTableName(UserInfo_.class);
Assert.assertEquals("user_info", tableName);

```

## 配置项
由于在编译时是无法获取到mapper的全局配置的，所以一些影响是否在元数据模型中生成元数据的配置需要在
注解处理器中设置。我们可以在plugin插件中加入配置项，例如上面涉及到的：
```xml
    <options>
        <debug>false</debug>
        <usePrimitiveType>false</usePrimitiveType>
    </options>
```

在mapper中，涉及下面几个配置：
- debug 是否在compile时打印debug信息，默认为false
- usePrimitiveType 请参考mapper中的定义
- useSimpleType 请参考mapper中的定义
- enumAsSimpleType 是否将枚举类型当成基本类型对待

