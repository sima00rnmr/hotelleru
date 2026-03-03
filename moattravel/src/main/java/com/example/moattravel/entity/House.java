package com.example.moattravel.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity //エンティティクラスとして使うよー
@Table(name = "houses") //作ったDB上のhousesと紐づけるよー
@Data //ゲッターやセッターなどを自動生成するよー
public class House {
	@Id //主キーには@GeneratedValueとセットでつけてねー 　
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	/*各フィールドに@Columnアノテーションをつけて
	 *対応させるカラム名を指定する
	 *
	 * カラムidはフィールドidと！みたいに
	 * */
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "image_name")
	private String imageName;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private Integer price;

	@Column(name = "capacity")
	private Integer capacity;

	@Column(name = "postal_code")
	private String postalCode;

	@Column(name = "address")
	private String address;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;

	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;
}
