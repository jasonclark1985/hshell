package org.jtc.hshell.command

import org.scalatest.{Matchers, path}
import org.scalatest.TryValues._
import org.jtc.hshell.command.CommandParser.Command

class CommandParserSpec extends path.FunSpec with Matchers {
  describe("CommandParser") {
    def parse(input: String) = new CommandParser(input).CommandEntry.run().success.value

    it("parses a simple command") {
      parse("lcd") should be (Command("lcd", Nil, Nil))
    }

    it("parses a command with arguments") {
      parse("lcd /foo") should be (Command("lcd", Nil, List("/foo")))
    }

    it("parses a command with modifiers") {
      parse("lcd -foo") should be (Command("lcd", List("foo"), Nil))
    }

    it("parses a command with both modifiers and arguments") {
      parse("lcd -foo /bar") should be (Command("lcd", List("foo"), List("/bar")))
    }

    it("ignores white space") {
      parse("lcd      -foo       /bar      ") should be (Command("lcd", List("foo"), List("/bar")))
    }
  }
}
