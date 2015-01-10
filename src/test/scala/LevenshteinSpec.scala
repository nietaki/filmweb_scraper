import org.specs2.mutable._
import Levenshtein._

class LevenshteinSpec extends Specification {
 
  "normalizing" should {
    "lowercase ascii chars" in {
      normalizeString("DERP") must be equalTo("derp")
    }

    "lowercase and ascii-cise non-ascii polish chars" in {
      normalizeString("ŻÓŁĆ") must be equalTo("zolc")
    }

    "Deal with a whole sentence, with hyphens, commas, spaces and docs" in {
      normalizeString("Zażółć, gęślą-jaźń!") must be equalTo("zazolc, gesla-jazn!")
    }
  }
}