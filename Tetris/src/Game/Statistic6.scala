// до пятницы (ссылка в заметках)...
































































package Game


object Statistic6 {


  def countFigureStatistic(unsortStatistic: Array[Int]): Int =
    unsortStatistic.groupBy(identity).mapValues(x => x.size).maxBy(x => x._2)._1


  def countScoreStatistic(unsortStatistic: Array[Int]): Double = {
    countSum(unsortStatistic, unsortStatistic.length - 1) / unsortStatistic.length
  }

  def countSum(array: Array[Int], index: Int): Int = {
    if (index == -1) 0
    else array(index) + countSum(array, index - 1);
  }

}



