package com.mydukan.elasticSearch.exceptions;

public class GroupNotFoundException extends AssignmentException {

	public GroupNotFoundException() {
	}

	public GroupNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public GroupNotFoundException(String message) {
		super(message);
	}

	public GroupNotFoundException(Throwable cause) {
		super(cause);
	}

}
