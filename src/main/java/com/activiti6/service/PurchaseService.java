package com.activiti6.service;

import java.util.Map;
import org.activiti.engine.runtime.ProcessInstance;

import com.activiti6.po.PurchaseApply;

public interface PurchaseService {
	public ProcessInstance startWorkflow(PurchaseApply apply,String userid,Map<String,Object> variables);
	PurchaseApply getPurchase(int id);
	void updatePurchase(PurchaseApply a);
}
