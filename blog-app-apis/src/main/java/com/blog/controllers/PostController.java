package com.blog.controllers;
import com.blog.config.AppConstants;
import com.blog.config.AppConstants.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;



@RestController
@RequestMapping("/api/")


public class PostController {
	
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,@PathVariable Integer categoryId){
		
		PostDto createdPost=this.postService.createPost(postDto, categoryId, userId);
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
	
	
	//get post by providing user
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable Integer userId){
		List <PostDto> posts=this.postService.getAllPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
	
	//get post using category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostByCategory(@PathVariable Integer categoryId){
		List <PostDto> posts=this.postService.getAllPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
	
	//get All posts
	//page number starts from 0
	// using requestParam to extarct page number and page size which is passed in url as parameter
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)String sortDir){
		PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	
	
	
	//get post by postId
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto=this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	//delete post using postid
	@DeleteMapping("/posts/{postId}")
	public ApiResponse  deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponse("Post successfully deleted",true);
		
	}
	
	//update post
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto>  updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId) {
		PostDto updatedPost=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	
		
	}
	
	// search method
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostDto> result=this.postService.searchPost(keywords);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
		
	}
	
	// post image uploading---
	
	@PostMapping("/posts/image/upload/{postId}") 
	public ResponseEntity<PostDto> uploadPostImage
	(@RequestParam("image")MultipartFile image,@PathVariable Integer postId) throws IOException{
		PostDto postDto=this.postService.getPostById(postId);
		String fileName=this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatedPost=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
		
		
	}
	
	//method to serve files
	
	@GetMapping(value="/posts/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName")String imageName,HttpServletResponse response)throws  IOException{
		InputStream resource=this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
	

}
