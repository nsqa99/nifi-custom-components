package com.viettel.example;

import com.viettel.example.service.EncryptionService;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.*;
import org.apache.nifi.processor.exception.ProcessException;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Set;

/**
 * @author anhnsq@viettel.com.vn
 */
@Tags({"example", "processor", "encrypt", "TripleDES"})
@CapabilityDescription("Encrypt FlowFile contents using TripleDES algorithm")
public class MyProcessor extends AbstractProcessor {
  private List<PropertyDescriptor> descriptors;
  private Set<Relationship> relationships;
  private static final Relationship REL_SUCCESS = new Relationship.Builder()
    .name("success")
    .description("Successfully encoded text")
    .build();

  private static final Relationship REL_FAILURE = new Relationship.Builder()
    .name("failure")
    .description("Failed to encode text")
    .build();
  public static final Relationship REL_ORIGINAL = new Relationship.Builder()
    .name("original")
    .description("The original file")
    .build();
  @Override
  protected void init(ProcessorInitializationContext context) {
    //descriptors.add(MY_PROPERTY);
    this.relationships = Set.of(REL_SUCCESS, REL_FAILURE, REL_ORIGINAL);
  }

  @Override
  public void onTrigger(ProcessContext processContext, ProcessSession processSession) throws ProcessException {
    FlowFile flowFile = processSession.get();
    if (flowFile == null) {
      return;
    }
//    final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//    processSession.exportTo(flowFile, bytes);
//    final String contents = bytes.toString();
//    String encryptedContent;
    FlowFile clone = processSession.clone(flowFile);
//    processSession.transfer(flowFile, REL_ORIGINAL);
    processSession.transfer(clone, REL_SUCCESS);
//
//    try {
//      encryptedContent = EncryptionService.encrypt(contents);
//      newFlowFile = processSession.write(newFlowFile, out -> out.write(encryptedContent.getBytes()));
//      processSession.transfer(newFlowFile, REL_SUCCESS);
//    } catch (RuntimeException e) {
//      processSession.transfer(newFlowFile, REL_FAILURE);
//    }
  }

  @Override
  public Set<Relationship> getRelationships() {
    return this.relationships;
  }

  @Override
  public final List<PropertyDescriptor> getSupportedPropertyDescriptors() {
    return descriptors;
  }
}
