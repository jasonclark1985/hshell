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

  def modifiers = rule { zeroOrMore(ch('-') ~ capture(characterString)).separatedBy(Space) }

  def arguments = rule { zeroOrMore(capture(characterString)).separatedBy(Space) }

  def characterString = rule { oneOrMore(AlphaNum) }

  def WhiteSpace = rule { zeroOrMore(WhiteSpaceChar) }

  def Space = rule { ch(' ') }

  def OptionalSpace = rule { optional(Space) }

}