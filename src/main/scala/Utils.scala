/**
 * Created by nietaki on 10.01.15.
 */
object Utils {
  def myParseInt(s: String): Int= {
    try {
      s.toInt
    } catch {
      case _: Exception => -1
    }
  }
}
