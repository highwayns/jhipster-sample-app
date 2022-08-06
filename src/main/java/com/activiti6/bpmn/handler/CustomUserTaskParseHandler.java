
package com.activiti6.bpmn.handler;

import com.activiti6.bpmn.behavior.CustomUserTaskActivityBehavior;
import com.activiti6.bpmn.model.CustomUserTask;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.AbstractActivityBpmnParseHandler;

/**
 * 用户节点处理器
 * @author lhj
 */
public class CustomUserTaskParseHandler extends AbstractActivityBpmnParseHandler<CustomUserTask> {

  public Class<? extends BaseElement> getHandledType() {
    return CustomUserTask.class;
  }

  @Override
  protected void executeParse(BpmnParse bpmnParse, CustomUserTask userTask) {
    userTask.setBehavior(new CustomUserTaskActivityBehavior(userTask));
  }

}
