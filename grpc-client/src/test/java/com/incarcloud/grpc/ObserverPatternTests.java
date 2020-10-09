package com.incarcloud.grpc;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Observable;
import java.util.Observer;

/**
 * ObserverPatternTests
 *
 * @author Aaric, created on 2020-05-22T09:23.
 * @version 0.6.2-SNAPSHOT
 */
public class ObserverPatternTests {

    static class Subject extends Observable {

        private String value;

        public Subject(String value) {
            this.value = value;
        }

        public void setValue(String value) {
            if (!StringUtils.endsWith(this.value, value)) {
                System.out.println("Value changed to new value: " + value);

                this.value = value;

                setChanged();

                notifyObservers(value);
            }
        }
    }

    static class Value1Observer implements Observer {

        private Subject subject;

        public Value1Observer(Subject subject) {
            this.subject = subject;
            this.subject.addObserver(this);
        }

        @Override
        public void update(Observable o, Object arg) {
//            if (1 == 1) {
//                throw new IllegalArgumentException("fake exception");
//            }
            System.err.println("Update <1> called with Arguments: " + arg);
        }

        public void destroy() {
            this.subject.deleteObserver(this);
        }
    }

    static class Value2Observer implements Observer {

        private Subject subject;

        public Value2Observer(Subject subject) {
            this.subject = subject;
            this.subject.addObserver(this);
        }

        @Override
        public void update(Observable o, Object arg) {
            System.err.println("Update <2> called with Arguments: " + arg);
        }

        public void destroy() {
            this.subject.deleteObserver(this);
        }
    }

    @Test
    public void testObserver() {
        Subject subject = new Subject("hello world");
        Value1Observer value1Observer = new Value1Observer(subject);
        Value2Observer value2Observer = new Value2Observer(subject);

        subject.setValue("hello java");

        subject.setValue("hello kotlin");

        value1Observer.destroy();
        value2Observer.destroy();

        subject.setValue("hello groovy");
    }
}
