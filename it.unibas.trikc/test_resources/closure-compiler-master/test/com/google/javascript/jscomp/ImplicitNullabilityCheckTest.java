/*
 * Copyright 2015 The Closure Compiler Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.javascript.jscomp;

/** Unit tests for {@link ImplicitNullabilityCheck}. */
public final class ImplicitNullabilityCheckTest extends TypeICompilerTestCase {

  @Override
  public CompilerPass getProcessor(Compiler compiler) {
    return new ImplicitNullabilityCheck(compiler);
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  public void testExplicitJsdocDoesntWarn() {
    noWarning("/** @type {boolean} */ var x;");
    noWarning("/** @type {null} */ var x;");
    noWarning("/** @type {!Object} */ var x;");
    noWarning("/** @type {?Object} */ var x;");
    noWarning("/** @type {function(new:Object)} */ function f(){}");
    noWarning("/** @type {function(this:Object)} */ function f(){}");
    noWarning("/** @typedef {!Object} */ var Obj; var /** Obj */ x;");
  }

  public void testExplicitlyNullableUnion() {
    noWarning("/** @type {(Object|null)} */ var x;");
    noWarning("/** @type {(Object|number)?} */ var x;");
    noWarning("/** @type {?(Object|number)} */ var x;");
    noWarning("/** @type {(Object|?number)} */ var x;");
    warnImplicitlyNullable("/** @type {(Object|number)} */ var x;");
  }

  public void testJsdocPositions() {
    warnImplicitlyNullable("/** @type {Object} */ var x;");
    warnImplicitlyNullable("var /** Object */ x;");
    warnImplicitlyNullable("/** @typedef {Object} */ var x;");
    warnImplicitlyNullable("/** @param {Object} x */ function f(x){}");
    warnImplicitlyNullable(
        "/** @return {Object} */ function f(x){ return {}; }");
  }

  public void testNullableTypedef() {
    // Arguable whether or not this deserves a warning, so leaving
    // out of NTI for now.
    testSameOtiOnly(
        DEFAULT_EXTERNS,
        "/** @typedef {?number} */ var Num; var /** Num */ x;",
        ImplicitNullabilityCheck.IMPLICITLY_NULLABLE_JSDOC);
  }

  public void testUnkownTypenameDoesntWarn() {
    // Different warnings in OTI and NTI
    testSameOtiOnly(
        DEFAULT_EXTERNS, "/** @type {gibberish} */ var x;", RhinoErrorReporter.TYPE_PARSE_ERROR);

    testSameNtiOnly(
        DEFAULT_EXTERNS, "/** @type {gibberish} */ var x;", GlobalTypeInfo.UNRECOGNIZED_TYPE_NAME);
  }

  public void testThrowsDoesntWarn() {
    noWarning("/** @throws {Error} */ function f() {}");
    noWarning("/** @throws {TypeError}\n * @throws {SyntaxError} */ function f() {}");
  }

  public void testUserDefinedClass() {
    warnImplicitlyNullable(LINE_JOINER.join(
        "/** @constructor */",
        "function Foo() {}",
        "/** @type {Foo} */ var x;"));

    // TODO(aravindpg): this ought to pass under both, or at any rate NTI.
    noWarning(LINE_JOINER.join(
        "function f() {",
        "  /** @constructor */",
        "  function Foo() {}",
        "  /** @type {Foo} */ var x;",
        "}"));
  }

  private void warnImplicitlyNullable(String js) {
    testSame(DEFAULT_EXTERNS, js, ImplicitNullabilityCheck.IMPLICITLY_NULLABLE_JSDOC);
  }

  private void noWarning(String js) {
    testSame(DEFAULT_EXTERNS, js, null);
  }
}