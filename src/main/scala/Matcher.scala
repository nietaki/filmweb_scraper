/**
 * Created by nietaki on 10.01.15.
 */
object Matcher {
  lazy val imdbFilms = Imdb.parseRatingsWithIds("res/imdb/ratings.list").toArray
  lazy val filmwebFilms = Filmweb.readAllFromCsv("res/fil")
}
