package aoc25

import Day
import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver
import solveDay
import utils.aStarSearch
import java.io.BufferedReader
import kotlin.math.roundToLong

fun main() { solveDay(10, 2025) }

class Day10(
    input: BufferedReader,
) : Day<Long, Long> {

    private val machines = input.readLines().map { line ->
        val target = line.substringAfter("[").substringBefore("]")
            .mapIndexedNotNullTo(HashSet()) { i, c -> if (c == '#') i else null }
        val buttons = line.substringAfter("] ").substringBefore(" {").split(" ").map { buttonStr ->
            buttonStr.drop(1).dropLast(1).split(",").mapTo(HashSet()) { it.toInt() }
        }.sortedByDescending { it.size }
        val targetJoltage = line.substringAfter("{").substringBefore("}").split(",").map { it.toInt() }
        Machine(target, buttons, targetJoltage)
    }

    private class Machine(
        val target: Set<Int>,
        val buttons: List<Set<Int>>,
        val targetJoltage: List<Int>,
    ) {
        fun minButtonPresses(): Int {
            return aStarSearch<Set<Int>>(
                start = emptySet(),
                isEnd = { it == target },
                next = { node ->
                    buttons.map { button ->
                        ((node - button) + (button - node)) to 1
                    }
                },
                heuristicCostToEnd = { 0 },
            )?.cost ?: error("cannot find")
        }

        fun minJoltageButtonPresses(): Long {
            val solver = MPSolver.createSolver("SCIP")
            val variables = solver.makeIntVarArray(buttons.size, 0.0, 100000000.0)
            for (i in targetJoltage.indices) {
                val joltage = targetJoltage[i]
                solver.makeConstraint(joltage.toDouble(), joltage.toDouble()).apply {
                    for (bi in buttons.indices) {
                        if (i in buttons[bi]) {
                            setCoefficient(variables[bi], 1.0)
                        }
                    }
                }
            }

            val objective = solver.objective().apply {
                for (i in variables.indices) {
                    setCoefficient(variables[i], 1.0)
                }
                minimization()
            }

            val resultStatus = solver.solve()

            if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
                return objective.value().roundToLong()
            } else {
                error("No optimal solution found. ${objective.value()}")
            }
        }
    }

    override fun part1(): Long {
        return machines.sumOf { it.minButtonPresses() }.toLong()
    }

    override fun part2(): Long {
        Loader.loadNativeLibraries()
        return machines.sumOf { it.minJoltageButtonPresses() }
    }
}
