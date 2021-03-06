package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.GameBeans;
import model.ScoreBeans;

/**
 * DBに登録されているスコアのリストを作成して返すDAOクラス。<br>
 * Viewを用いて抽出、ソートし、各ロジックへリストを渡す。
 *
 * @author 近藤
 */
public class ScoreDAO
extends ConstantDefinition
{

	/**
	 * ランキング表示用のリストコレクションを返すメソッド。<br>
	 * 引数として検索するゲームを絞り込み、各ユーザの最高得点を抽出する。<br>
	 * 抽出されたものをscoreを元に降順にソートし、これをViewとする。<br>
	 * ViewをSELECTし、順番にリストコレクションへ保存しこれを返す。
	 *
	 * @param game
	 * 取得するリストのゲーム名
	 *
	 * @return
	 * ランキングのリストコレクション
	 */
	public List<ScoreBeans> getRankingList(String game) {

		List<ScoreBeans> scoreList = new ArrayList<>();

		//データベース接続
		try(Connection con = DriverManager.getConnection(ACCOUNT_URL,DRIVER_USER,DRIVER_PASS)){

			//SELECT文の準備
			String selectSQL = "SELECT * FROM RankView WHERE rank = 1 AND game = '"+ game +"'";
			PreparedStatement stateSELECT = con.prepareStatement(selectSQL);

			//SELECTを実行
			ResultSet list = stateSELECT.executeQuery();

			int index = -1;

			while(list.next())
			{	// SELECT文の結果をArrayListに格納

				// dateのフォーマットを作成
				String date = list.getString("year") +"/"+ list.getString("month") +"/"+ list.getString("day");

				if (index != -1)
				{
					if (scoreList.get(index).getName().equals(list.getString("name")))
					{ continue; }
				}	// if end

				//Beansへセットする
				ScoreBeans var = new ScoreBeans();
				var.setName(list.getString("name"));
				var.setGame(list.getString("game"));
				var.setScore(list.getString("score"));
				var.setDate(date);
				scoreList.add(var);

				index++ ;

			}	// while end

		}catch(SQLException e)
		{	// 例外処理

			e.printStackTrace();
			return null;

		}	// try end

		return scoreList;
	}	// getRankingList method end



	/**
	 * プレイ履歴表示用のリストコレクションを返すメソッド。<br>
	 * 引数よりユーザ名を受け取り、一致するものだけを抽出する。<br>
	 * 抽出されたものをそのままリストコレクションへ保存しこれを返す。
	 *
	 * @param name
	 * 取得するリストのユーザ名
	 *
	 * @return
	 * プレイ履歴のリストコレクション
	 */
	public List<GameBeans> getHistoryList(String name)
	{	//TODO 実装予定
		return null;
	}	// getHistoryList method end
}
