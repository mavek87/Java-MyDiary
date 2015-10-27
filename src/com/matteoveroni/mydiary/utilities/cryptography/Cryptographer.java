package com.matteoveroni.mydiary.utilities.cryptography;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Matteo Veroni
 */
public class Cryptographer {

	private static final String SALT = "java-my-diary";

	public String sha2_256(String string) {
		return DigestUtils.sha256Hex(SALT + string);
	}

	public boolean isHashEqualToString(String hash, String string) {
		return (hash.equals(sha2_256(SALT + string)));
	}
}
