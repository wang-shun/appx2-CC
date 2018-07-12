package com.dreawer.customer.domain;

import static org.apache.commons.lang.StringUtils.isBlank;

import com.dreawer.domain.BaseDomain;

public class App extends BaseDomain {

	private static final long serialVersionUID = 5069461670303502943L;
	
	private String category = null; // 分类

	public String getCategory() {
		if(isBlank(category)) {
            category = this.getClass().getSimpleName().toLowerCase();
        }
        return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
    
}
