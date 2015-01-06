import java.text.SimpleDateFormat

import com.github.tototoshi.csv.CSVWriter

import scala.annotation.tailrec
import scala.collection.JavaConversions._
import org.jsoup._
import org.jsoup.nodes._
import org.jsoup.select.Elements
import Implicits._

object Filmweb {

  @tailrec
  def scrapeUrl(url: String): Seq[Film] = {
    try {
      val doc: Document = Jsoup.connect(url).get();
      val filmDivs = doc.select("div.filmContent").toList
      filmDivs.map { filmDiv =>
        //println(filmDiv)
        val title = filmDiv.select("a.filmTitle").first().text().trim()
        val origTitle = filmDiv.select("span.filmSubtitle").first().ownText().trim()
        val year = filmDiv.select("span.titleYear").first().text().digits
        val rating = filmDiv.select("span.rateBox").first().select("strong").first().ownText().digits
        val voteCount = filmDiv.select("div.box").first().ownText().digits

        Film(title, origTitle, year.toInt, rating.toInt, voteCount.toInt)
      }
    } catch {
      case _: HttpStatusException => Thread.sleep(1000); scrapeUrl(url);
      case _: java.net.SocketTimeoutException => Thread.sleep(1000); scrapeUrl(url);
    }
  }

  val lastPageNo = 24335

  /**
   * returns pages sorted descending, by vote count (for now
   * @param pageNo page number
   * @return the constructed url
   */
  def constructUrl(pageNo: Int): String = {
    s"http://www.filmweb.pl/search/film?q=&type=&startYear=&endYear=&countryIds=&genreIds=&startRate=&endRate=&startCount=100&endCount=&sort=COUNT&sortAscending=false&c=portal&page=$pageNo"
  }

  def getCurrentFilename(): String = {
    import java.util.Date
    val now = new Date();
    val datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
    s"res/$datetime.csv"
  }

  def run() = {

    val writer = CSVWriter.open(getCurrentFilename(), append = true)

    (1 to 10).foreach {pageNo =>
      val url = constructUrl(pageNo)
      val films = scrapeUrl(url)
      films.foreach(println(_))
      writer.writeAll(films.map(_.row))
    }
  }
}
