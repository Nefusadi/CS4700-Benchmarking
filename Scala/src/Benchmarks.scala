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

  /** Dijkstra's Algorithm - Code taken from https://rosettacode.org/wiki/Dijkstra%27s_algorithm#Scala */

  type Path[Key] = (Double, List[Key])

    def Dijkstra[Key](lookup: Map[Key, List[(Double, Key)]], fringe: List[Path[Key]], dest: Key, visited: Set[Key]): Path[Key] = fringe match {
      case (dist, path) :: fringe_rest => path match {case key :: path_rest =>
        if (key == dest) (dist, path.reverse)
        else {
          val paths = lookup(key).flatMap {case (d, key) => if (!visited.contains(key)) List((dist + d, key :: path)) else Nil}
          val sorted_fringe = (paths ++ fringe_rest).sortWith {case ((d1, _), (d2, _)) => d1 < d2}
          Dijkstra(lookup, sorted_fringe, dest, visited + key)
        }
      }
      case Nil => (0, List())
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
    val started = System.currentTimeMillis()
    val res = block
    logPerf(System.currentTimeMillis - started)
    res
  }

    def main(args: Array[String]): Unit = {
      measure(fib(45))

      var filepath = "C:\\Users\\Shane\\Documents\\CS4700-Benchmarking\\Benchmarking\\array.txt"
      val list = readFileToList(filepath)
      val sorted = measure(mergeSort(list))

      /** from small graph */
      val lookup = Map(
        "a" -> List((3.0, "c"), (4.0, "e")),
        "b" -> List((2.0, "c"), (1.0, "d"), (1.0,"e")),
        "c" -> List((3.0, "a"), (2.0, "b"),(3.0,"e")),
        "d" -> List((1.0, "b"), (1.0,"e")),
        "e" -> List((4.0, "a"), (1.0, "b"), (3.0,"c"),(1.0,"d")),
        "f" -> Nil
      )
      val res = Dijkstra[String](lookup, List((0, List("a"))), "e", Set())
      measure(Dijkstra[String](lookup, List((0, List("a"))),"e",Set()))
      println(res)

    }
}
