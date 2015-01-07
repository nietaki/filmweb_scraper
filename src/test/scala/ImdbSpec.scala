import org.specs2.mutable._

class ImdbSpec extends Specification {
 
  "sample" should {
    "work" in {
      0.0 must be closeTo(0.0, 0.1)
    }
  }

  "Imdb.parse" should {
    "parse a sample line" in {
      val line = "0...002120      23   7.4  Cities on Speed: Mumbai Disconnected (2009)"
      val matchOption = Imdb.parseFilm(line)
      matchOption must not beNone
      val film = matchOption.get
      film.distribution must be equalTo("0...002120")
      film.voteCount must be equalTo(23)
      film.rating must be equalTo(74)
      film.title must be equalTo("Cities on Speed: Mumbai Disconnected")
      film.year must be equalTo(2009)
    }
  }

}