package tech.simter.util;

import org.modelmapper.ModelMapper;

/**
 * Bean property copy util tools.
 *
 * @author RJ
 */
public class BeanUtils {
  private static ModelMapper mapper;

  static {
    // initial {@link ModelMapper} instance
    mapper = new ModelMapper();
    mapper.getConfiguration().setFieldMatchingEnabled(true);
  }

  /**
   * Get the default {@link ModelMapper} instance,
   * <p>
   * <code>mapper.getConfiguration().setFieldMatchingEnabled(true)</code>
   *
   * @return {@link ModelMapper} instance
   */
  public static ModelMapper getDefaultMapper() {
    return mapper;
  }

  /**
   * Maps {@code sources} to an instance of {@code targetType}.
   *
   * @param targetType targetType type
   * @param sources    objects to copy from
   * @param <T>        type to copy to
   * @return fully mapped instance of {@code targetType}
   * @see ModelMapper#map(Object, Class) and ModelMapper#copy(Object, Object)
   */
  public static <T> T assign(Class<T> targetType, Object... sources) {
    if (sources == null || sources.length == 0) return null;

    T target = null;
    for (int i = 0; i < sources.length; i++) {
      if (i == 0) target = mapper.map(sources[i], targetType);
      else mapper.map(sources[i], target);
    }
    return target;
  }

  /**
   * Maps {@code sources} to a target instance.
   *
   * @param target  target instance
   * @param sources objects to copy from
   * @see ModelMapper#map(Object, Object)
   */
  public static void assign(Object target, Object... sources) {
    if (target == null || sources == null || sources.length == 0) return;
    for (Object source : sources) mapper.map(source, target);
  }
}