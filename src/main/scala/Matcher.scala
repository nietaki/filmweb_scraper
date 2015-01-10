/**
 * Created by nietaki on 10.01.15.
 */
object Matcher {
  lazy val imdbFilms = Imdb.parseRatingsWithIds("res/imdb/ratings.list").toArray
  lazy val filmwebFilms = Filmweb.readAllFromCsv("res/filmweb.csv")

  lazy val imdbYearly = imdbFilms.groupBy(_.year)
  lazy val filmwebYearly = imdbFilms.groupBy(_.year)

  def getExactMatches() = {
    filmwebFilms.foreach {ff =>
      val imdbOfThisYear = imdbYearly.get(ff.year)
      val found = imdbOfThisYear match {
        case None => None//no films in this year
        case Some(arr) => {
          arr.find {imdbFilm =>
            val imdbTitle = Levenshtein.normalizeString(imdbFilm.title)
            val filmbwebTitle = Levenshtein.normalizeString(ff.title)
            val filmbwebOrigTitle = Levenshtein.normalizeString(ff.originalTitle)
            (imdbTitle == filmbwebTitle) || (imdbTitle == filmbwebOrigTitle)
          }
        }
      }
      val msg = found match {
        case Some(imdbFound) => s"${imdbFound.title} - imdb: ${imdbFound.rating}, filmweb: ${ff.rating}"
        case None => s"no match found for ${ff.title}"
      }
      println(msg)
    }
  }
}
