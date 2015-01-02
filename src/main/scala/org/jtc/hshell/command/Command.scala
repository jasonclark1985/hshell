package org.jtc.hshell.command

import org.parboiled2._
import CharPredicate._

object CommandParser {
  case class Command(command: String, modifiers: Seq[String], arguments: Seq[String])
}

class CommandParser(val input: ParserInput) extends Parser {
  import CommandParser._

  val WhiteSpaceChar = CharPredicate(" \n\r\t\f")

  def CommandEntry: Rule1[Command] = rule { commandName ~ OptionalSpace ~ modifiers ~ OptionalSpace ~ arguments ~ EOI ~> Command }

  def commandName = rule { capture(oneOrMore(AlphaNum)) ~> (_.toString.replace(" ", "")) }

  def modifiers = rule { zeroOrMore(WhiteSpace ~ ch('-') ~ capture(AlphaNumericString) ~ WhiteSpace).separatedBy(Space) }

  def arguments = rule { zeroOrMore(WhiteSpace ~ capture(CharacterString) ~ WhiteSpace).separatedBy(Space) }

  def AlphaNumericString = rule { oneOrMore(AlphaNum) }

  def CharacterString = rule { oneOrMore(Visible) }

  def WhiteSpace = rule { zeroOrMore(WhiteSpaceChar) }

  def Space = rule { ch(' ') }

  def OptionalSpace = rule { optional(Space) }

}