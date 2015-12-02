

package Game

import scala.collection.JavaConversions
import scala.collection.JavaConverters._


object sem {

  //передаём список из ТТТ и возвращаем его
  def findMostCommonSequence[T](list: java.util.ArrayList[T]): (T, T, T) = {
    val unformedList = list.asScala.toList //Превращаем лист джавы в лист скала
    countMostCommonSequence(formSequenceList(unformedList))
  }

  def formSequenceList[T](unformedList: List[T]): List[(T, T, T)] = {

    def formSequenceListLoop[T](unformedList: List[T], formedList: List[(T, T, T)]): List[(T, T, T)] = {
      formSequence(unformedList) match {
        case None => formedList
        case Some(sequence) => unformedList match {
          case a :: more => formSequenceListLoop(more, sequence :: formedList)
        }
      }
    }
    formSequenceListLoop(unformedList, List())
  }

  def formSequence[T](list: List[T]): Option[(T, T, T)] = list match {
    case a :: b :: c :: more => Some((a, b, c)) //Смотрим из чего состоит список
    case _ => None
  }


  def countMostCommonSequence[T](unsortStatistic: List[(T, T, T)]): (T, T, T) =
    unsortStatistic.groupBy(identity).mapValues(x => x.size).maxBy(x => x._2)._1
}
