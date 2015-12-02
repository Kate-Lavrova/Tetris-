package Game

/**
   * Created by SONY on 15.06.2015.
   */
  object sorting {
  def qsort(list:List[Int]):List[Int]=list match

    {
      case head::tail=>{qsort(tail filter(head>=)):::head::qsort(tail filter(head<))}
      case _=>list;
    }

  }

