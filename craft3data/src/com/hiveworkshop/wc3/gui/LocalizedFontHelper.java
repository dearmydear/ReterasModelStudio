package com.hiveworkshop.wc3.gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides fonts that support CJK (Chinese, Japanese, Korean) characters in
 * addition to standard Latin characters.  Calling code should use this helper
 * instead of constructing {@code new Font("Arial", ...)} directly so that
 * Chinese file/object names are displayed correctly on all platforms.
 */
public final class LocalizedFontHelper {

	/**
	 * Preferred sans-serif fonts, ordered from most desirable to fallback.
	 * "Microsoft YaHei" and "SimHei" are bundled with Chinese Windows; the
	 * WenQuanYi / Noto fonts cover Linux distributions; "Arial Unicode MS"
	 * is available on macOS; "Dialog" is Java's built-in composite logical
	 * font that uses platform CJK fonts automatically.
	 */
	private static final String[] PREFERRED_SANS_FONTS = {
		"Microsoft YaHei", "SimHei", "SimSun", "NSimSun",
		"WenQuanYi Micro Hei", "Noto Sans CJK SC", "Arial Unicode MS",
		"Dialog"
	};

	/**
	 * Preferred monospace fonts for technical content such as file paths and
	 * time-slider tick labels.  Falls back to Java's "Monospaced" logical
	 * font which includes CJK glyphs through composite font mapping.
	 */
	private static final String[] PREFERRED_MONO_FONTS = {
		"Consolas", "Lucida Console", "Courier New", "Monospaced"
	};

	private static Set<String> availableFontNames;
	private static String cachedSansFont;
	private static String cachedMonoFont;

	/**
	 * Returns a sans-serif {@link Font} with the given style and size that is
	 * capable of displaying CJK characters.
	 */
	public static synchronized Font getSansFont(final int style, final int size) {
		if (cachedSansFont == null) {
			cachedSansFont = pickFont(PREFERRED_SANS_FONTS, "Dialog");
		}
		return new Font(cachedSansFont, style, size);
	}

	/**
	 * Returns a monospace {@link Font} with the given style and size.  The
	 * returned font preserves the monospaced appearance while still supporting
	 * CJK characters through Java's composite font mechanism.
	 */
	public static synchronized Font getMonospaceFont(final int style, final int size) {
		if (cachedMonoFont == null) {
			cachedMonoFont = pickFont(PREFERRED_MONO_FONTS, "Monospaced");
		}
		return new Font(cachedMonoFont, style, size);
	}

	private static String pickFont(final String[] candidates, final String fallback) {
		final Set<String> available = getAvailableFontNames();
		for (final String name : candidates) {
			if (available.contains(name)) {
				return name;
			}
		}
		return fallback;
	}

	private static synchronized Set<String> getAvailableFontNames() {
		if (availableFontNames == null) {
			availableFontNames = new HashSet<>(Arrays.asList(
					GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
		}
		return availableFontNames;
	}

	private LocalizedFontHelper() {
	}
}
