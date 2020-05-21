package com.incarcloud.grpc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CircularFifoQueueTests
 *
 * @author Aaric, created on 2020-05-20T13:18.
 * @version 0.6.0-SNAPSHOT
 */
@Slf4j
public class CircularFifoQueueTests {

    /**
     * 限定长度自动删除队头元素队列
     */
    private static volatile CircularFifoQueue<String> limit5Queue = new CircularFifoQueue<String>(5);

    @Test
    public void testUsage() {
        // offer, add
        for (int i = 0; i < 10; i++) {
            limit5Queue.offer(MessageFormat.format("index-{0,number,00}", i + 1));
        }
        limit5Queue.add("hello world");

        // poll, remove
        log.info("poll: {}", limit5Queue.poll());
        log.info("remove: {}\n", limit5Queue.remove());

        // peek, element
        log.info("peak: {}", limit5Queue.peek());
        log.info("element: {}\n", limit5Queue.element());

        // size
        log.info("size: {}\n", limit5Queue.size());

        // reverse
        List<String> list = limit5Queue.stream().collect(Collectors.toList());
        Collections.reverse(list);
        list.forEach(string -> log.info(string));

        Assertions.assertEquals(3, limit5Queue.size());
    }
}
