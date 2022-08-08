package tech.simter.util

/**
 * String util tools.
 *
 * @author RJ
 */
object StringUtils {
  enum class CaseType {
    LowerCase, UpperCase, Original
  }

  /** A regex for find the first upper-char in a word */
  private val caseRegex = Regex("([A-Z][a-z]+)")

  /**
   * Convert a camel-case string to an underscore string.
   *
   * Default return lower-case string. Use [caseType] to change it.
   *
   * Default examples:
   *
   * - "a" | "A"  to a
   * - "ABC"  to abc
   * - "ABCar"  to ab_car
   * - "myWork" | "MyWork"  to my_work
   * - "myOfficeWork" | "MyOfficeWork" to my_office_work
   */
  fun underscore(source: String, caseType: CaseType = CaseType.LowerCase): String {
    val underscore = caseRegex.replace(source.replaceFirstChar { it.lowercase() }) { "_${it.value}" }
    return when (caseType) {
      CaseType.LowerCase -> underscore.lowercase()
      CaseType.UpperCase -> underscore.uppercase()
      else -> underscore
    }
  }
}