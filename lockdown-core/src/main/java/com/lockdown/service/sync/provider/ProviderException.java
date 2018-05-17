package com.lockdown.service.sync.provider;

public class ProviderException extends RuntimeException {

	private static final long serialVersionUID = 2304440327847055819L;

	public ProviderException() {
		super();
	}

	public ProviderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProviderException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProviderException(String message) {
		super(message);
	}

	public ProviderException(Throwable cause) {
		super(cause);
	}

}
