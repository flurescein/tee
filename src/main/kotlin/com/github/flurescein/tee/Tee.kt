package com.github.flurescein.tee

import java.io.File

private enum class Flags(val flag: String) {
    APPENDS_TO_THE_END("-a"),
    IGNORE_INTERRUPTS("-i")
}

fun main(args: Array<String>) {
    val (flags, files) = parseArguments(args)
    val input = readLine()

    println(input)
    writeToFiles(input, files, flags)
}

private fun parseArguments(arguments: Array<String>) =
        arguments.partition { Regex("-[a-zA-Z]").matches(it) }

private fun writeToFiles(input: String?, files: List<String>, flags: List<String>) {
    files.forEach {
        val file = File(it)

        try {
            if (flags.contains(Flags.APPENDS_TO_THE_END.flag)) {
                file.appendText(input.toString())
            } else {
                file.writeText(input.toString())
            }
        } catch (exception: Exception) {
            if (!flags.contains(Flags.IGNORE_INTERRUPTS.flag)) {
                throw InterruptedException("The utility's work was interrupted.")
            }
        }
    }
}
