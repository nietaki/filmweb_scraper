/**
 * Created by nietaki on 10.01.15.
 */
trait IdOptionalProduct extends Product {
  val idOption: Option[Int]

  def row: List[Any] = this.productIterator.toList match {
    case h :: tail => {
      id :: tail
    }
    case _ => ??? //should not happen
  }

  def id = idOption.getOrElse(-1)
}
