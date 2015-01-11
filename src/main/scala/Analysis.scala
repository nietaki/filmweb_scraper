import com.github.tototoshi.csv.{DefaultCSVFormat, CSVWriter}

/**
 * Created by nietaki on 10.01.15.
 */
object Analysis {

  object spaceFormat extends DefaultCSVFormat {
    override val delimiter = ' '
  }

  /**
   * @param data sequence of rows containing data
   * @param filename filename to be saved to
   */
  def saveToCsv(data: Seq[Seq[Any]], filename: String): Unit = {
    val writer = CSVWriter.open(s"data/$filename.csv")
    writer.writeAll(data)
  }

  /*
  filmweb is the outer loop, hence y axis
   */
  lazy val heatMapData = {
    for (
      fwScore <- (10 to 100)
    ) yield {
      (10 to 100).map{imdbScore =>
        val result = Matcher.matches.count { case(filmwebFilm, imdbFilm) =>
          filmwebFilm.rating == fwScore && imdbFilm.rating == imdbScore
        }
        math.log1p(result)
      }
    }
  }

  def mostDifferent = {
    val sorted = Matcher.matches.filter{ pr =>
      pr._1.voteCount >= 100
    }.sortBy{ pr =>
      val fw = pr._1
      val im = pr._2
      -math.abs(fw.rating - im.rating)
    }.take(100)

    sorted.foreach{ pr =>
      val fw = pr._1
      val im = pr._2
      val diff = fw.rating - im.rating

      println(s"$diff: imdb_title: ${im.title} (${im.year}), filmweb_url: ${fw.url}, filmweb rating: ${fw.rating}, imdb rating: ${im.rating}") // TODO url
    }

  }
}
