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

        @Override
        public void update(Observable o, Object arg) {
            System.err.println("Update called with Arguments: " + arg);
        }
    }

    @Test
    public void testObserver() {
        Subject subject = new Subject("hello world");
        ValueObserver valueObserver = new ValueObserver();

        subject.setValue("hello java");

        subject.addObserver(valueObserver);

        subject.setValue("hello kotlin");

        subject.deleteObserver(valueObserver);

        subject.setValue("hello groovy");
    }
}
