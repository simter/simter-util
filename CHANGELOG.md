# simter-util changelog

## 2.0.0 - 2020-11-19

- Upgrade to simter-dependencies-2.0.0

## 2.0.0-M1 - 2020-06-02

- Upgrade to simter-dependencies-2.0.0-M1

## 1.2.0-M3 - 2020-04-15

- Add new method `BeanUtils.diff`
- Add new method `RandomUtils.randomBigDecimal(from, until, scale)`
- Add new method `RandomUtils.randomLong(from, until)`
- Add len param to `RandomUtils.randomString(len, prefix)`
- Avoid conflict on `RandomUtils.nextInt()` and `.nextLong()`
- Refactor `RandomUtils.randomInt(start: Int = 0, end: Int = 9): Int` to `randomInt(from: Int = 0, until: Int = 100): Int`
- Upgrade to simter-dependencies-1.3.0-M14

## 1.2.0-M2 - 2020-02-11

- Upgrade to simter-dependencies-1.3.0-M13

## 1.2.0-M1 - 2020-01-08

- Upgrade to simter-dependencies-1.3.0-M11
- Support nullable javatime

## 1.1.0 - 2019-07-03

- Use JUnit5|AssertJ instead of JUnit4|Hamcrest
- Change parent to simter-dependencies-1.2.0
- Add `RandomUtils.nextLong|nextInt` method
- Add `AssertUtils.assertSamePropertyHasSameValue` method [#1](https://github.com/simter/simter-util/issues/1)
    > Mostly for unit test to assert two bean instance same name property has same property value.
    > If same name property has diff type or value, throw `AssertionError`.
- Add `StringUtils.underscore` method

## 1.0.0 - 2019-01-08

- Convert to kotlin module
- Upgrade test to junit5
- Add RandomUtils

## 0.5.0 - 2018-01-05

- Just align version

## 0.4.0 - 2018-01-05

- Just centralize-version

## 0.3.0 - 2017-12-12

- Add BeanUtils.assign (similar to JavaScript `Object.assign` method)
- Add XPathUtils to find xml node with standard xpath