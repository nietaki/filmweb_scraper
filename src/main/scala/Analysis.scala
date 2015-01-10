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
  def saveToDatFile(data: Seq[Seq[Any]], filename: String): Unit = {
    val writer = CSVWriter.open(s"data/$filename.dat")(spaceFormat)
    writer.writeAll(data)
  }

  def getHeatMapData = {
    for (
      fwScore <- (10 to 100);
      imdbScore <- (10 to 100)
    ) yield {
      val count = Matcher.matches.count { case(filmwebFilm, imdbFilm) =>
        filmwebFilm.rating == fwScore && imdbFilm.rating == imdbScore
      }
      Seq(fwScore, imdbScore, count)
    }
  }
}
