package org.example.usage.basics;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GitHub {

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(@Param("owner") String owner,
                                   @Param("repo") String repo);

    @RequestLine("POST /repos/{owner}/{repo}/issues")
    void createIssue(Issue issue,
                     @Param("owner") String owner,
                     @Param("repo") String repo);

    @RequestLine("GET /users/{username}/repos?sort={sort}")
    List<Repo> repos(@Param("username") String owner, @Param("sort") String sort);

    default List<Repo> repos(String owner) {
        return repos(owner, "full_name");
    }

    default List<Contributor> contributors(String owner) {
        MergingContributorList contributors = new MergingContributorList();
        for (Repo repo : this.repos(owner)) {
            contributors.addAll(this.contributors(owner, repo.getName()));
        }
        return contributors.mergeResult();
    }

    static GitHub connect() {
        return Feign.builder()
                .decoder(new GsonDecoder())
                .target(GitHub.class, "https://api.github.com");
    }

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    CompletableFuture<List<Contributor>> asyncContributors(@Param("owner") String owner, @Param("repo") String repo);
}
