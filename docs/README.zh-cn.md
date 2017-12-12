# [simter-util](https://github.com/simter/simter-util) [[English]]

一些工具方法，包括：

- BeanUtils.assign : 类似于 JavaScript 的 `Object.assign` 方法
- XPathUtils : 使用标准的 xpath 查找 xml 节点


## 安装

```xml
<dependency>
  <groupId>tech.simter</groupId>
  <artifactId>simter-util</artifactId>
  <version>0.3.0</version>
</dependency>
```

## 要求

- Java 8+
- ModelMapper 1+

## 使用 - BeanUtils.assign

### 转换 PO 为 DTO 或者反过来

```java
UserPo po = new UserPo();
// 设置 po 的属性值
...
// 转换为 DTO 实例
UserDto dto = BeanUtils.assign(UserDto.class, po);
```

如果 DTO 的属性混合了多个 PO 的属性，代码则类似如下：

```java
UserDto dto = BeanUtils.assign(UserDto.class, po1, po2, ...);
```

后面的 PO 属性值将覆盖前面的 PO 属性值。

### 复制属性值

```java
UserPo po = new UserPo();
// 设置 po 的属性值
...
// 创建目标实例对象
UserDto dto = new UserDto();

// 复制属性值
BeanUtils.assign(dto, po);
```

如果 DTO 的属性混合了多个 PO 的属性，代码则类似如下：

```java
BeanUtils.assign(dto, po1, po2, ...);
```

后面的 PO 属性值将覆盖前面的 PO 属性值。

## 使用 - XPathUtils

TODO.

## 构建

```bash
mvn clean package
```

## 发布

请先查看 [simter-parent] 的发布配置说明。

### 发布到局域网 Nexus 仓库

```bash
mvn clean deploy -P lan
```

### 发布到 Sonatype 仓库

```bash
mvn clean deploy -P sonatype
```

发布成功后登陆到 <https://oss.sonatype.org>，在 `Staging Repositories` 找到这个包，然后将其 close 和 release。
过几个小时后，就会自动同步到 [Maven 中心仓库](https://search.maven.org/#search%7Cga%7C1%7Ca%3A%22simter-util%22) 了。

### 发布到 Bintray 仓库

```bash
mvn clean deploy -P bintray
```

发布之前要先在 Bintray 创建 package `https://bintray.com/simter/maven/tech.simter:simter-util`。
发布到的地址为 `https://api.bintray.com/maven/simter/maven/tech.simter:simter-util/;publish=1`。
发布成功后可以到 <https://jcenter.bintray.com/tech/simter/simter-util> 检查一下结果。


[simter-parent]: https://github.com/simter/simter-parent/blob/master/docs/README.zh-cn.md
[English]: https://github.com/simter/simter-util/blob/master/README.md