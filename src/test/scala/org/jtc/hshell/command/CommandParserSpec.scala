package org.jtc.hshell.command

import org.scalatest.{Matchers, path}
import org.scalatest.TryValues._
import org.jtc.hshell.command.CommandParser.Command
import org.parboiled2.ParseError

class CommandParserSpec extends path.FunSpec with Matchers {
  describe("CommandParser") {
    def parse(input: String) = new CommandParser(input).CommandEntry.run().success.value

    it("parses a simple command") {
      parse("lcd") should be (Command("lcd", Nil, Nil))
    }

    it("parses a command with arguments") {
      parse("lcd /foo bar") should be (Command("lcd", Nil, List("/foo", "bar")))
    }

    it("parses a command with modifiers") {
      parse("lcd -foo -bar") should be (Command("lcd", List("foo", "bar"), Nil))
    }

    it("parses a command with both modifiers and arguments") {
      parse("lcd -foo -fizz /bar buzz") should be (Command("lcd", List("foo", "fizz"), List("/bar", "buzz")))
    }

    it("ignores white space") {
      parse("lcd      -foo       /bar      ") should be (Command("lcd", List("foo"), List("/bar")))
    }
  }
}
