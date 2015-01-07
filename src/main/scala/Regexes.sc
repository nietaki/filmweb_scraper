
val r = """^([0-9\.]{10})\s+(\d+)\s+(\d+\.\d)\s+(.*)\s+\((\S+)\)$""".r

val res = r.findFirstMatchIn("1....1..16       9   8.4  Citius, altius, fortius (2005)")
val found = res.get
found.subgroups
