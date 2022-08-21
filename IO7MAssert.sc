/*
 * Copyright © 2022 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

IO7MAssertionError : MethodError
{
  var <>expected;
  var <>received;
  var <>kind;

  *new
  {
    arg receiver, expected, received, kind;

    ^super.new(nil, receiver)
      .expected_(expected)
      .received_(received)
      .kind_(kind);
  }

  errorString
  {
    ^kind ++ " VIOLATION:\n"
     ++ "Expected: " ++ this.expected ++ "\n"
     ++ "Received: " ++ this.received ++ "\n";
  }
}

IO7MAssertions
{
  *prCheck
  {
    arg expression, condition, message, kind;

    if (condition.value == false, {
      IO7MAssertionError.new (IO7MAssertions, message, expression, kind).throw;
    });

    ^expression;
  }

  *checkAssertion
  {
    arg expression, condition, message;
    this.prCheck(expression, condition, message, "ASSERTION");
  }

  *checkInvariant
  {
    arg expression, condition, message;
    this.prCheck(expression, condition, message, "INVARIANT");
  }

  *checkPrecondition
  {
    arg expression, condition, message;
    this.prCheck(expression, condition, message, "PRECONDITION");
  }

  *checkPostcondition
  {
    arg expression, condition, message;
    this.prCheck(expression, condition, message, "POSTCONDITION");
  }
}

IO7MAssertionsTest : UnitTest
{
  test_AssertionOK
  {
    var x = 1;
    IO7MAssertions.checkAssertion(x, x > 0, "Input must be > 0");
  }

  test_AssertionNotOK
  {
    var x = -2;

    this.assertException({
      IO7MAssertions.checkAssertion(x, x > 0, "Input must be > 0");
    }, IO7MAssertionError);
  }

  test_InvariantOK
  {
    var x = 1;
    IO7MAssertions.checkInvariant(x, x > 0, "Input must be > 0");
  }

  test_InvariantNotOK
  {
    var x = -2;

    this.assertException({
      IO7MAssertions.checkInvariant(x, x > 0, "Input must be > 0");
    }, IO7MAssertionError);
  }

  test_PreconditionOK
  {
    var x = 1;
    IO7MAssertions.checkPrecondition(x, x > 0, "Input must be > 0");
  }

  test_PreconditionNotOK
  {
    var x = -2;

    this.assertException({
      IO7MAssertions.checkPrecondition(x, x > 0, "Input must be > 0");
    }, IO7MAssertionError);
  }

  test_PostconditionOK
  {
    var x = 1;
    IO7MAssertions.checkPostcondition(x, x > 0, "Input must be > 0");
  }

  test_PostconditionNotOK
  {
    var x = -2;

    this.assertException({
      IO7MAssertions.checkPostcondition(x, x > 0, "Input must be > 0");
    }, IO7MAssertionError);
  }
}
