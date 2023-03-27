package com.viettel.example;

/**
 * @author anhnsq@viettel.com.vn
 */

import org.apache.nifi.annotation.behavior.InputRequirement;
import org.apache.nifi.annotation.behavior.InputRequirement.Requirement;
import org.apache.nifi.annotation.behavior.SideEffectFree;
import org.apache.nifi.annotation.behavior.SupportsBatching;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.*;

import java.util.List;
import java.util.Set;

//@SideEffectFree
//@SupportsBatching
@Tags({"content", "split", "binary"})
@CapabilityDescription("Splits incoming FlowFiles by a specified byte sequence")
public class MyOtherProcessor extends AbstractProcessor {
  public static final Relationship REL_SPLITS = new Relationship.Builder()
    .name("splits")
    .description("All Splits will be routed to the splits relationship")
    .build();
  public static final Relationship REL_ORIGINAL = new Relationship.Builder()
    .name("original")
    .description("The original file")
    .build();

  private Set<Relationship> relationships;
  private List<PropertyDescriptor> properties;

  @Override
  protected void init(final ProcessorInitializationContext context) {
    this.relationships = Set.of(REL_SPLITS, REL_ORIGINAL);
  }

  @Override
  public Set<Relationship> getRelationships() {
    return relationships;
  }

  @Override
  protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
    return properties;
  }

  @Override
  public void onTrigger(final ProcessContext context, final ProcessSession session) {
    FlowFile flowFile = session.get();
    if (flowFile == null) {
      return;
    }

//    FlowFile clone = session.clone(flowFile);
    FlowFile newFlowFile = session.create();
//    session.transfer(clone, REL_SPLITS);
    session.transfer(newFlowFile, REL_SPLITS);
    session.transfer(flowFile, REL_ORIGINAL);
  }
}