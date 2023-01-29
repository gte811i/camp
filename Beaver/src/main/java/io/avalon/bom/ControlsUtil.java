/**
 * Copyright (c) 2015, ControlsFX
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of ControlsFX, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL CONTROLSFX BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package io.avalon.bom;

import javafx.animation.PauseTransition;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import javafx.util.StringConverter;
import lombok.extern.log4j.Log4j2;

import org.controlsfx.control.PrefixSelectionComboBox;

import io.avalon.bom.components.AvalonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Log4j2
public class ControlsUtil{

	private static final int hidingDelay = 200;

	public static void setSearchParameters(PrefixSelectionComboBox<AvalonObject> comboBox) {
		comboBox.setConverter(new StringConverter<AvalonObject>() {
			@Override
			public String toString(AvalonObject object) {
				return String.format(object != null ? object.toString() : "");
			}

			@Override
			public AvalonObject fromString(String string) {
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}
		});
		comboBox.setBackSpaceAllowed(true);
		comboBox.setDisplayOnFocusedEnabled(true);
		comboBox.setTypingDelay(1000);  
		comboBox.setLookup(searchFunction());
		comboBox.setMaxWidth(Double.MAX_VALUE);
	}

	private static boolean isValidNumber(String prefix, int numberOfDigits) {
		if (prefix == null || prefix.length() != numberOfDigits) {
			return false;
		}
		try {
			Integer.parseInt(prefix);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	private static <T> void commitSelection(ComboBox<T> combo) {
		if (combo == null) {
			return;
		}
		PauseTransition pause = new PauseTransition(Duration.millis(hidingDelay));
		pause.setOnFinished(f -> {
			combo.hide();
			combo.fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.TAB, false, false, false, false));
		});
		pause.playFromStart();
	}

	private static <T> List<T> getItemsByLetter(ComboBox<T> combo, String firstLetter, int numberOfDigits) {
		if (combo == null) {
			return new ArrayList<>();
		}
		return combo.getItems().stream()
				.filter(item -> {
					String s = combo.getConverter().toString(item);
					return (s != null && ! s.isEmpty() && s.length() >= numberOfDigits + 2 && 
							s.substring(numberOfDigits + 1, numberOfDigits + 2).toUpperCase(Locale.ROOT).equals(firstLetter));
				})
				.collect(Collectors.toList());
	}
	private static BiFunction<ComboBox, String, Optional> searchFunction() {
		BiFunction<ComboBox, String, Optional> value = (t, u) -> {
			ComboBox<AvalonObject> combo = t;
			Supplier<? extends Optional<? extends AvalonObject>> supplier = () -> combo.getItems().stream().findFirst();
			Optional<AvalonObject> opt = combo.getItems().stream()
					.filter(item -> {
						String s = combo.getConverter().toString(item);
						log.debug("STRING: " + s);
						log.debug("U: " + u);
						if (s != null && ! s.isEmpty() && ! u.isEmpty()) {
							s = s.toUpperCase(Locale.ROOT);
							String firstLetter = u.substring(0, 1).toUpperCase(Locale.ROOT);
							final int numberOfOccurrences = u.toUpperCase(Locale.ROOT).replaceFirst(".*?(" + firstLetter + "+).*", "$1").length();
							int numberOfDigits = 0;
							if (s.substring(numberOfDigits + 0).startsWith(u.toUpperCase(Locale.ROOT))) {
								// alpha characters: highlight closest (first) match
//								commitSelection(combo);
								return true;
							}
							else if (numberOfOccurrences > 1 && 
									s.substring(0, 1).equals(firstLetter)) {
								final int numberOfItems = getItemsByLetter(combo, firstLetter, numberOfDigits).size();
								final int index = getItemsByLetter(combo, firstLetter, numberOfDigits).indexOf(item);
								// repeated alpha characters: highlight match based on order
								if(numberOfItems > 0)
									if (index == (numberOfOccurrences - 1) % numberOfItems) {
//									commitSelection(combo);
										return true;
									}
							}
//							int numberOfDigits = 2;
//							log.debug("STRING2: " + s.substring(numberOfDigits + 1));
//							log.debug("STRING3: " + numberOfOccurrences);
//							if (isValidNumber(u, numberOfDigits) && s.startsWith(u)) {
//								// two digits: select that line and tab to the next field
//								commitSelection(combo);
//								return true;
//							} else if (s.substring(numberOfDigits + 1).startsWith(u.toUpperCase(Locale.ROOT))) {
//								// alpha characters: highlight closest (first) match
//								return true;
//							} else if (numberOfOccurrences > 1 && 
//									s.substring(numberOfDigits + 1, numberOfDigits + 2).equals(firstLetter)) {
//								final int numberOfItems = getItemsByLetter(combo, firstLetter, numberOfDigits).size();
//								final int index = getItemsByLetter(combo, firstLetter, numberOfDigits).indexOf(item);
//								// repeated alpha characters: highlight match based on order
//								if (index == (numberOfOccurrences - 1) % numberOfItems) {
//									return true;
//								}
//							}
						}
						return false;
					})
					.findFirst()
//					.or(supplier)
					;
			log.debug("After: "+opt.toString());
			return opt;
		};
		return value;
	}

}