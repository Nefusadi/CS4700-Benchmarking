/**
  * Created by Shane on 3/12/2017.
  */

import java.io.File

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

  /** Dijkstra's Algorithm - Code from https://rosettacode.org/wiki/Dijkstra%27s_algorithm#Scala */

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



  def readFileToMapOfLists(file: String): Map[String , List[(Double, String)]] =
  {
    val lines = fromFile(file).getLines
    var mapForReturn: Map[String , List[(Double, String)]] = Map()
    var index = 0
    for(line <- lines)
    {
      var rowList = new ListBuffer[(Double, String)]
      val splited = line.split(" +")
        for(i <- 0 to splited.size - 1)
          {
            val weight = splited(i).toDouble
            if(weight != 0) {
              rowList += ((weight, i.toString))
            }
          }
      mapForReturn.+=(index.toString ->rowList.toList)
      index = index +1
    }
    return mapForReturn
  }

  private def logPerf(elapsed: Long) = {
    println("The execution took: " + elapsed + " microseconds.")
  }

  def measure[T](block: => T): T = {
    val started = System.nanoTime()/1000
    val res = block
    logPerf(System.nanoTime()/1000 - started)
    res
  }

    def main(args: Array[String]): Unit = {

      var curDir = System.getProperty("user.dir")
      var path = new File(curDir)
      var parent = path.getParent()

      measure(fib(20))

      var filepath = parent + File.separator + "Benchmarking" + File.separator + "array.txt"
      val list = readFileToList(filepath)
      val sorted = measure(mergeSort(list))

      var graph = parent+ File.separator + "Benchmarking" + File.separator + "graph.txt"

      val largeMap = readFileToMapOfLists(graph)

      val resLar = Dijkstra[String](largeMap,List((0, List("0"))), "99", Set());
      measure(Dijkstra[String](largeMap,List((0, List("0"))), "99", Set()))
      println(resLar);

    }


}
