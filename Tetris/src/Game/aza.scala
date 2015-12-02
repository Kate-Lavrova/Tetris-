// глянуть на суберфоруме функцию
//
//
//
//




























/*
Функция на Scala:


object MySort {

  //@scala.annotation.tailrec
  def sort(xs : Array[Int], count : Int, current : Int): Array[Int] = {

    if( xs(current) < xs(current-1) )
    {
      val reversed = (xs(current - 1), xs(current))
      xs(current) = reversed._1
      xs(current - 1) = reversed._2
    }

    if(current >= xs.length-1)
    {
      if(count+1 <= xs.length) sort(xs, count+1, 1)
      else xs

    }else sort(xs, count, current+1)

  }
}

*/









package Game

/**
 * Created by Kate on 03.06.2015.
 */
object aza {
  def sort(array: Array[Int]): Array[Int] = {
    if (array.length <= 1) array
    else {
      val pivot = array(array.length / 2)
      Array.concat(
        sort(array filter (pivot >)),
        array filter (pivot ==),
        sort(array filter (pivot <)))
    }
  }
}




/*
package Game

/**
 * Created by SONY on 15.06.2015.
 */
object sort {
  def qsort(list:List[Int]):List[Int]=list match
  {
    case head::tail=>{qsort(tail filter(head>=)):::head::qsort(tail filter(head<))}
    case _=>list;
  }

}




*/



































