package beakjoon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Greedy {

}
/// ==잃어버린 괄호 ===//
/*
 * public static void main(String[] args) {
 * 
 * Scanner sc = new Scanner(System.in); String [] str =
 * sc.nextLine().split("");//첨엔 그냥 쪼갬
 * 
 * List<Integer> intList = new ArrayList<>(); //====== keys ======// String temp
 * = ""; int num = 0;
 * 
 * for(int i = 0; i< str.length; i++){ String chr = str[i];
 * if(chr.contains("-")){ if(num > 0){ num += Integer.parseInt(temp);
 * intList.add(num); num = 0; temp = ""; continue; } if(temp.length()>0){
 * intList.add(Integer.parseInt(temp)); } temp = ""; continue; }
 * if(chr.contains("+")) { num += Integer.parseInt(temp); temp = ""; continue; }
 * temp += str[i]; // 중복이 생겼으니 리팩토링 할 것 if(i == str.length -1 && temp.length() >
 * 0){ if(num > 0){ num += Integer.parseInt(temp); intList.add(num); continue; }
 * intList.add(Integer.parseInt(temp)); } } int result = intList.get(0); for(int
 * i = 1; i < intList.size(); i++){ result -= intList.get(i); }
 * System.out.println(result); }
 */

/*
 * public static void main(String[] args) {
 * 
 * try{ BufferedReader br = new BufferedReader(new
 * InputStreamReader(System.in)); BufferedWriter bw = new BufferedWriter(new
 * OutputStreamWriter(System.out));
 * 
 * int N = Integer.parseInt(br.readLine()); String str = br.readLine(); int []
 * arr = new int[N];
 * 
 * StringTokenizer stringTokenizer = new StringTokenizer(str);
 * 
 * for(int i = 0; i<N; i++){ arr[i] =
 * Integer.parseInt(stringTokenizer.nextToken()); } Arrays.sort(arr);
 * 
 * int result = 0; int temp = 0; for (int item : arr){ temp += item; result +=
 * temp; } bw.write(result+""); bw.flush(); bw.close(); br.close();
 * 
 * }catch(Exception e){ e.printStackTrace(); }
 * 
 * } }
 * 
 */

/*
 * ============== 2번 문제 ==============// public static void main(String[] args)
 * { BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 * try{ int N = Integer.parseInt(br.readLine()); // N Integer [][] timeArr = new
 * Integer [N] [2]; for( int i = 0; i < N ; i++ ){ String [] str =
 * br.readLine().split(" "); timeArr[i] = new
 * Integer[]{Integer.parseInt(str[0]), Integer.parseInt(str[1])}; }
 *
 * Arrays.sort(timeArr, new Comparator<Integer[]>() {
 * 
 * @Override public int compare(Integer[] o1, Integer[] o2) { if(o1[1] ==
 * o2[1]){ return o1[0] - o2[0]; }else{ return o1[1] - o2[1]; } } });
 *
 *
 *
 * int last = 0 ; int count = 0; for(Integer[] item : timeArr){ if( last <=
 * item[0]){ last = item[1]; count++; } }
 *
 * System.out.println(count);
 *
 * br.close();
 *
 * }catch (Exception e){ e.printStackTrace(); } }
 *
 *
 */
/*
 *
 * public static void main(String[] args) { Scanner sc = new Scanner(System.in);
 * String [] str = sc.nextLine().split(" "); int N = Integer.parseInt(str[0]);
 * int K = Integer.parseInt(str[1]); int count = 0; int [] intArr = new int[N];
 * 
 * for( int i = 0; i < N; i++ ){ int num = sc.nextInt(); intArr[i] = num; } //
 * System.out.println(Arrays.toString(intArr)); for( int i = N - 1; i >= 0; i--
 * ){ int num = K / intArr[i]; //몫에 해당 if( num > 0 ){ count += num; K -= num *
 * intArr[i]; } } System.out.println( count ); }
 *
 *
 *
 *
 */