case class Film(title: String, originalTitle: String, year: Int, rating: Int, voteCount: Int) {
  def row: Seq[Any] = Seq(title, originalTitle, year, rating, voteCount)
}
