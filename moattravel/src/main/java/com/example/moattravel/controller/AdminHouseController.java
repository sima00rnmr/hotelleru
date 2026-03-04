package com.example.moattravel.controller;
/*管理者用の各種民宿ページを
 * 制御するためのコントローラ
 * 
 * */

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.moattravel.entity.House;
import com.example.moattravel.repository.HouseRepository;

//このクラスはコントローラーだよ
@Controller
/*@RequestMapping
 * URLの共通ルール（基準）を決めるアノテーション
 * このクラスの中のURLは全部"/admin/houses"から
 * 始まるようになる
 * 
 */
@RequestMapping("/admin/houses")

public class AdminHouseController {
	 /*変更は許容しない
	  * DB操作担当をこのクラスで使う…宣言
	  * */
	private final HouseRepository houseRepository;

	public AdminHouseController(HouseRepository houseRepository) {
		this.houseRepository = houseRepository;
	}

	@GetMapping
	public String index(Model model) {
		List<House> houses = houseRepository.findAll();

		model.addAttribute("houses", houses);
		return "admin/houses/index";

	}

}
