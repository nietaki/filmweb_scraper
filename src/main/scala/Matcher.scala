/**
 * Created by nietaki on 10.01.15.
 */
object Matcher {
  lazy val imdbFilms = Imdb.parseRatingsWithIds("res/imdb/ratings.list").toArray
  lazy val filmwebFilms = Filmweb.readAllFromCsv("res/filmweb.csv")

  lazy val imdbYearly = imdbFilms.groupBy(_.year)
  lazy val filmwebYearly = imdbFilms.groupBy(_.year)


  def exactMatcher(s1: String, s2: String) = s1 == s2

  // doesn't work too well, will not be used
  def similarityMatcher(minSimilarity: Double) = { (s1: String, s2: String) => {
      Levenshtein.heuristicSimilarity2(s1, s2) >= minSimilarity
    }
  }


  def getMatches(matcher: (String, String) => Boolean) = {
    filmwebFilms.foreach {ff =>
      val imdbOfThisYear = imdbYearly.get(ff.year)
      val found = imdbOfThisYear match {
        case None => None//no films in this year
        case Some(arr) => {
          arr.find {imdbFilm =>
            val imdbTitle = Levenshtein.normalizeString(imdbFilm.title)
            val filmwebTitle = Levenshtein.normalizeString(ff.title)
            val filmwebOriginalTitle = Levenshtein.normalizeString(ff.originalTitle)
            (matcher(imdbTitle, filmwebTitle) || matcher(imdbTitle, filmwebOriginalTitle))
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
