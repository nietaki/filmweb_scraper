/**
 * Created by nietaki on 07.01.15.
 */
case class ImdbFilm(idOption: Option[Int], distribution: String, voteCount: Int, rating: Int, title: String, year: Int) {
  def row: List[Any] = this.productIterator.toList match {
    case h :: tail => {
      id :: tail
    }
    case _ => ??? //should not happen
  }

  def id = idOption.getOrElse(-1)
}
