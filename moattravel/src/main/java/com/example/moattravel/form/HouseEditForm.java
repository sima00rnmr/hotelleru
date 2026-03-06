package com.example.moattravel.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HouseEditForm{
	@NotNull
	private Integer Id;
	@NotBlank(message ="民宿名を入力してください。")
	private String name;
	
	private MultipartFile imageFile;
	
	//説明
	@NotBlank(message ="説明を入力してください。")
	private String description;
	//料金
	@NotNull(message ="宿泊料金を入力してください。")
	@Min(value = 1,message ="宿泊料金は1円以上に設定してください")
	private Integer price;
	//定員
	@NotNull(message ="定員を入力してください。")
	private Integer capacity;
	@Min(value = 1,message ="定員は1人以上に設定してください")
	//郵便番号
	@NotBlank(message ="郵便番号を入力してください。")
	private String postalCode;
	//住所
	@NotBlank(message ="住所をを入力してください。")
	private String address;
	//電話番号
	@NotBlank(message ="電話番号を入力してください。")
	private String phoneNumber;
	
	
	
}