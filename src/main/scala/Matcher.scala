import com.github.tototoshi.csv.{CSVReader, CSVWriter}

/**
 * Created by nietaki on 10.01.15.
 */
object Matcher {
  lazy val imdbFilms = Imdb.parseRatingsWithIds("res/imdb/ratings.list").toArray
  lazy val filmwebFilms = Filmweb.readAllFromCsv("res/filmweb.csv").toArray

  lazy val imdbYearly = imdbFilms.groupBy(_.year)
  lazy val filmwebYearly = imdbFilms.groupBy(_.year)


  def exactMatcher(s1: String, s2: String) = s1 == s2

  // doesn't work too well, will not be used
  def similarityMatcher(minSimilarity: Double) = { (s1: String, s2: String) => {
      Levenshtein.heuristicSimilarity2(s1, s2) >= minSimilarity
    }
  }

  def getMatches(matcher: (String, String) => Boolean): Seq[(Int, Int)] = {
    filmwebFilms.map {ff =>
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
      found.map { imdbFilm =>
        (ff.id, imdbFilm.id)
      }
    }.filter(_.isDefined).map(_.get)
  }

  def saveMatches() = {
    val matches = getMatches(exactMatcher)
    val writer = CSVWriter.open("res/matches.csv")
    writer.writeAll(matches.map{ _.productIterator.toSeq})
  }

  def getMatches(): Seq[(FilmwebFilm, ImdbFilm)] = {
    val reader = CSVReader.open("res/matches.csv")
    val lines = reader.iterator.map{ seq => seq.map {_.toInt}}
    val films = lines.map{ line =>
      val ff = filmwebFilms(line(0))
      val imdb  = imdbFilms(line(1))
      assert(ff.year == imdb.year)
      (ff, imdb)
    }
    films.toSeq
  }
}
