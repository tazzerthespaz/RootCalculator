package com.lunarempire.squarerootcalculator;

import java.util.ArrayList;

public class Radical {
	int[] prime_list = {101,101,97,89,83,79,73,71,67,61,59,53,47,43,41,37,31,29,23,19,17,13,11,7,5,3,2};
	int[] prime_tally_list = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	int[] div_tally = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	int orig_in_num;
	int orig_out_num;
	int cur_in_num;
	int cur_out_num;
	int index;
	boolean imaginary;
	
	public Radical(int in_num, int out_num, int in_index){
		if (in_num < 0){
			imaginary = true;
			in_num = in_num * (-1);
		}else{
			imaginary = false;
		}
		index = in_index;
		orig_in_num = in_num;
		cur_in_num = in_num;
		cur_out_num = out_num;
		orig_out_num = out_num;
	}
	
	
	boolean is_square(){
		double potential_sqr = Math.pow(this.orig_in_num, 1.0/(double)index);
		if ((int)potential_sqr == potential_sqr){
			return true;
		}else{
			return false;
		}
	}
	boolean is_prime(){
		int[] in_prime_list = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};
		ArrayList<Boolean> tf_list = new ArrayList<Boolean>();
		
		for(int i = 0; i < in_prime_list.length; i++){
			int prime = in_prime_list[i];
			if (this.cur_in_num % prime == 0 && this.cur_in_num != prime){
				tf_list.add(false);
				break;
			}else if (this.cur_in_num == prime){
				return true;
			}else{
				tf_list.add(true);
			}
		}
		if (tf_list.contains(false)){
			return false;
		}else{
			return true;
		}
	}
	boolean orig_is_prime(){
		int[] in_prime_list = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47};
		ArrayList<Boolean> tf_list = new ArrayList<Boolean>();
		
		for(int i = 0; i < in_prime_list.length; i++){
			int prime = in_prime_list[i];
			if (this.orig_in_num % prime == 0 && this.orig_in_num != prime){
				tf_list.add(false);
				break;
			}else{
				tf_list.add(true);
			}
		}
		if (tf_list.contains(false)){
			return false;
		}else{
			return true;
		}
	}
	boolean factor_out(int divisor){
		if (this.cur_in_num % divisor == 0){
			this.cur_in_num /= divisor;
			return true;
		}else{
			return false;
		}
	}
	void simplify(){
		if(this.is_square()){
			this.cur_out_num = this.cur_out_num * (int)(Math.pow(this.cur_in_num, 1.0/(double)index));
		}else{
			while (this.is_prime() == false){
				for(int i = 0; i < this.prime_list.length; i++){
					int prime = this.prime_list[i];
					
					if (this.factor_out(prime) == true){
						this.prime_tally_list[i] += 1;
						break;
					}
				}
			}
		}
		for(int itemNum = 0;itemNum < this.prime_list.length;itemNum++){
			int prime = this.prime_list[itemNum];
			if (this.cur_in_num == prime){
				this.prime_tally_list[itemNum] += 1;
			}
		}
		
		if (this.orig_is_prime() == false){
			this.cur_in_num = 1;
			for (int itemNum = 0; itemNum < this.prime_list.length;itemNum++){
				int prime = this.prime_list[itemNum];
				if (this.prime_tally_list[itemNum] >= this.index){
					if (this.prime_tally_list[itemNum] % index == 0){
						this.div_tally[itemNum] = this.prime_tally_list[itemNum]/index;
						this.prime_tally_list[itemNum] = 0;
					}else{
						this.div_tally[itemNum] = (int)(this.prime_tally_list[itemNum]/index);
						this.prime_tally_list[itemNum] = this.prime_tally_list[itemNum] % index;
					}
				}
				if (this.prime_tally_list[itemNum] >= 1){
					for(int tally = this.prime_tally_list[itemNum];tally > 0;tally--){
						this.cur_in_num *= prime;
					}
				}
				
				this.cur_out_num = (int) (this.cur_out_num * (Math.pow(prime, this.div_tally[itemNum])));
			}
		}else{
			this.cur_in_num = this.orig_in_num;
		}
	}
}
