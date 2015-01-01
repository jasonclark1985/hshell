import org.parboiled2._
import CharPredicate._

object CommandParser {
  case class ParsedCommand(command: String, modifiers: Option[Seq[String]], arguments: Option[Seq[String]])
}

class CommandParser(val input: ParserInput) extends Parser {
  import CommandParser._

  def Command = rule { commandName ~ OptionalSpace ~ optional(modifiers) ~ OptionalSpace ~ optional(arguments) ~ EOI ~> ParsedCommand }

  def commandName = rule { capture(oneOrMore(AlphaNum)) ~> (_.toString.replace(" ", "")) }

  def modifiers = rule { oneOrMore(ch('-') ~ capture(characterString)).separatedBy(Space) }

  def arguments = rule { oneOrMore(capture(characterString)).separatedBy(Space) }

  def characterString = rule { oneOrMore(AlphaNum) }

  def ws(c: Char) = rule { c ~ WhiteSpace }

  def WhiteSpace = rule { zeroOrMore(WhiteSpaceChar) }

  def Space = rule { ch(' ') }

  def OptionalSpace = rule { optional(Space) }

  val WhiteSpaceChar = CharPredicate(" \n\r\t\f")

}