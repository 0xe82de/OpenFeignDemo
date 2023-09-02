package org.example.usage.basics;

import feign.AsyncFeign;
import feign.Feign;
import feign.gson.GsonDecoder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MyApp {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        GitHub github = Feign.builder()
                .decoder(new GsonDecoder())
                .target(GitHub.class, "https://api.github.com");
//        basic(github);
//        repo(github);

        async();
    }

    private static void async() throws InterruptedException, ExecutionException, TimeoutException {
        GitHub asyncGithub = AsyncFeign.builder()
                .decoder(new GsonDecoder())
                .target(GitHub.class, "https://api.github.com");
        CompletableFuture<List<Contributor>> asyncContributors = asyncGithub.asyncContributors("OpenFeign", "feign");
        for (Contributor contributor : asyncContributors.get(1, TimeUnit.SECONDS)) {
            System.out.println(contributor.getLogin() + " (" + contributor.getContributions() + ")");
            String owner = contributor.getLogin();
            List<Repo> repos = asyncGithub.repos(owner);
            for (Repo repo : repos) {
                System.out.println("\t\trepo.getName() = " + repo.getName());
            }
        }
    }

    private static void repo(GitHub github) {
        List<Repo> repos = github.repos("0xe82de");
        for (Repo repo : repos) {
            System.out.println("repo.getName() = " + repo.getName());
        }
    }

    private static void basic(GitHub github) {
        List<Contributor> contributors = github.contributors("OpenFeign", "feign");
        for (Contributor contributor : contributors) {
            System.out.println(contributor.getLogin() + " (" + contributor.getContributions() + ")");
//            String owner = contributor.getLogin();
//            List<Repo> repos = github.repos(owner);
//            for (Repo repo : repos) {
//                System.out.println("\t\trepo.getName() = " + repo.getName());
//            }
        }
    }
}
