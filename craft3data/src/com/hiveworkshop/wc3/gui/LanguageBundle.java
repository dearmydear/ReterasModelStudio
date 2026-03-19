package com.hiveworkshop.wc3.gui;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Central access point for all localizable UI strings.
 * <p>
 * Call {@link #setLocale(Locale)} once at application startup (before any UI
 * is constructed) to choose the active language.  If no locale is set the JVM
 * default locale is used, which means the application will automatically
 * display in Simplified Chinese on a Chinese Windows/Linux system.
 * </p>
 * <p>
 * String resources are loaded from
 * {@code com/hiveworkshop/wc3/gui/messages.properties} (English) and
 * {@code com/hiveworkshop/wc3/gui/messages_zh_CN.properties} (Simplified
 * Chinese).
 * </p>
 */
public final class LanguageBundle {

	private static final String BUNDLE_NAME = "com.hiveworkshop.wc3.gui.messages";
	private static ResourceBundle bundle;

	/**
	 * Sets the locale to use for all subsequent {@link #get(String)} calls.
	 * This method is not thread-safe and must be called before any Swing UI is
	 * created on the Event Dispatch Thread.
	 *
	 * @param locale the desired locale; {@code null} means JVM default
	 */
	public static synchronized void setLocale(final Locale locale) {
		bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale == null ? Locale.getDefault() : locale);
	}

	/**
	 * Returns the localized string for the given key.  If the key does not
	 * exist in the current bundle the key itself is returned so the UI never
	 * shows a blank label.
	 *
	 * @param key the message key (e.g. {@code "menu.file"})
	 * @return the localized string, never {@code null}
	 */
	public static String get(final String key) {
		if (bundle == null) {
			bundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());
		}
		try {
			return bundle.getString(key);
		}
		catch (final MissingResourceException e) {
			return key;
		}
	}

	private LanguageBundle() {
	}
}
