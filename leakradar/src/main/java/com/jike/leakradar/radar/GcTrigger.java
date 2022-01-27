package com.jike.leakradar.radar;

public interface GcTrigger {
    
  GcTrigger DEFAULT = new GcTrigger() {
    @Override public void runGc() {
      Runtime.getRuntime().gc();
      enqueueReferences();
      System.runFinalization();
    }
      
    private void enqueueReferences() {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        throw new AssertionError();
      }
    }
  };
  void runGc();
}
