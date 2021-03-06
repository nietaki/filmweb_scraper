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

    //Matcher.saveMatches()
    //Matcher.matches.foreach(println(_))

    //Analysis.saveToCsv(Matcher.imdbFilms.map(_.row), "imdbFilms")
    //Analysis.saveToCsv(Matcher.filmwebFilms.map(_.row), "filmwebFilms")

    //Analysis.saveToCsv(Matcher.matches.map{case (filmweb, imdb) =>
    //  filmweb.row ++ imdb.row
    //}, "joinedFilms")

    //Analysis.saveToCsv(Analysis.heatMapData, "heatmap")
    //println(Analysis.heatMapData.map(_.max).max)

    Analysis.mostDifferent

    Analysis.saveToCsv(Analysis.byYear, "by_year")
    Analysis.saveToCsv(Analysis.averageYearly, "averages_yearly")

  }
}
