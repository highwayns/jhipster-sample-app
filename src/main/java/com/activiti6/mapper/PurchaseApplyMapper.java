package com.activiti6.mapper;

import com.activiti6.po.PurchaseApply;

public interface PurchaseApplyMapper {
	void save(PurchaseApply apply);
	
	PurchaseApply getPurchaseApply(int id);
	
	void updateByPrimaryKeySelective(PurchaseApply apply);
}
