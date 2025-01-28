package com.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repositories.CommentRepo;
import com.blog.repositories.PostRepo;
import com.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	// we need to get post so need postRepo
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		// fetch post
		
		Post Post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","PostId",postId));
		    Comment comment = this.modelMapper.map(commentDto,Comment.class);
		    comment.setPost(Post);
		   Comment savedComment= this.commentRepo.save(comment);
		   
		   
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment Id",commentId));
		this.commentRepo.delete(comment);

	}

}
