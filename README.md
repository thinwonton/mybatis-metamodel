# mybatis-metamodel
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.thinwonton/mybatis-metamodel/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.thinwonton/mybatis-metamodel)

## 介绍
mybatis-metamodel 是 mybatis-plus 和 tk mapper 的元数据模型生成插件，目的是解决它们在复杂场景下自定义SQL时的紧耦合问题

## 用途
我们都知道mybatis-plus 和 tk mapper 开源项目是mybatis优秀的增强插件，它们通过重定义MappedStatement实现了通用增删改查。它们所提供的增删改查的功能，极大提高了开发者的开发效率。
但是，在一些复杂的应用场景下，它们的通用增删改查不能满足我们的开发需求，为了实现需求，需要通过它们内置的条件查询组件自定义查询语句。在这种复杂场景下，就无法避免使用字符串表示
表名或者字段名，这在复杂的项目中如果修改了字段名，很容易造成后期运行时错误。    

为了避免应用在运行时发生错误，本项目将在编译期提供数据库的元数据，可以帮助应用在编译期就感知原有数据库元数据的修改。

举个列子：	
我们需要自定义SQL查询用户信息。引入本项目后，可以在编译期自动生成元数据模型UserInfo_.class，元数据模型里面包含每个table field的信息，除此之外，还可以通过MetaModelContext获取对应的
表信息。

**Mapper**

```java

// 根据 example 条件查询
String queryUserName = "hugo_1";
Example userInfoQueryExample = new Example(UserInfo.class);
Example.Criteria criteria = userInfoQueryExample.createCriteria();
criteria.andEqualTo(UserInfo_.username.getColumn(), queryUserName);
List<UserInfo> userInfosByQuery = userInfoMapper.selectByExample(userInfoQueryExample);
Assert.assertEquals(1, userInfosByQuery.size());

//获取table名
String tableName = metaModelContext.getTableName(UserInfo_.class);
Assert.assertEquals("user_info", tableName);

```

**mybatis-plus**

```java

 // 根据 map 查询
// 查询username为hugo_1的数据
Map<String, Object> map = new HashMap<>();
map.put(UserInfo_.username.getColumn(), "hugo_1"); //获取元数据
userInfo = userInfoMapper.selectByMap(map).get(0);

// wrapper查询
// 查询address为中国的列表
List<UserInfo> userList = userInfoMapper.selectList(
        new QueryWrapper<UserInfo>().eq(UserInfo_.address.getColumn(), "中国")
);

//获取table名
String tableName = metaModelContext.getTableName(UserInfo_.class);

```


## 支持特性

### mapper
支持MAPPER版本1.1.5+
1. 支持是否使用原语类型配置usePrimitiveType，默认为false，建议不要开启，没有意义
2. 支持配置useSimpleType，默认为true
3. 支持配置enumAsSimpleType
4. 支持数据库的catalog和schema配置
5. 支持全局字段转换方式style
6. 支持@Table注解
7. 支持@NameStyle注解
8. 支持@Transient、@Column、@ColumnType注解
9. 支持忽略static/transient修饰词的字段

### mybatis-plus
支持mybatis-plus版本3.2.0+
1. 支持全局配置tableUnderline、capitalMode
2. 支持忽略static/transient修饰词的字段
3. 支持@TableName注解
4. 支持@TableId、@TableField注解


## 不支持特性

### mapper
1. 不支持动态表名 IDynamicTableName
2. 不支持方法上使用注解, 即 enableMethodAnnotation 全局配置

## TODO    

### mapper   

### mybatis-plus   
1. 支持配置keepGlobalFormat
2. 支持配置tablePrefix
3. 支持配置columnFormat
4. 支持配置propertyFormat



## 使用说明
参考下面的wiki
- [gitee](https://gitee.com/thinwonton/mybatis-metamodel/blob/develop/doc/guide/index.md)
- [github](https://github.com/thinwonton/mybatis-metamodel/blob/develop/doc/guide/index.md)


## 鸣谢

感谢以下开源项目

- [hibernate](https://github.com/hibernate/hibernate-orm)
- [mybatis-plus](https://gitee.com/baomidou/mybatis-plus)
- [mapper](https://gitee.com/free/Mapper)
