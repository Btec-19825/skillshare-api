package com.skillshare.controller;

import com.skillshare.model.Post;
import com.skillshare.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.skillshare.model.Comment;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable String id, @RequestBody Post updated) {
        return postRepository.findById(id).map(p -> {
            p.setUsername(updated.getUsername());
            p.setTitle(updated.getTitle());
            p.setDescription(updated.getDescription());
            p.setMediaUrls(updated.getMediaUrls());
            return postRepository.save(p);
        }).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable String id) {
        postRepository.deleteById(id);
    }

    @PostMapping("/upload")
    public List<String> uploadMedia(@RequestParam("files") MultipartFile[] files) throws IOException {
        System.out.println("Upload triggered. File count: " + files.length);

        List<String> urls = new ArrayList<>();
        String uploadRoot = System.getProperty("user.dir") + File.separator + "uploads";
        File uploadDir = new File(uploadRoot);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        for (MultipartFile file : files) {
            if (urls.size() >= 3) break;

            String contentType = file.getContentType();
            if (contentType != null && (contentType.startsWith("image/") || contentType.equals("video/mp4"))) {
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                File dest = new File(uploadDir, filename);
                file.transferTo(dest);
                urls.add("/uploads/" + filename);
                System.out.println("Saved: " + filename);
            }
        }

        return urls;
    }

    @PostMapping("/{postId}/comments")
    public Post addComment(@PathVariable String postId, @RequestBody Comment comment) {
        return postRepository.findById(postId).map(post -> {
            comment.setId(UUID.randomUUID().toString());
            comment.setTimestamp(Instant.now().toString());
            post.getComments().add(comment);
            return postRepository.save(post);
        }).orElseThrow();
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public Post updateComment(
        @PathVariable String postId,
        @PathVariable String commentId,
        @RequestBody Comment updated
    ) {
        return postRepository.findById(postId).map(post -> {
            post.getComments().forEach(c -> {
                if (c.getId().equals(commentId)) {
                    c.setMessage(updated.getMessage());
                    c.setTimestamp(Instant.now().toString());
                }
            });
            return postRepository.save(post);
        }).orElseThrow();
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public Post deleteComment(@PathVariable String postId, @PathVariable String commentId) {
        return postRepository.findById(postId).map(post -> {
            post.getComments().removeIf(c -> c.getId().equals(commentId));
            return postRepository.save(post);
        }).orElseThrow();
    }

    @PutMapping("/{postId}/like")
    public Post likePost(@PathVariable String postId, @RequestBody Map<String, String> body) {
        String username = body.get("username");
        return postRepository.findById(postId).map(post -> {
            if (!post.getLikedBy().contains(username)) {
                post.getLikedBy().add(username);
                post.setLikes(post.getLikes() + 1);

                if (post.getUnlikedBy().remove(username)) {
                    post.setUnlikes(post.getUnlikes() - 1);
                }
            }
            return postRepository.save(post);
        }).orElseThrow();
    }

    @PutMapping("/{postId}/unlike")
    public Post unlikePost(@PathVariable String postId, @RequestBody Map<String, String> body) {
        String username = body.get("username");
        return postRepository.findById(postId).map(post -> {
            if (!post.getUnlikedBy().contains(username)) {
                post.getUnlikedBy().add(username);
                post.setUnlikes(post.getUnlikes() + 1);

                if (post.getLikedBy().remove(username)) {
                    post.setLikes(post.getLikes() - 1);
                }
            }
            return postRepository.save(post);
        }).orElseThrow();
    }

    // @PostMapping("/upload")
    // public List<String> uploadMedia(@RequestParam("files") MultipartFile[] files) throws IOException {
    //     List<String> urls = new ArrayList<>();
    //     File uploadDir = new File("uploads");
    //     if (!uploadDir.exists()) uploadDir.mkdirs();

    //     for (MultipartFile file : files) {
    //         if (urls.size() >= 3) break; // Limit to 3 uploads

    //         String contentType = file.getContentType();
    //         if (contentType != null && (contentType.startsWith("image/") || contentType.equals("video/mp4"))) {
    //             String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
    //             File dest = new File(uploadDir, filename);
    //             file.transferTo(dest);
    //             urls.add("/uploads/" + filename);
    //         }
    //     }

    //     return urls;
    // }
}
