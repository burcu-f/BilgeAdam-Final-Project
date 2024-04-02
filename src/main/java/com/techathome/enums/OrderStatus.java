package com.techathome.enums;

public enum OrderStatus {
	PROCESSING("Processing"),
	COMPLETED("Completed");
	
	private final String displayName;
	
	OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
