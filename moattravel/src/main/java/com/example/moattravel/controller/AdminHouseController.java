package com.example.moattravel.controller;
/*管理者用の各種民宿ページを
 * 制御するためのコントローラ
 * 
 * */

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

	/*ページネーションとは？
	 * houseRepository.findAll();
	 * は、全部DBのデータをまとめて取ってくる
	 * 今は57件だから良いけど　多ければ重くなる
	 * ので、10けんずつの表示にして重さを解消する
	 * 
	 * */
	@GetMapping
	/*Pageable　とは…
	 * 10件ずつ表示の11件目以降を2ページ目に表示する
	 * ページ設定の箱、を作ってくれる仕組みのことである
	 * findAll(pageable)で「指定ページ分だけ」取る
	 * ことが出来るのである。
	 * */

	public String index(Model model,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
		/*戻り値が
		 * ListからPageになってる…
		 * 実際はデータから取ってきているが
		 * 今何ページ目であるのか、全体で何ページあるのか
		 * 前後のページの有無を教えてくれている
		 * 
		 * */
		Page<House> housePage = houseRepository.findAll(pageable);

		model.addAttribute("housePage", housePage);
		return "admin/houses/index";

	}

}
