//HomeController.java
package com.example.moattravel.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.moattravel.entity.House;
import com.example.moattravel.repository.HouseRepository;

/*アノテーションとは…
 * 日本語では「注釈」って意味
 * Springbootdeでは@から始まる短いコードを指す
 * 
 * Getterしてくれたり色々便利！
 * 
 * */

/*クラスの前に@Controllerをつけると
 * このクラスはコントロールクラスだよ！
 *という宣言になる
 */
@Controller
public class HomeController {
	/*これはマッピング
	 *HttpリクエストのGetメソッドをそのメソッドに
	 *マッピング（対応付け）できる
	 *引数＝ルートパス
	 *サーバーから情報取得のために使う 
	 * 
	 */
	private final HouseRepository houseRepository;

	public HomeController(HouseRepository houseRepository) {
		this.houseRepository = houseRepository;
	}

	/*
	 * アプリのトップページにアクセスしたら
	 *このメソッドを実行 
	 * */
	@GetMapping("/")
	public String index(Model model) {
		List<House> newHouses = houseRepository.findTop10ByOrderByCreatedAtDesc();
		model.addAttribute("newHouses", newHouses);

		return "index";
	}
}