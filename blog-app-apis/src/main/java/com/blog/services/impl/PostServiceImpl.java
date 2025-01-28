package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;



@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto,Integer categoryId,Integer userId) {
		
		// user has title and content already from userDto
		
		   User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","UserId",userId));
			Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
		   Post post=this.modelMapper.map(postDto,Post.class);
			post.setImageName("default.png");
			post.setAddedDate(new Date());
			post.setCategory(category);
			post.setUser(user);
			
			Post newPost=this.postRepo.save(post);
			return this.modelMapper.map(newPost, PostDto.class);
		// TODO Auto-generated method stub
		
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","PostId",postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		
		Post updatedPost=this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
		
		
		
		
		
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","PostID",postId));
		this.postRepo.delete(post);
	}
//changing return type from PostDto to PostResponse
	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		// implementing paginaton
		
		//creating pageable object
		//Pageable p=PageRequest.of(pageNumber, pageSize);
		// with sorting use below
		//Pageable p=PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		//for ascending and descending sort
		
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
			
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		
		//Pageable p=PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
		
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
		//Find post using pageable
		Page <Post> pagePosts=this.postRepo.findAll(p);
		List<Post> allPosts=pagePosts.getContent();
		
		//List<Post> allPosts=this.postRepo.findAll();
		List<PostDto> allPostDto= allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(allPostDto);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
		
	
	}

	@Override
	public PostDto getPostById(Integer postId) {
		// TODO Auto-generated method stub
		
	Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post_id",postId));	
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getAllPostByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category_id",categoryId));
		
		List<Post> posts= this.postRepo.findByCategory(cat);
		//converting post to postdto
		List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostByUser(Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User_id",userId));
		// TODO Auto-generated method stub
		List<Post> posts=this.postRepo.findByUser(user);
		List <PostDto> postDtos=   posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List <Post> posts=this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
				return postDtos ;
	}

}
