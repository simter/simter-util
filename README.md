# [simter-util](https://github.com/simter/simter-util) [[中文]]

Some util tools. Includes:

- BeanUtils.assign : similar to JavaScript `Object.assign` method
- XPathUtils : find xml node by standard xpath

## Installation

```xml
<dependency>
  <groupId>tech.simter</groupId>
  <artifactId>simter-util</artifactId>
  <version>0.3.0</version>
</dependency>
```

## Requirement

- Java 8+
- ModelMapper 1+

## Usage - BeanUtils.assign

### Convert PO to DTO or reverse

```java
UserPo po = new UserPo();
// set po property value
...
// convert to DTO instance
UserDto dto = BeanUtils.assign(UserDto.class, po);
```

If the DTO has the same property from multiple po, code should be like this:

```java
UserDto dto = BeanUtils.assign(UserDto.class, po1, po2, ...);
```

The latter po property value will override the previous po property value.

### Copy property value to target instance object

```java
UserPo po = new UserPo();
// set po property value
...
// instance target instance
UserDto dto = new UserDto();

// copy po property value to the dto property
BeanUtils.assign(dto, po);
```

If the DTO has the same property from multiple po, code should be like this:

```java
BeanUtils.assign(dto, po1, po2, ...);
```

The latter po property value will override the previous po property value.

## Usage - XPathUtils

TODO.

## Build

```bash
mvn clean package
```


## Deploy

First take a look at [simter-parent] deploy config.

### Deploy to LAN Nexus Repository

```bash
mvn clean deploy -P lan
```

### Deploy to Sonatype Repository

```bash
mvn clean deploy -P sonatype
```

After deployed, login into <https://oss.sonatype.org>. Through `Staging Repositories`, search this package,
then close and release it. After couple hours, it will be synced
to [Maven Central Repository](https://search.maven.org/#search%7Cga%7C1%7Ca%3A%22simter-util%22).

### Deploy to Bintray Repository

```bash
mvn clean deploy -P bintray
```

Will deploy to `https://api.bintray.com/maven/simter/maven/tech.simter:simter-util/;publish=1`.
So first create a package `https://bintray.com/simter/maven/tech.simter:simter-util` on Bintray.
After deployed, check it from <https://jcenter.bintray.com/tech/simter/simter-util>.


[simter-parent]: https://github.com/simter/simter-parent
[中文]: https://github.com/simter/simter-util/blob/master/docs/README.zh-cn.md