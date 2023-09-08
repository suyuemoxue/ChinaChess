package com.sxj;

import com.sxj.entity.GameBean;
import com.sxj.enums.ColorEnum;
import com.sxj.mapper.GameMapper;
import com.sxj.pojo.Chess;
import com.sxj.pojo.ChessBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Qapi on 2017/6/14.
 */
public class ChessTest {
	ChessBoard chessBoard = ChessBoard.newInstance();

	@Test
	public void test1() {
		printArray(chessBoard.getBoard());
	}

//    @Test
//    public void test2() {
//        Chess[][] chesss = chessBoard.getBoard();
//        System.out.print(ChessUtil.moveOrNot(4,9,chesss[4][0],chesss));
//    }

	public void printArray(Chess[][] chesss) {
		for (int i = 0; i < chesss.length; i++) {
			for (int j = 0; j < chesss[i].length; j++) {
				if (chesss[i][j] != null) {
					System.out.println(
							"坐标：" + i + "-" + j + " 颜色：" + chesss[i][j].getColor() + " 角色：" + chesss[i][j].getIden());

				}
			}
		}
	}

	@Autowired
	GameMapper gameMapper;

	@Autowired
	GameBean gameBean;

	@Test
	public void testMapper() {
		gameBean = new GameBean();
		/*gameBean.setIds("11");*/
		/*System.out.println(gameBean.toString());
		System.out.println("11");*/
		/*
		 * gameMapper.insert(gameBean); List<GameBean> search = gameMapper.search();
		 * System.out.println(search);
		 */
	}

	@Test
	void testMap(){

		ConcurrentMap<ColorEnum, String> ipMap = new ConcurrentHashMap<ColorEnum, String>();
		ipMap.put(ColorEnum.BLACK,"11");
		ipMap.put(ColorEnum.RED,"22");
		for (Map.Entry<ColorEnum, String> aa:ipMap.entrySet() ) {
			System.out.println(aa);
		}
		String red = ipMap.get(ColorEnum.RED);
		System.out.println(red);
	}
}
