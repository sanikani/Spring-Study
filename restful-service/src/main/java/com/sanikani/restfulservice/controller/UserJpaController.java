package com.sanikani.restfulservice.controller;

import com.sanikani.restfulservice.bean.Post;
import com.sanikani.restfulservice.bean.User;
import com.sanikani.restfulservice.bean.UserListResponse;
import com.sanikani.restfulservice.excetion.UserNotFoundException;
import com.sanikani.restfulservice.repository.PostRepository;
import com.sanikani.restfulservice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
@RequiredArgsConstructor
@Slf4j
public class UserJpaController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    @GetMapping("/users/{id}")
    public ResponseEntity retrieveUserById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException("id-" + id);
        }

        EntityModel<User> entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));


        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        log.info("ssn={},password={},name={},joinDate={}", savedUser.getSsn(), savedUser.getPassword(), savedUser.getName(), savedUser.getJoinDate());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    //    @GetMapping("/users")
//    public ResponseEntity<UserListResponse> retrieveAllUsers() {
//        List<User> users = userRepository.findAll();
//        UserListResponse response = UserListResponse.builder()
//                .count(users.size())
//                .users(users)
//                .build();
//        return ResponseEntity.ok(response);
//    }
    @GetMapping("/users")
    public ResponseEntity retrieveAllUsers() {
        List<User> users = userRepository.findAll();
        UserListResponse response = UserListResponse.builder()
                .count(users == null || users.isEmpty() ? 0 : users.size())
                .users(users)
                .build();

        EntityModel entityModel = EntityModel.of(response);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withSelfRel());

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("유저를 찾을 수 없습니다");
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("유저를 찾을 수 없습니다");
        }

        post.setUser(user.get());
        postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
