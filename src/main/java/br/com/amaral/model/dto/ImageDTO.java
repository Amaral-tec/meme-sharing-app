package br.com.amaral.model.dto;

import java.io.Serializable;

public class ImageDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String originalImage;
	private String thumbnailImage;
	private Long meme;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getOriginalImage() {
		return originalImage;
	}
	
	public void setOriginalImage(String originalImage) {
		this.originalImage = originalImage;
	}
	
	public String getThumbnailImage() {
		return thumbnailImage;
	}
	
	public void setThumbnailImage(String thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}
	
	public Long getMeme() {
		return meme;
	}
	
	public void setMeme(Long meme) {
		this.meme = meme;
	}
	
}