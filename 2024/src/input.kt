import java.io.File
import java.net.URI

/**
 * Get input for a day
 */
fun getDayInputFile(
    day: Int,
    year: Int,
    forceDownload: Boolean = false,
): Result<File> {
    File("input").mkdirs()
    val file = File("input/day${day.toString().padStart(2, '0')}.txt")

    if (file.exists() && !forceDownload) {
        return Result.success(file)
    }

    val session = runCatching { File(".session").readText().trim() }.getOrElse { "" }
    if (session.isBlank()) {
        return Result.failure(IllegalStateException("Missing session cookie"))
    }

    println("Downloading input file for day $day")

    val url = URI.create("https://adventofcode.com/$year/day/$day/input").toURL()
    val connection = url.openConnection().apply {
        setRequestProperty("User-Agent", "github.com/janbina")
        setRequestProperty("Cookie", "session=$session")
    }

    val input = connection.inputStream
    val output = file.outputStream()

    return try {
        input.copyTo(output)
        Result.success(file)
    } catch (t: Throwable) {
        Result.failure(t)
    } finally {
        input.close()
        output.close()
    }
}
