/**
 * Created by nietaki on 05.01.15.
 */
object Implicits {

  implicit def richString (s: String) = new {
    def digits = s.filter(c => c >= '0' && c <= '9')
  }

}
