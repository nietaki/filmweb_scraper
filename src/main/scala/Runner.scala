/**
 * Created by nietaki on 05.01.15.
 */
object Runner {
  def main(args: Array[String]): Unit = {
    //Filmweb.run()

    //Imdb.parseRatings("res/imdb/ratings.list").foreach(println(_))
    println(Imdb.parseRatings("res/imdb/ratings.list").toList.length)
    //Imdb.getFileLines("res/imdb/ratings.list").foreach(println(_))
  }
}
