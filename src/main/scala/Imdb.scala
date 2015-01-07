object Imdb {

  def isTvSeriesEpisode(line: String): Boolean = {
    line.contains("{")
  }

  def isVideoGame(line: String): Boolean = {
    line.contains("(VG)")
  }

  def stripNoise(line: String): String = {
    line.replace(" (TV)", "")
        .replace(" (VG)", "")
        .replace(" (V)", "")
        .trim()
  }

  val ratingRegex = new scala.util.matching.Regex("""^([0-9\.]{10})\s+(\d+)\s+(\d+\.\d)\s+(.*)\s+\((\S+)\)$""",
                                                  "distribution", "voteCount", "rating", "title", "year")

  def parseFilm(line: String): Option[ImdbFilm] = {

    def parseFilmInner(line: String): Option[ImdbFilm] = {
      import Implicits._
      val matches = ratingRegex.findFirstMatchIn(line)
      matches.map { m =>
        val voteCount = m.group("voteCount").toInt
        val rating = m.group("rating").digits.toInt
        val year = m.group("year").digits.toInt
        ImdbFilm(m.group("distribution"), voteCount, rating, m.group("title"), year)
      }
    }

    val cleaned = stripNoise(line)
    parseFilmInner(cleaned)
  }


}
