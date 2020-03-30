package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HWCacheDemo {
  private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

  public static void main(String[] args) {
    new HWCacheDemo().demo();
  }

  private void demo() {
    HwCache<Integer, Integer> cache = new MyCache<Integer, Integer>();

    HwListener<Integer, Integer> listener = new HwListenerImpl(logger, "Listener1");
    HwListener<Integer, Integer> listener2 = new HwListenerImpl(logger, "Listener2");

    cache.addListener(listener);
    cache.addListener(listener2);
    cache.put(1, 1);

    logger.info("getValue:{}", cache.get(1));
    cache.remove(1);
    cache.removeListener(listener);
    cache.removeListener(listener2);
  }
}
