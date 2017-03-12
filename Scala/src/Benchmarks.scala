/**
  * Created by Shane on 3/12/2017.
  */

import scala.collection.mutable.ListBuffer
import scala.io.Source._

object Benchmarks {

  /** Merge Sort - Code from https://rosettacode.org/wiki/Sorting_algorithms/Merge_sort#Scala*/
  def mergeSort(input: List[Int]) = {
    def merge(left: List[Int], right: List[Int]): Stream[Int] = (left, right) match {
      case (x :: xs, y :: ys) if x <= y => x #:: merge(xs, right)
      case (x :: xs, y :: ys) => y #:: merge(left, ys)
      case _ => if (left.isEmpty) right.toStream else left.toStream
    }
    def sort(input: List[Int], length: Int): List[Int] = input match {
      case Nil | List(_) => input
      case _ =>
        val middle = length / 2
        val (left, right) = input splitAt middle
        merge(sort(left, middle), sort(right, middle + length % 2)).toList
    }
    sort(input, input.length)
  }

  /** Recursive Fib - Code from https://rosettacode.org/wiki/Fibonacci_sequence#Scala*/
  def fib(i: Int): Int = i match {
    case 0 => 0
    case 1 => 1
    case _ => fib(i - 1) + fib(i - 2)
  }

  def readFileToList(file: String): List[Int] =  {
    val lines = fromFile(file).getLines
    var list = new ListBuffer[Int]()
    for(elem <- lines)
      {
        val inted = elem.toInt
        list += inted
      }
    val toReturn = list.toList
    return toReturn
  }

  private def logPerf(elapsed: Long) = {
    println("The execution took: " + elapsed)
  }

  def measure[T](block: => T): T = {
    val started = System.currentTimeMillis
    val res = block
    logPerf(System.currentTimeMillis - started)
    res
  }

    def main(args: Array[String]): Unit = {
      val fibPer = measure(fib(45))

      var filepath = "C:\\Users\\Shane\\Documents\\CS4700-Benchmarking\\Benchmarking\\array.txt"
      val list = readFileToList(filepath)
      measure(mergeSort(list))
    }
}
