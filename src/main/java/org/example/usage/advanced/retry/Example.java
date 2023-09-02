package org.example.usage.advanced.retry;

import feign.Feign;
import feign.Response;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import org.example.usage.basics.GitHub;

public class Example {

    public static void main(String[] args) {
        GitHub github = Feign.builder()
                .decoder(new GsonDecoder())
                .retryer(new MyRetryer(100, 3))
                .errorDecoder(new MyErrorDecoder())
                .target(GitHub.class, "https://api.github.com");
    }

    static class MyErrorDecoder implements ErrorDecoder {

        private final ErrorDecoder defaultErrorDecoder = new Default();

        @Override
        public Exception decode(String methodKey, Response response) {
            if (response.status() == 401) {
                return new RetryableException(response.status(), response.reason(), response.request().httpMethod(), null, response.request());
            }

            return defaultErrorDecoder.decode(methodKey, response);
        }
    }

    static class MyRetryer implements Retryer {

        private final long period;

        private final int maxAttempts;

        private int attempt = 1;

        public MyRetryer(long period, int maxAttempts) {
            this.period = period;
            this.maxAttempts = maxAttempts;
        }

        @Override
        public void continueOrPropagate(RetryableException e) {
            if (++attempt > maxAttempts) {
                throw e;
            }

            if (e.status() == 401) {
                e.request().requestTemplate().removeHeader("Authorization");
                e.request().requestTemplate().header("Authorization", "Bearer " + getNewToken());
                try {
                    Thread.sleep(period);
                } catch (InterruptedException ex) {
                    throw e;
                }
            } else {
                throw e;
            }
        }

        private String getNewToken() {
            return "newToken";
        }

        @Override
        public Retryer clone() {
            return new MyRetryer(period, maxAttempts);
        }
    }
}
