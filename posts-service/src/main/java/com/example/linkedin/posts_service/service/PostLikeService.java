package com.example.linkedin.posts_service.service;

import com.example.linkedin.posts_service.entity.PostLike;
import com.example.linkedin.posts_service.exception.BadRequestException;
import com.example.linkedin.posts_service.exception.ResourceNotFoundException;
import com.example.linkedin.posts_service.repository.PostLikeRepository;
import com.example.linkedin.posts_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public void likePost(Long postId, Long userId) {
        boolean doesPostExists = postRepository.existsById(postId);
        if(!doesPostExists) throw new ResourceNotFoundException("Post not found with id: " + postId);

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(alreadyLiked) throw new BadRequestException("Already Liked");

        PostLike postLike = new PostLike();
        postLike.setUserId(userId);
        postLike.setPostId(postId);
        postLikeRepository.save(postLike);
        log.info("Post with id: {} liked successfully", postId);
    }

    public void unlikePost(Long postId, Long userId) {
        boolean doesPostExists = postRepository.existsById(postId);
        if(!doesPostExists) throw new ResourceNotFoundException("Post not found with id: " + postId);

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(!alreadyLiked) throw new BadRequestException("Can not unlike the post which is not liked");

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
        log.info("Post with id: {} unliked successfully", postId);
    }
}
