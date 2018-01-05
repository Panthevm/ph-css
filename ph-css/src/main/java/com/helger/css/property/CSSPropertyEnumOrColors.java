/**
 * Copyright (C) 2014-2018 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.css.property;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.regex.RegExHelper;
import com.helger.css.ECSSVendorPrefix;
import com.helger.css.property.customizer.ICSSPropertyCustomizer;
import com.helger.css.utils.CSSColorHelper;

/**
 * CSS property that is a list of either an enumeration or a color value (e.g.
 * border-color)
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSPropertyEnumOrColors extends AbstractCSSPropertyEnums
{
  public CSSPropertyEnumOrColors (@Nonnull final ECSSProperty eProp,
                                  @Nonnegative final int nMinNumbers,
                                  @Nonnegative final int nMaxNumbers,
                                  @Nonnull @Nonempty final String... aEnumValues)
  {
    this (eProp, (ICSSPropertyCustomizer) null, nMinNumbers, nMaxNumbers, aEnumValues);
  }

  public CSSPropertyEnumOrColors (@Nonnull final ECSSProperty eProp,
                                  @Nullable final ICSSPropertyCustomizer aCustomizer,
                                  @Nonnegative final int nMinNumbers,
                                  @Nonnegative final int nMaxNumbers,
                                  @Nonnull @Nonempty final String... aEnumValues)
  {
    this (eProp, (ECSSVendorPrefix) null, aCustomizer, nMinNumbers, nMaxNumbers, aEnumValues);
  }

  public CSSPropertyEnumOrColors (@Nonnull final ECSSProperty eProp,
                                  @Nullable final ECSSVendorPrefix eVendorPrefix,
                                  @Nullable final ICSSPropertyCustomizer aCustomizer,
                                  @Nonnegative final int nMinNumbers,
                                  @Nonnegative final int nMaxNumbers,
                                  @Nonnull @Nonempty final String... aEnumValues)
  {
    super (eProp, eVendorPrefix, aCustomizer, nMinNumbers, nMaxNumbers, aEnumValues);
  }

  public CSSPropertyEnumOrColors (@Nonnull final ECSSProperty eProp,
                                  @Nonnegative final int nMinNumbers,
                                  @Nonnegative final int nMaxNumbers,
                                  @Nonnull @Nonempty final Iterable <String> aEnumValues)
  {
    this (eProp, null, nMinNumbers, nMaxNumbers, aEnumValues);
  }

  public CSSPropertyEnumOrColors (@Nonnull final ECSSProperty eProp,
                                  @Nullable final ICSSPropertyCustomizer aCustomizer,
                                  @Nonnegative final int nMinNumbers,
                                  @Nonnegative final int nMaxNumbers,
                                  @Nonnull @Nonempty final Iterable <String> aEnumValues)
  {
    this (eProp, (ECSSVendorPrefix) null, aCustomizer, nMinNumbers, nMaxNumbers, aEnumValues);
  }

  public CSSPropertyEnumOrColors (@Nonnull final ECSSProperty eProp,
                                  @Nullable final ECSSVendorPrefix eVendorPrefix,
                                  @Nullable final ICSSPropertyCustomizer aCustomizer,
                                  @Nonnegative final int nMinNumbers,
                                  @Nonnegative final int nMaxNumbers,
                                  @Nonnull @Nonempty final Iterable <String> aEnumValues)
  {
    super (eProp, eVendorPrefix, aCustomizer, nMinNumbers, nMaxNumbers, aEnumValues);
  }

  @Override
  @OverridingMethodsMustInvokeSuper
  public boolean isValidValue (@Nullable final String sValue)
  {
    if (sValue == null)
      return false;

    // Split by whitespaces " a b " results in { "a", "b" }
    final String [] aParts = RegExHelper.getSplitToArray (sValue.trim (), "\\s+");
    if (aParts.length < getMinimumArgumentCount () || aParts.length > getMaximumArgumentCount ())
      return false;

    // Check each value
    for (final String aPart : aParts)
    {
      final String sTrimmedPart = aPart.trim ();
      if (!super.isValidValue (sTrimmedPart) && !CSSColorHelper.isColorValue (sTrimmedPart))
        return false;
    }
    return true;
  }

  @Override
  @Nonnull
  public CSSPropertyEnumOrColors getClone (@Nonnull final ECSSProperty eProp)
  {
    return new CSSPropertyEnumOrColors (eProp,
                                        getVendorPrefix (),
                                        getCustomizer (),
                                        getMinimumArgumentCount (),
                                        getMaximumArgumentCount (),
                                        directGetEnumValues ());
  }

  @Override
  @Nonnull
  public CSSPropertyEnumOrColors getClone (@Nullable final ECSSVendorPrefix eVendorPrefix)
  {
    return new CSSPropertyEnumOrColors (getProp (),
                                        eVendorPrefix,
                                        getCustomizer (),
                                        getMinimumArgumentCount (),
                                        getMaximumArgumentCount (),
                                        directGetEnumValues ());
  }
}
