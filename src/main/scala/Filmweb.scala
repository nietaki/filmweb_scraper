import java.text.SimpleDateFormat

import com.github.tototoshi.csv.{CSVReader, CSVWriter}

import scala.annotation.tailrec
import scala.collection.JavaConversions._
import org.jsoup._
import org.jsoup.nodes._
import org.jsoup.select.Elements
import Implicits._
import Utils._

object Filmweb {

  @tailrec
  def scrapeUrl(url: String): Seq[FilmwebFilm] = {
    try {
      val doc: Document = Jsoup.connect(url).get();
      val filmDivs = doc.select("div.filmContent").toList
      filmDivs.map { filmDiv =>
        //println(filmDiv)
        val titleLink = filmDiv.select("a.filmTitle").first()
        val titleUrl = titleLink.absUrl("href")
        val title = titleLink.text().trim()
        val origTitle = filmDiv.select("span.filmSubtitle").first().ownText().trim()
        val year = filmDiv.select("span.titleYear").first().text().digits
        val rating = filmDiv.select("span.rateBox").first().select("strong").first().ownText().digits
        val voteCount = filmDiv.select("div.box").first().ownText().digits

        FilmwebFilm(None, title, origTitle, titleUrl, myParseInt(year), myParseInt(rating), myParseInt(voteCount))
      }
    } catch {
      case _: HttpStatusException => Thread.sleep(1000); scrapeUrl(url);
      case _: java.net.SocketTimeoutException => Thread.sleep(1000); scrapeUrl(url);
    }
  }

  /**
   * returns pages sorted descending, by vote count (for now
   * @param pageNo page number
   * @return the constructed url
   */
  def constructUrl(pageNo: Int): String = {
    s"http://www.filmweb.pl/search/film?q=&type=&startYear=&endYear=&countryIds=&genreIds=&startRate=&endRate=&startCount=&endCount=&sort=COUNT&sortAscending=false&c=portal&page=$pageNo"
  }

  def getCurrentFilename(): String = {
    import java.util.Date
    val now = new Date();
    val datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
    s"res/$datetime.csv"
  }

  val lastPageNo = 5000

  def scrape() = {

    val writer = CSVWriter.open(getCurrentFilename(), append = true)
    var curId = 0
    (1 to lastPageNo).foreach { pageNo =>
      println()
      println(pageNo)
      println()
      val url = constructUrl(pageNo)
      val films = scrapeUrl(url).map { f =>
        val ret = f.copy(idOption = Some(curId))
        curId += 1 // dirty, I know
        ret
      }

      films.foreach(println(_))
      writer.writeAll(films.map(_.row))
    }
  }

  def readAllFromCsv(filename: String): Iterator[FilmwebFilm] = {
    val reader = CSVReader.open(filename)
    reader.iterator.map { stringSeq =>
      parseFromFields(stringSeq)
    }
  }

  def parseFromFields(ss: Seq[String]): FilmwebFilm = {
    FilmwebFilm(Some(myParseInt(ss(0))), ss(1), ss(2), ss(3), myParseInt(ss(4)), myParseInt(ss(5)), myParseInt(ss(6)))
  }
}
