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

    static class ValueObserver implements Observer {

        private Subject subject;

        public ValueObserver(Subject subject) {
            this.subject = subject;
            this.subject.addObserver(this);
        }

        @Override
        public void update(Observable o, Object arg) {
            System.err.println("Update called with Arguments: " + arg);
        }

        public void destroy() {
            this.subject.deleteObserver(this);
        }
    }

    @Test
    public void testObserver() {
        Subject subject = new Subject("hello world");
        ValueObserver valueObserver = new ValueObserver(subject);

        subject.setValue("hello java");

        subject.setValue("hello kotlin");

        valueObserver.destroy();

        subject.setValue("hello groovy");
    }
}
