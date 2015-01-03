package org.jtc.hshell.command

import org.parboiled2._
import CharPredicate._

object CommandParser {
  case class Command(command: String, modifiers: Seq[String] = Nil, arguments: Seq[String] = Nil)
}

class CommandParser(val input: ParserInput) extends Parser {
  import CommandParser._

  val WhiteSpaceChar = CharPredicate(" \n\r\t\f")

  def CommandEntry: Rule1[Command] = rule { commandName ~ OptionalSpace ~ modifiers ~ OptionalSpace ~ arguments ~ EOI ~> Command }

  def commandName = rule { capture(oneOrMore(AlphaNum)) ~> (_.toString.replace(" ", "")) }

  def modifiers = rule { zeroOrMore(ch('-') ~ capture(AlphaNumericString)).separatedBy(WhiteSpace) }

  def arguments = rule { zeroOrMore(capture(CharacterString)).separatedBy(WhiteSpace) }

  def AlphaNumericString = rule { oneOrMore(AlphaNum) }

  def CharacterString = rule { oneOrMore(Visible) }

  def WhiteSpace = rule { zeroOrMore(WhiteSpaceChar) }

  def OptionalSpace = rule { optional(ch(' ')) }

}