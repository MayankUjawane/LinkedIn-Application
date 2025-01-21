package com.example.linkedin.posts_service.service;

import com.example.linkedin.posts_service.auth.UserContextHolder;
import com.example.linkedin.posts_service.clients.ConnectionsClient;
import com.example.linkedin.posts_service.dto.PersonDto;
import com.example.linkedin.posts_service.dto.PostCreateRequestDto;
import com.example.linkedin.posts_service.dto.PostDto;
import com.example.linkedin.posts_service.entity.Post;
import com.example.linkedin.posts_service.exception.ResourceNotFoundException;
import com.example.linkedin.posts_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionsClient connectionsClient;

    public PostDto createPost(PostCreateRequestDto postDto, Long userId) {
        Post post = modelMapper.map(postDto, Post.class);
        post.setUserId(userId);
        Post createdPost = postRepository.save(post);
        return modelMapper.map(createdPost, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        List<PersonDto> firstDegreeConnections = null;
        if(userId != null) {
            firstDegreeConnections = connectionsClient.firstDegreeConnections();
        }
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: "+postId));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }
}
