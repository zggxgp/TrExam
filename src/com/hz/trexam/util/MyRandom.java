package com.hz.trexam.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author hz
 * ����100�����ظ��������
 *
 */

public class MyRandom {
	
	public static List<Integer> getRandomList(){
		List<Integer> randomList = new ArrayList<Integer>();
		
		int n = 971;
		Random rand = new Random();
		boolean[] bool = new boolean[n];
		int randint = 0;
		for(int i=0;i<100;i++){
			do{
				randint = rand.nextInt(n);
			}while(bool[randint]);
			bool[randint] = true;
			randomList.add(randint);
		}
		
		return randomList;
	}
}
