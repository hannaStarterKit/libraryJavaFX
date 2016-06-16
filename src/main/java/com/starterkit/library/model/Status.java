/**
 * 
 */
package com.starterkit.library.model;

import com.starterkit.library.data.StatusVO;

/**
 * @author HSIENKIE
 *
 */
public enum Status {
	
	ANY, FREE;
	
	public static Status fromStatusVO(StatusVO status) {
		return Status.valueOf(status.name());
	}
	
	public StatusVO toStatusVO() {
		if (this == ANY) {
			return null;
		}
		return StatusVO.valueOf(this.name());
	}
	
}
