/**
 * Created by nietaki on 05.01.15.
 */
object Runner {
  def main(args: Array[String]): Unit = {
    //Filmweb.scrape()

    //Imdb.parseRatings("res/imdb/ratings.list").foreach(println(_))
    //println(Imdb.parseRatings("res/imdb/ratings.list").toList.length)
    //Imdb.getFileLines("res/imdb/ratings.list").foreach(println(_))
    //Imdb.parseRatingsWithIds("res/imdb/ratings.list").foreach(println(_))

    //Matcher.filmwebFilms.take(10).foreach(println(_))
    //Matcher.imdbFilms.take(10).foreach(println(_))

    Matcher.getExactMatches
  }
}
