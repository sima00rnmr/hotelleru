package com.example.moattravel.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;


//getter setterの自動生成
@Data
public class HouseRegisterForm{
	
	/*それぞれの意味…
	 * >>@NotBlank
	 * 空欄を許容しないよー
	 * 
	 *>>@NotNull
	 *値が0なのはだめだよー
	 *
	 *>>@Min
	 *指定した額以上を設定してねー
	 * 
	 * 
	 * 他にも…＠Max（最大値）や＠Size（(min = 最小数, max = 最大数の指定）
	 * @Email 
	 * 文字列がメルアド出ない場合はダメ
	 * 
	 * @Pattern(regexp = "正規表現")
 　　* 文字列が正規表現にマッチすることを検証
 　　*→自分でチェック方法を定めることができる
	 *（ライブラリごと作れるって認識？）
	 *
	 *
	 * */
	@NotBlank(message = "民宿名を入力してください。")
	private String name;
	
	private MultipartFile imageFile;
	
	@NotBlank(message ="説明を入力してください。")
	private String description;
	
	@NotNull(message = "宿泊料金を入力してください。")
	@Min(value = 1,message = "宿泊料金は1円以上に設定してください。")
	private Integer price;
	
	@NotNull(message ="定員を入力してください。")
	@Min(value = 1 ,message= "定員は1人以上に設定してください。")
	private Integer capacity;
	
	@NotBlank(message ="郵便番号を入力してください。")
	private String postalCode;
	
	@NotBlank(message ="住所を入力してください。")
	private String address;
	
	@NotBlank(message ="電話番号を入力してください。")
	private String phoneNumber;
	
	
}