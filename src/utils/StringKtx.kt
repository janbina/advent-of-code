package utils

fun String.indexOfOrNull(
    string: String,
    startIndex: Int = 0,
    ignoreCase: Boolean = false,
): Int? {
    val index = indexOf(
        string = string,
        startIndex = startIndex,
        ignoreCase = ignoreCase,
    )
    return if (index == -1) null else index
}
