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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.moattravel.entity.House;
import com.example.moattravel.form.HouseEditForm;
import com.example.moattravel.form.HouseRegisterForm;
import com.example.moattravel.repository.HouseRepository;
import com.example.moattravel.service.HouseService;

//このクラスはコントローラーだよ
@Controller
/*
 *   >>@RequestMapping
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
	private final HouseService houseService;

	public AdminHouseController(HouseRepository houseRepository, HouseService houseService) {

		this.houseRepository = houseRepository;
		this.houseService = houseService;
	}

	/*ページネーションとは？
	 * houseRepository.findAll();
	 * は、全部DBのデータをまとめて取ってくる
	 * 今は57件だから良いけど　多ければ重くなる
	 * ので、10件ずつの表示にして重さを解消する
	 * 
	 * ※実際
	 * ページ指定がなければ全件取得になる
	 * の認識で
	 * →現在のコード
	 * @PageableDefault(page = 0, size = 10)
	 * に修正したので
	 * ページ指定がなくても 0ページ目・10件取得になる
	 * 
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
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			/*@RequestParam　って何？
			 * URLのクエリパラメータを受け取る
			 * 例: /admin/houses?keyword=東京
			 * URLの ?◯◯=値 を受け取る係
			 * 下記の検索欄に関連
			 * ない場合はnullとしてうけとっている
			 * 
			 * */

			@RequestParam(name = "keyword", required = false) String keyword) {
		/*戻り値が
		 * ListからPageになってる…
		 * 実際はデータから取ってきているが
		 * 今何ページ目であるのか、全体で何ページあるのか
		 * 前後のページの有無を教えてくれている
		 * 
		 * */
		Page<House> housePage;

		/* keyword= について
		 * 
		 * これによって…コントローラが受けとり
		 * あれば検索、無ければ全件表示、その文字を画面に戻す
		 *の、動きをしている 
		 *
		 *ここの　％って何？
		 *　これはSQLのLIKE '%東京%'と同じ
		 *東京を含む～と同じ意味になる
		 * */
		//キーワードが存在していて空じゃない場合は～
		if (keyword != null && !keyword.isEmpty()) {
			//存在していたらそれを返すよ
			housePage = houseRepository.findByNameLike("%" + keyword + "%", pageable);
		} else {
			//無かったら全件返すよ
			housePage = houseRepository.findAll(pageable);
		}

		model.addAttribute("housePage", housePage);
		model.addAttribute("keyword", keyword);
		return "admin/houses/index";

	}

	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model) {

		/*getReferenceById() って何をしている？
		 * IDが～のHouseを1件だけ取ってきて　の意味
		 * id=主キーなので重複がない∴1件だけ
		 * 
		 * 一覧画面で生成されたURL（例：/admin/houses/3）の
		 *「3」の部分が @GetMapping("/{id}") の {id} にバインドされる
		 *
		 *※バインド
		 *外から来た値を、プログラムの変数に入れること
		 *（URLを読む、値を取り出す、型変換する、引数に入れる　など…）
		 * */
		House house = houseRepository.getReferenceById(id);

		model.addAttribute("house", house);

		return "admin/houses/show";
	}

	/* 
	 * ここのメソッドは
	 * /admin/houses/register にアクセス
	 *空のフォームオブジェクトを作る
	 *それをHTMLに渡す
	 *register.html を表示する
	 * 
	 * 役割
	 * 
	 * 表示とフォーム用のオブジェクトを渡してる
	 * */
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("houseRegisterForm", new HouseRegisterForm());
		return "admin/houses/register";

	}

	@PostMapping("/create")
	public String create(@ModelAttribute @Validated HouseRegisterForm houseRegisterForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "admin/houses/register";
		}

		houseService.create(houseRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "民宿を登録しました。");
		return "redirect:/admin/houses";
	}
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable(name = "id")Integer id, Model model) {
		House house =houseRepository.getReferenceById(id);
		String imageName =house.getImageName();
		HouseEditForm houseEditForm = new HouseEditForm(house.getId(),house.getName(),null,house.getDescription(),house.getPrice(),house.getCapacity(),house.getPostalCode(),house.getAddress(),house.getPhoneNumber());
		
		model.addAttribute("imageName",imageName);
		model.addAttribute("houseEditForm",houseEditForm);
		
		return"admin/houses/edit";
	}
}
