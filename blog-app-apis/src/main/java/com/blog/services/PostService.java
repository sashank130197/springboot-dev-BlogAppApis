package com.blog.services;

import java.util.List;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {

	
	//create
	
	PostDto createPost(PostDto postDto,Integer categoryId,Integer userId);
	
	PostDto updatePost(PostDto post,Integer postId);
	
	void deletePost(Integer postId);
	
	//getAllPost
	
	//List<PostDto> getAllPost();
	// get all post using pagination and return type is now PostResponse instead of PostDto
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	//get single post
	
	PostDto getPostById(Integer postId);
	
	List<PostDto> getAllPostByCategory(Integer categoryId);
	 
	//get post by user
	
	List <PostDto> getAllPostByUser(Integer userId);
	
	//search post
	List<PostDto> searchPost(String keyword);
	
	
}
