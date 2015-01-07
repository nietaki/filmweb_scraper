case class FilmwebFilm(title: String, originalTitle: String, url: String, year: Int, rating: Int, voteCount: Int) {
  def row: Seq[Any] = productIterator.toList
}
